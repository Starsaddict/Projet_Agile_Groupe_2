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
    <title>Floating Window</title>
    <style>
        /* Basic floating window styling */
        .floating-window {
            position: fixed;
            bottom: 24px;
            right: 24px;
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
    </style>
</head>
<body>

<div id="floatingWindow" class="floating-window" aria-live="polite">
    <div class="floating-window__header">
        <h4 class="floating-window__title">Quick Panel</h4>
        <button class="floating-window__close" type="button" aria-label="Close" onclick="FloatingWindow.close()">&times;</button>
    </div>
    <div class="floating-window__content" id="floatingWindowContent">
        Loading...
    </div>
    <div class="floating-window__status" id="floatingWindowStatus"></div>
    <div class="floating-window__actions">
        <button class="floating-window__button floating-window__button--secondary" type="button" onclick="FloatingWindow.refresh()">Refresh</button>
        <button class="floating-window__button" type="button" onclick="FloatingWindow.invokeAction()">Run Action</button>
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
                            return (
                                '<div class="floating-window__item">' +
                                    '<div class="floating-window__name">' + (item.nom || 'N/A') + '</div>' +
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
            }
        };
    })();
</script>
</body>
</html>
