<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Utilisateur, model.Parent, model.Joueur" %>
<%
    List<Utilisateur> profiles = (List<Utilisateur>) request.getAttribute("profiles");
    Parent parent = (Parent) request.getAttribute("parent");
    String contextPath = request.getContextPath();
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mon Profil - Parent</title>
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
        <h1>Mes Profils - <%= parent != null ? parent.getPrenomUtilisateur() + " " + parent.getNomUtilisateur() : "Parent" %></h1>

        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>

        <% if ("1".equals(success)) { %>
            <div class="alert success">Profil mis à jour avec succès.</div>
        <% } %>

        <% if (profiles != null && !profiles.isEmpty()) {
            for (Utilisateur user : profiles) {
                String nom = user.getNomUtilisateur() != null ? user.getNomUtilisateur() : "";
                String prenom = user.getPrenomUtilisateur() != null ? user.getPrenomUtilisateur() : "";
                String email = user.getEmailUtilisateur() != null ? user.getEmailUtilisateur() : "";
                String adresse = user.getAdresseUtilisateur() != null ? user.getAdresseUtilisateur() : "-";
                String telephone = user.getTelephoneUtilisateur() != null ? user.getTelephoneUtilisateur() : "-";
                String numeroLicence = (user instanceof Joueur) ? ((Joueur) user).getNumeroJoueur() : null;
                String userType = (user instanceof Parent) ? "(Parent)" : "(Enfant)";
                String actionLabel = "Modifier le profil";
        %>
            <div class="profile-card">
                <h2>
                    <%= prenom %> <%= nom %>
                    <span class="user-type"><%= userType %></span>
                </h2>

                <p><strong>Email :</strong> <%= email %></p>
                <p><strong>Adresse :</strong> <%= adresse %></p>
                <p><strong>Téléphone :</strong> <%= telephone %></p>

                <% if (numeroLicence != null && !numeroLicence.isEmpty()) { %>
                    <p><strong>Numéro licence :</strong> <%= numeroLicence %></p>
                <% } %>

                <div class="action-buttons">
                    <a href="<%= contextPath %>/parent/profil/edit?id=<%= user.getIdUtilisateur() %>" class="btn btn-primary">
                        <%= actionLabel %>
                    </a>
                </div>
            </div>
        <%
            }
        } else {
        %>
            <p class="empty">Aucun profil disponible.</p>
        <%
        }
        %>
    </div>
</div>

</body>
</html>