<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Joueur" %>
<%
    Joueur joueur = (Joueur) request.getAttribute("joueur");
    String contextPath = request.getContextPath();
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
    if (joueur == null) {
        response.sendRedirect(contextPath + "/Login?error=Joueur+introuvable");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mon Profil - Joueur</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
    <style>
        .profile-card {
            background: white;
            border: 1px solid var(--border);
            border-radius: var(--radius);
            padding: 1.5rem;
            margin-top: 1rem;
        }
        .profile-card h2 {
            margin-bottom: 1rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            flex-wrap: wrap;
        }
        .user-type {
            font-size: 0.8125rem;
            color: var(--text-muted);
            font-weight: 500;
        }
        .profile-card p {
            margin-bottom: 0.75rem;
            color: var(--text);
        }
        .profile-card p strong {
            color: var(--text-muted);
            font-weight: 500;
        }
        .action-buttons {
            margin-top: 1.25rem;
            display: flex;
            gap: 0.75rem;
            flex-wrap: wrap;
        }
    </style>
</head>

<body>
<%@ include file="/jsp/header.jspf" %>

<div class="container">
    <div class="card">
        <h1>Mon Profil - <%= joueur.getPrenomUtilisateur() %> <%= joueur.getNomUtilisateur() %></h1>

        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>

        <% if ("1".equals(success)) { %>
            <div class="alert success">Profil mis à jour avec succès.</div>
        <% } %>

        <div class="profile-card">
            <h2>
                <%= joueur.getPrenomUtilisateur() %> <%= joueur.getNomUtilisateur() %>
                <span class="user-type">(Joueur)</span>
            </h2>

            <p><strong>Email :</strong> <%= joueur.getEmailUtilisateur() != null ? joueur.getEmailUtilisateur() : "-" %></p>
            <p><strong>Numéro licence :</strong> <%= joueur.getNumeroJoueur() != null ? joueur.getNumeroJoueur() : "-" %></p>
            <p><strong>Adresse :</strong> <%= joueur.getAdresseUtilisateur() != null ? joueur.getAdresseUtilisateur() : "-" %></p>
            <p><strong>Téléphone :</strong> <%= joueur.getTelephoneUtilisateur() != null ? joueur.getTelephoneUtilisateur() : "-" %></p>

            <div class="action-buttons">
                <a href="<%= contextPath %>/joueur/profil/edit" class="btn btn-primary">Modifier mon profil</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>