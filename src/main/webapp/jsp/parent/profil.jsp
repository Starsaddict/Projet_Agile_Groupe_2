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
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <div class="action-buttons" style="justify-content: space-between; align-items: center;">
            <a href="<%= contextPath %>/jsp/home.jsp" class="btn secondary">⬅ Retour à l’accueil</a>
            <form action="<%= contextPath %>/CtrlLogout" method="post" style="margin:0;">
                <button type="submit" class="btn danger">🚪 Déconnexion</button>
            </form>
        </div>

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
            <div class="card" style="margin-top: 16px;">
                <h2><%= prenom %> <%= nom %> <span style="font-size: 0.75em; color: var(--text-muted);"><%= userType %></span></h2>
                <p><strong>Email:</strong> <%= email %></p>
                
                <p><strong>Adresse:</strong> <%= adresse %></p>
                <p><strong>Téléphone:</strong> <%= telephone %></p>
                <% if (numeroLicence != null && !numeroLicence.isEmpty()) { %>
                    <p><strong>Numéro licence:</strong> <%= numeroLicence %></p>
                <% } %>
                
                <div class="action-buttons">
                    <a href="<%= contextPath %>/parent/profil/edit?id=<%= user.getIdUtilisateur() %>" class="btn"><%= actionLabel %></a>
                </div>
            </div>
        <%      }
        } %>
    </div>
</div>
</body>
</html>
