<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String code = (String) request.getAttribute("invitationCode");
    if (code == null) code = request.getParameter("code");
    String error = request.getParameter("error");
%>
<html>
<head>
    <title>Inscription</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/secretaire-profil.css">
</head>
<body>
<div class="container">
    <h1>Inscription - Créer votre compte</h1>

    <% if (error != null && !error.isEmpty()) { %>
        <div class="alert error"><%= error %></div>
    <% } %>

    <form class="form-card" method="post" action="<%= contextPath %>/inscription/process">
        <input type="hidden" name="code" value="<%= code %>" />

        <div class="form-columns">
            <div class="form-column">
                <h2 class="section-title">Informations parent</h2>
                <div class="field">
                    <label>Nom</label>
                    <input type="text" name="parent_nom" required />
                </div>
                <div class="field">
                    <label>Prénom</label>
                    <input type="text" name="parent_prenom" required />
                </div>
                <div class="field">
                    <label>Email</label>
                    <input type="email" name="parent_email" required />
                </div>
            </div>

            <div class="form-column">
                <h2 class="section-title">Informations joueur (optionnel)</h2>
                <div id="joueur-rows">
                    <div class="dynamic-row joueur-row">
                        <div class="field">
                            <label>Nom</label>
                            <input type="text" name="joueur_nom[]" />
                        </div>
                        <div class="field">
                            <label>Prénom</label>
                            <input type="text" name="joueur_prenom[]" />
                        </div>
                        <div class="field">
                            <label>Email</label>
                            <input type="email" name="joueur_email[]" />
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="add-group">
            <button type="button" class="btn secondary" onclick="addJoueur()">+ Ajouter un joueur</button>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn">S'inscrire</button>
            <a href="<%= contextPath %>/" class="btn secondary">Annuler</a>
        </div>
    </form>
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
        row.innerHTML =
            '<div class="field">'
            + '<label>Nom</label>'
            + '<input type="text" name="' + type + '_nom[]" />'
            + '</div>'
            + '<div class="field">'
            + '<label>Prénom</label>'
            + '<input type="text" name="' + type + '_prenom[]" />'
            + '</div>'
            + '<div class="field">'
            + '<label>Email</label>'
            + '<input type="email" name="' + type + '_email[]" />'
            + '</div>';

        container.appendChild(row);
    }

    function addJoueur() { addRow('joueur-rows', 'joueur'); }
</script>
</body>
</html>
