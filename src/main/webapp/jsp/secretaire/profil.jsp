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
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <div class="action-buttons" style="justify-content: space-between; align-items: center;">
            <a href="<%= contextPath %>/jsp/home.jsp" class="btn secondary"> Retour à l’accueil</a>
            <form action="<%= contextPath %>/CtrlLogout" method="post" style="margin:0;">
                <button type="submit" class="btn danger"> Déconnexion</button>
            </form>
        </div>

        <h1>Secrétariat - Gestion des Comptes</h1>
        
        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>
        
        <% if ("1".equals(success)) { %>
            <div class="alert success">Opération complétée avec succès</div>
        <% } else if ("invitation".equals(success)) { %>
            <div class="alert success">Invitation envoyée avec succès</div>
        <% } %>
        
        <% if (batchSuccess != null) { %>
            <div class="alert success"><%= batchSuccess %></div>
        <% } %>
        
        <div class="card" style="background:#f9f9f9; margin-top:12px;">
            <strong>Bienvenue dans l'interface de gestion du secrétariat.</strong><br/>
            Vous pouvez créer des comptes, importer des données ou gérer les utilisateurs existants.
        </div>
        
        <div class="card" style="margin-top:12px;">
            <h2>👥 Gestion des utilisateurs</h2>
            <ul>
                <li style="padding-left:0;">
                    <a href="<%= contextPath %>/secretaire/profil/modifier">👁️ Voir tous les utilisateurs</a>
                    <p class="description">Consulter et modifier les profils existants</p>
                </li>
            </ul>
        </div>
        
        <div class="card" style="margin-top:12px;">
            <h2>➕ Création de comptes</h2>
            <ul>
                <li style="padding-left:0;">
                    <a href="<%= contextPath %>/secretaire/profil/creerCompteFamile">👨‍👩‍👧 Créer une famille</a>
                    <p class="description">Ajouter un parent et ses enfants</p>
                </li>
                <li style="padding-left:0;">
                    <a href="<%= contextPath %>/secretaire/profil/batchCreate">📊 Import en masse (Excel)</a>
                    <p class="description">Importer plusieurs comptes via fichier Excel</p>
                </li>
                <li style="padding-left:0;">
                    <form method="post" action="<%= contextPath %>/secretaire/profil/sendInvitation" style="display:flex;gap:8px;align-items:center;flex-wrap:wrap;">
                        <input type="email" name="email" placeholder="Email parent" required style="flex:1; min-width:200px;" />
                        <button type="submit" class="btn">Envoyer invitation</button>
                    </form>
                    <p class="description">Envoyer un code d'inscription au parent</p>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
