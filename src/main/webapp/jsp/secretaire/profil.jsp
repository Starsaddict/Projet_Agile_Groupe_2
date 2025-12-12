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
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 800px; margin: 0 auto; }
        h1 { color: #1976d2; border-bottom: 2px solid #1976d2; padding-bottom: 10px; }
        .alert { padding: 10px 12px; border-radius: 4px; margin: 10px 0; }
        .alert.success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert.error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .menu-section {
            background: #f9f9f9;
            padding: 20px;
            border-radius: 4px;
            margin: 20px 0;
        }
        .menu-section h2 {
            margin-top: 0;
            color: #1976d2;
        }
        .menu ul { list-style: none; padding: 0; }
        .menu li { margin: 12px 0; }
        .menu a {
            display: inline-block;
            color: white;
            text-decoration: none;
            padding: 12px 20px;
            background: #1976d2;
            border-radius: 4px;
            font-weight: bold;
        }
        .menu a:hover { background: #125aa0; }
        .info-box {
            background: #e3f2fd;
            border-left: 4px solid #1976d2;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .description {
            color: #666;
            font-size: 13px;
            margin: 5px 0 0 0;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Secrétariat - Gestion des Comptes</h1>
    
    <% if (error != null) { %>
        <div class="alert alert-error"><%= error %></div>
    <% } %>
    
    <% if ("1".equals(success)) { %>
        <div class="alert alert-success">Opération complétée avec succès</div>
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
        </ul>
    </div>
</div>
</body>
</html>
