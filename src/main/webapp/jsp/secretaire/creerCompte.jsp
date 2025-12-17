<%--
  Created by IntelliJ IDEA.
  User: kaiyangzhang
  Date: 2025/12/10
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String error = request.getParameter("error");
%>
<html>
<head>
    <title>Creer Compte</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card" style="margin-bottom:12px;">
        <a href="<%= contextPath %>/secretaire/profil" class="btn secondary">← Retour</a>
    </div>

    <div class="card">
        <h1>Création du compte famille</h1>

        <% if (error != null && !error.isEmpty()) { %>
            <div class="alert error"><%= error %></div>
        <% } %>
        <div class="action-buttons" style="margin-top:12px; gap:8px; flex-wrap:wrap;">
                <button type="button" class="btn secondary" onclick="addJoueur()">+ Ajouter un joueur</button>
                <button type="button" class="btn secondary" onclick="addParent()">+ Ajouter un parent</button>
        </div>
        <form method="post" action="<%= contextPath %>/secretaire/profil/creerCompteFamile">
            <div class="form-columns">
                <div class="form-column">
                    <h2 class="section-title">Informations joueur</h2>
                    <div id="joueur-rows">
                        <div class="dynamic-row joueur-row">
                            <div class="field">
                                <label>Nom</label>
                                <input type="text" name="joueur_nom[]" required />
                            </div>
                            <div class="field">
                                <label>Prénom</label>
                                <input type="text" name="joueur_prenom[]" required />
                            </div>
                            <div class="field">
                                <label>Email</label>
                                <input type="email" name="joueur_email[]" />
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-column">
                    <h2 class="section-title">Informations parent</h2>
                    <div id="parent-rows">
                        <div class="dynamic-row parent-row">
                            <div class="field">
                                <label>Nom</label>
                                <input type="text" name="parent_nom[]" required />
                            </div>
                            <div class="field">
                                <label>Prénom</label>
                                <input type="text" name="parent_prenom[]" required />
                            </div>
                            <div class="field">
                                <label>Email</label>
                                <input type="email" name="parent_email[]" required />
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="action-buttons" style="margin-top:12px;">
                <button type="submit" class="btn">Créer</button>
                <button type="button" class="btn secondary" onclick="window.history.back();">Annuler</button>
            </div>
        </form>
    </div>
</div>

<script>
    function addRow(containerId, type) {
        var container = document.getElementById(containerId);
        if (container.children.length) {
            var separator = document.createElement("hr");
            separator.className = "row-separator";
            container.appendChild(separator);
        }

        var row = document.createElement("div");
        row.className = "dynamic-row " + type + "-row";
        var emailRequired = type === "parent" ? " required" : "";
        row.innerHTML =
            '<div class="field">' +
                '<label>Nom</label>' +
                '<input type="text" name="' + type + '_nom[]" required />' +
            '</div>' +
            '<div class="field">' +
                '<label>Prénom</label>' +
                '<input type="text" name="' + type + '_prenom[]" required />' +
            '</div>' +
            '<div class="field">' +
                '<label>Email</label>' +
                '<input type="email" name="' + type + '_email[]"' + emailRequired + ' />' +
            '</div>';

        container.appendChild(row);
    }

    function addJoueur() {
        addRow("joueur-rows", "joueur");
    }

    function addParent() {
        addRow("parent-rows", "parent");
    }
</script>
</body>
</html>
