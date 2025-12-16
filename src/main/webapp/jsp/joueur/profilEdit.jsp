<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.Joueur" %>
<%
    Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
    String contextPath = request.getContextPath();
    String error = (String) request.getAttribute("error");

    if (!(utilisateur instanceof Joueur)) {
        response.sendRedirect(contextPath + "/Login?error=Acces+refuse");
        return;
    }

    Joueur joueur = (Joueur) utilisateur;
%>
<html>
<head>
    <title>Modifier mon profil - Joueur</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <div class="action-buttons" style="justify-content: space-between; align-items: center;">
            <a href="<%= contextPath %>/joueur/profil" class="btn secondary">⬅ Retour</a>
            <form action="<%= contextPath %>/CtrlLogout" method="post" style="margin:0;">
                <button type="submit" class="btn danger">🚪 Déconnexion</button>
            </form>
        </div>

        <h1>Modifier mon profil</h1>

        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>

        <form method="post" action="<%= contextPath %>/joueur/profil/edit">
            <div class="card" style="background:#f9f9f9; margin-top:12px;">
                <p><strong>Nom:</strong> <%= joueur.getNomUtilisateur() %></p>
                <p><strong>Prénom:</strong> <%= joueur.getPrenomUtilisateur() %></p>
                <p><strong>Email:</strong> <%= joueur.getEmailUtilisateur() != null ? joueur.getEmailUtilisateur() : "-" %></p>
                <p><strong>Numéro licence:</strong> <%= joueur.getNumeroJoueur() != null ? joueur.getNumeroJoueur() : "-" %></p>
            </div>

            <div class="field">
                <label for="adresse">Adresse</label>
                <input type="text" id="adresse" name="adresse" value="<%= joueur.getAdresseUtilisateur() != null ? joueur.getAdresseUtilisateur() : "" %>">
            </div>

            <div class="field">
                <label for="telephone">Téléphone</label>
                <input type="text" id="telephone" name="telephone" value="<%= joueur.getTelephoneUtilisateur() != null ? joueur.getTelephoneUtilisateur() : "" %>">
            </div>

            <div class="action-buttons">
                <button type="submit" class="btn">Enregistrer</button>
                <a href="<%= contextPath %>/joueur/profil" class="btn secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
