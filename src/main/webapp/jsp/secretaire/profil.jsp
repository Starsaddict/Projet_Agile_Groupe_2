<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
    String batchSuccess = (String) request.getAttribute("batchSuccess");
%>
<html>
<head>
    <title>Profil Secrétaire</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/secretaire-profil.css">
</head>
<body>
<div class="container">
    <h1>Secrétariat - Gestion des Comptes</h1>
    
    <% if (error != null) { %>
        <div class="alert alert-error"><%= error %></div>
    <% } %>
    
    <% if ("1".equals(success)) { %>
        <div class="alert alert-success">Opération complétée avec succès</div>
    <% } else if ("invitation".equals(success)) { %>
        <div class="alert alert-success">Invitation envoyée avec succès</div>
    <% } %>
    
    <% if (batchSuccess != null) { %>
        <div class="alert alert-success"><%= batchSuccess %></div>
    <% } %>
    
    <div class="info-box">
        <strong>Bienvenue dans l'interface de gestion du secrétariat.</strong><br/>
        Vous pouvez créer des comptes, importer des données ou gérer les utilisateurs existants.
    </div>
    
    <div class="menu-section">
        <h2>👥 Gestion des utilisateurs</h2>
        <ul>
            <li>
                <a href="<%= contextPath %>/secretaire/profil/modifier">👁️ Voir tous les utilisateurs</a>
                <p class="description">Consulter et modifier les profils existants</p>
            </li>
        </ul>
    </div>
    
    <div class="menu-section">
        <h2>➕ Création de comptes</h2>
        <ul>
            <li>
                <a href="<%= contextPath %>/secretaire/profil/creerCompteFamile">👨‍👩‍👧 Créer une famille</a>
                <p class="description">Ajouter un parent et ses enfants</p>
            </li>
            <li>
                <a href="<%= contextPath %>/secretaire/profil/batchCreate">📊 Import en masse (Excel)</a>
                <p class="description">Importer plusieurs comptes via fichier Excel</p>
            </li>
            <li>
                <form method="post" action="<%= contextPath %>/secretaire/profil/sendInvitation" style="display:flex;gap:8px;align-items:center;">
                    <input type="email" name="email" placeholder="Email parent" required />
                    <button type="submit" class="btn">Envoyer invitation</button>
                </form>
                <p class="description">Envoyer un code d'inscription au parent</p>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
