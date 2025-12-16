<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Récupération du chemin de contexte de l'application
    String contextPath = request.getContextPath();
    
    // Récupération du code d'invitation depuis les attributs ou paramètres de requête
    String code = (String) request.getAttribute("invitationCode");
    if (code == null) code = request.getParameter("code");
    
    // Récupération des messages d'erreur
    String error = request.getParameter("error");
%>
<html>
<head>
    <title>Inscription</title>
    <%-- Inclusion de la feuille de style CSS --%>
    <link rel="stylesheet" href="<%= contextPath %>/css/secretaire-profil.css">
</head>
<body>
<div class="container">
    <h1>Inscription - Créer votre compte</h1>

    <%-- Affichage des messages d'erreur s'ils existent --%>
    <% if (error != null && !error.isEmpty()) { %>
        <div class="alert error"><%= error %></div>
    <% } %>

    <%-- Formulaire d'inscription --%>
    <form class="form-card" method="post" action="<%= contextPath %>/inscription/process">
        <%-- Champ caché pour transmettre le code d'invitation --%>
        <input type="hidden" name="code" value="<%= code %>" />

        <%-- Structure en deux colonnes pour séparer parent et joueur --%>
        <div class="form-columns">
            <%-- Colonne de gauche : Informations du parent --%>
            <div class="form-column">
                <h2 class="section-title">Informations parent</h2>
                <%-- Champ Nom du parent --%>
                <div class="field">
                    <label>Nom</label>
                    <input type="text" name="parent_nom" required />
                </div>
                <%-- Champ Prénom du parent --%>
                <div class="field">
                    <label>Prénom</label>
                    <input type="text" name="parent_prenom" required />
                </div>
                <%-- Champ Email du parent --%>
                <div class="field">
                    <label>Email</label>
                    <input type="email" name="parent_email" required />
                </div>
            </div>

            <%-- Colonne de droite : Informations du joueur (au moins 1 requis) --%>
            <div class="form-column">
                <h2 class="section-title">Informations joueur</h2>
                <%-- Conteneur dynamique pour les lignes de joueurs --%>
                <div id="joueur-rows">
                    <%-- Première ligne de joueur par défaut --%>
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
                <p class="description" style="margin-top:8px;color:var(--text-muted);">
                    Saisissez au moins un joueur (nom et prénom requis). Vous pouvez en ajouter plusieurs.
                </p>
            </div>
        </div>

        <%-- Section pour ajouter dynamiquement des joueurs --%>
        <div class="add-group">
            <%-- Bouton pour ajouter un nouveau joueur --%>
            <button type="button" class="btn secondary" onclick="addJoueur()">+ Ajouter un joueur</button>
        </div>

        <%-- Boutons d'action du formulaire --%>
        <div class="form-actions">
            <%-- Bouton de soumission du formulaire --%>
            <button type="submit" class="btn">S'inscrire</button>
            <%-- Lien d'annulation --%>
            <a href="<%= contextPath %>/" class="btn secondary">Annuler</a>
        </div>
    </form>
</div>

<%-- Script JavaScript pour la fonctionnalité dynamique --%>
<script>
    /**
     * Fonction pour ajouter dynamiquement une nouvelle ligne de formulaire
     * @param {string} containerId - ID du conteneur où ajouter la nouvelle ligne
     * @param {string} type - Type d'utilisateur ('joueur' ou autre)
     */
    function addRow(containerId, type) {
        // Récupération du conteneur par son ID
        var container = document.getElementById(containerId);
        
        // Ajout d'un séparateur visuel si ce n'est pas la première ligne
        if (container.children.length) {
            var separator = document.createElement("hr");
            separator.className = "row-separator";
            container.appendChild(separator);
        }

        // Création de la nouvelle ligne
        var row = document.createElement("div");
        row.className = "dynamic-row " + type + "-row";
        
        // Construction du HTML de la nouvelle ligne
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

        // Ajout de la nouvelle ligne au conteneur
        container.appendChild(row);
    }

    /**
     * Fonction spécifique pour ajouter un nouveau joueur
     * Appelle addRow() avec les paramètres appropriés
     */
    function addJoueur() { 
        addRow('joueur-rows', 'joueur'); 
    }
</script>
</body>
</html>
