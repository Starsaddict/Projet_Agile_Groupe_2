<%--
  Created by IntelliJ IDEA.
  User: kaiyangzhang
  Date: 2025/12/18
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Messages</title>
    <style>
        /* Basic floating window styling */
        .floating-window {
            position: fixed;
            bottom: 64px;
            right: 64px;
            width: 320px;
            max-height: 60vh;
            background: #ffffff;
            border: 1px solid #e0e0e0;
            border-radius: 12px;
            box-shadow: 0 12px 28px rgba(0, 0, 0, 0.12);
            overflow: hidden;
            display: none;
            z-index: 9999;
            font-family: Arial, sans-serif;
        }

        .floating-window__header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 12px 16px;
            background: #0a65cc;
            color: #ffffff;
        }

        .floating-window__title {
            margin: 0;
            font-size: 16px;
            font-weight: 600;
        }

        .floating-window__close {
            cursor: pointer;
            font-size: 16px;
            border: none;
            background: transparent;
            color: inherit;
        }

        .floating-window__content {
            padding: 12px 16px;
            overflow-y: auto;
            max-height: 46vh;
            font-size: 14px;
            color: #1f1f1f;
        }

        .floating-window__actions {
            display: flex;
            justify-content: flex-end;
            gap: 8px;
            padding: 10px 16px 14px;
            border-top: 1px solid #f0f0f0;
            background: #fafafa;
        }

        .floating-window__button {
            padding: 8px 12px;
            border-radius: 8px;
            border: 1px solid #0a65cc;
            background: #0a65cc;
            color: #ffffff;
            cursor: pointer;
            font-size: 13px;
        }

        .floating-window__button--secondary {
            background: #ffffff;
            color: #0a65cc;
        }

        .floating-window__status {
            font-size: 12px;
            color: #666666;
            padding: 4px 16px 10px;
        }

        .floating-window__item {
            padding: 8px 0;
            border-bottom: 1px solid #f0f0f0;
        }

        .floating-window__item:last-child {
            border-bottom: none;
        }

        .floating-window__name {
            font-weight: 600;
            margin-bottom: 4px;
        }

        .floating-window__meta {
            color: #444;
            margin-bottom: 2px;
        }

        .message-modal-overlay {
            position: fixed;
            inset: 0;
            background: rgba(0, 0, 0, 0.45);
            display: none;
            align-items: center;
            justify-content: center;
            z-index: 10001;
        }

        .message-modal {
            background: #fff;
            border-radius: 12px;
            width: 420px;
            max-width: 90vw;
            padding: 20px;
            box-shadow: 0 18px 32px rgba(0,0,0,0.18);
        }

        .message-modal h4 {
            margin: 0 0 12px;
            font-size: 18px;
        }

        .message-modal label {
            display: block;
            margin-bottom: 6px;
            font-weight: 600;
        }

        .message-modal input,
        .message-modal textarea {
            width: 100%;
            padding: 8px 10px;
            border: 1px solid #d0d0d0;
            border-radius: 6px;
            font-size: 14px;
            box-sizing: border-box;
        }

        .message-modal textarea {
            min-height: 120px;
            resize: vertical;
        }

        .message-modal__actions {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 14px;
        }
    </style>
</head>
<body>

<div id="floatingWindow" class="floating-window" aria-live="polite">
    <div class="floating-window__header">
        <h4 class="floating-window__title">Messagerie</h4>
        <button class="floating-window__close" type="button" aria-label="Close" onclick="FloatingWindow.close()">&times;</button>
    </div>
    <div class="floating-window__content" id="floatingWindowContent">
        Loading...
    </div>
    <div class="floating-window__status" id="floatingWindowStatus"></div>
</div>

<div class="message-modal-overlay" id="messageModalOverlay">
    <div class="message-modal">
        <h4 id="messageModalTitle">Envoyer un message</h4>
        <input type="hidden" id="messageEmail">
        <div>
            <label for="messageSubject">Titre</label>
            <input type="text" id="messageSubject" placeholder="Sujet du message">
        </div>
        <div style="margin-top:10px;">
            <label for="messageBody">Contenu</label>
            <textarea id="messageBody" placeholder="Tapez votre message"></textarea>
        </div>
        <div class="message-modal__actions">
            <button type="button" class="floating-window__button floating-window__button--secondary" onclick="FloatingWindow.closeMessageModal()">Annuler</button>
            <button type="button" class="floating-window__button" onclick="FloatingWindow.sendMessage()">Envoyer</button>
        </div>
    </div>
</div>

<script>
    (function () {
        const API = {
            base: '<c:url value="/api/floating"/>'
        };

        function setStatus(msg) {
            document.getElementById('floatingWindowStatus').textContent = msg || '';
        }

        async function fetchJson(url, options) {
            const response = await fetch(url, Object.assign({
                headers: { 'Accept': 'application/json' },
                credentials: 'same-origin'
            }, options || {}));

            if (!response.ok) {
                throw new Error('Request failed with status ' + response.status);
            }
            return response.json();
        }

        window.FloatingWindow = {
            open: function () {
                const el = document.getElementById('floatingWindow');
                el.style.display = 'block';
                this.refresh();
            },
            close: function () {
                document.getElementById('floatingWindow').style.display = 'none';
            },
            refresh: async function () {
                setStatus('Refreshing...');
                try {
                    const data = await fetchJson(API.base);
                    const contentEl = document.getElementById('floatingWindowContent');
                    const items = (data && data.items) || [];
                    contentEl.innerHTML = items.length
                        ? items.map(function (item) {
                            const name = ((item.prenom || '') + ' ' + (item.nom || '')).trim();
                            return (
                                '<div class="floating-window__item" ' +
                                    'data-email="' + (item.email || '') + '" ' +
                                    'data-name="' + name + '" ' +
                                    'onclick="FloatingWindow.openMessageModal(this.dataset)">' +
                                    '<div class="floating-window__name">' + (name || 'N/A') + '</div>' +
                                    '<div class="floating-window__meta">Email: ' + (item.email || 'N/A') + '</div>' +
                                    '<div class="floating-window__meta">Rôles: ' + (item.roles || 'N/A') + '</div>' +
                                '</div>'
                            );
                        }).join('')
                        : '<div>No data available.</div>';
                    setStatus('Updated just now');
                } catch (err) {
                    document.getElementById('floatingWindowContent').innerHTML = '<div>Failed to load data.</div>';
                    setStatus(err.message);
                }
            },
            invokeAction: async function () {
                setStatus('Running action...');
                try {
                    await fetchJson(API.base, { method: 'POST' });
                    setStatus('Action completed');
                } catch (err) {
                    setStatus('Action failed: ' + err.message);
                }
            },
            openMessageModal: function (data) {
                const overlay = document.getElementById('messageModalOverlay');
                document.getElementById('messageEmail').value = data.email || '';
                document.getElementById('messageSubject').value = '';
                document.getElementById('messageBody').value = '';
                document.getElementById('messageModalTitle').textContent = 'Envoyer un message à ' + (data.name || 'utilisateur');
                overlay.style.display = 'flex';
            },
            closeMessageModal: function () {
                const overlay = document.getElementById('messageModalOverlay');
                overlay.style.display = 'none';
            },
            sendMessage: async function () {
                const email = document.getElementById('messageEmail').value;
                const title = document.getElementById('messageSubject').value;
                const content = document.getElementById('messageBody').value;
                setStatus('Envoi...');
                try {
                    await fetch(API.base, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                        body: new URLSearchParams({ email: email, title: title, content: content })
                    });
                    setStatus('Message prêt à être traité');
                } catch (err) {
                    setStatus('Échec: ' + err.message);
                } finally {
                    this.closeMessageModal();
                }
            }
        };
    })();
</script>
</body>
</html>
