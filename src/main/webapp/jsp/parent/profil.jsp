<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Utilisateur, model.Parent, model.Joueur" %>
<%
    List<Utilisateur> profiles = (List<Utilisateur>) request.getAttribute("profiles");
    Parent parent = (Parent) request.getAttribute("parent");
    String contextPath = request.getContextPath();
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
%>
<html>
<head>
    <title>Mon Profil - Parent</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 900px; margin: 0 auto; }
        h1 { color: #1976d2; border-bottom: 2px solid #1976d2; padding-bottom: 10px; }
        .alert { padding: 10px 12px; border-radius: 4px; margin: 10px 0; }
        .alert.success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert.error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .profile-card {
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 15px;
            margin: 15px 0;
            background: #f9f9f9;
        }
        .profile-status {
            font-weight: bold;
            padding: 8px;
            border-radius: 4px;
            margin: 10px 0;
            display: inline-block;
        }
        .status-incomplete {
            background: #fff3cd;
            color: #856404;
        }
        .status-complete {
            background: #d4edda;
            color: #155724;
        }
        .btn {
            display: inline-block;
            padding: 8px 15px;
            margin: 5px 5px 5px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
        }
        .btn-primary {
            background: #1976d2;
            color: white;
        }
        .btn-primary:hover {
            background: #125aa0;
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background: #5a6268;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Mes Profils - <%= parent != null ? parent.getPrenomUtilisateur() + " " + parent.getNomUtilisateur() : "Parent" %></h1>
    
    <% if (error != null) { %>
        <div class="alert alert-error"><%= error %></div>
    <% } %>
    
    <% if ("1".equals(success)) { %>
        <div class="alert alert-success">Profil mis à jour avec succès.</div>
    <% } %>
    
    <% if (profiles != null && !profiles.isEmpty()) {
        for (Utilisateur user : profiles) {
            String nom = user.getNomUtilisateur() != null ? user.getNomUtilisateur() : "";
            String prenom = user.getPrenomUtilisateur() != null ? user.getPrenomUtilisateur() : "";
            String email = user.getEmailUtilisateur() != null ? user.getEmailUtilisateur() : "";
            String description = user.getDescription();
            boolean hasProfile = description != null && !description.trim().isEmpty();
            
            String userType = (user instanceof Parent) ? "(Parent)" : "(Enfant)";
    %>
        <div class="profile-card">
            <h2><%= prenom %> <%= nom %> <span style="font-size: 0.7em; color: #999;"><%= userType %></span></h2>
            <p><strong>Email:</strong> <%= email %></p>
            
            <% if (hasProfile) { %>
                <div class="profile-status status-complete">✓ Profil complété</div>
                <p><strong>Profil:</strong></p>
                <p style="background: #f0f0f0; padding: 10px; border-radius: 4px; white-space: pre-wrap;">
                    <%= description %>
                </p>
            <% } else { %>
                <div class="profile-status status-incomplete">⚠ Profil non complété</div>
                <p style="color: #666;"><em>Aucun profil créé. Cliquez sur "Créer/Modifier" pour créer le profil.</em></p>
            <% } %>
            
            <div style="margin-top: 10px;">
                <a href="<%= contextPath %>/parent/profil/edit?id=<%= user.getIdUtilisateur() %>" class="btn btn-primary">
                    Créer/Modifier le profil
                </a>
            </div>
        </div>
    <%      }
    } %>
</div>
</body>
</html>
