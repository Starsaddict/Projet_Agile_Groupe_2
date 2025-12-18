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
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier mon profil - Joueur</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
    <style>
        .floating-panel-trigger {
            position: fixed;
            bottom: 24px;
            right: 24px;
            z-index: 9000;
        }
    </style>
</head>

<body>
<%@ include file="/jsp/header.jspf" %>

<div class="container">
    <div class="card">
        

        <h1>Modifier mon profil</h1>

        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>

        <div class="alert" style="background: #e3f2fd; border-left: 4px solid var(--primary); color: var(--text);">
            <p><strong>Nom :</strong> <%= joueur.getNomUtilisateur() %></p>
            <p><strong>Prénom :</strong> <%= joueur.getPrenomUtilisateur() %></p>
            <p><strong>Email :</strong> <%= joueur.getEmailUtilisateur() != null ? joueur.getEmailUtilisateur() : "-" %></p>
            <p><strong>Numéro licence :</strong> <%= joueur.getNumeroJoueur() != null ? joueur.getNumeroJoueur() : "-" %></p>
        </div>

        <form method="post" action="<%= contextPath %>/joueur/profil/edit">
            <div class="field">
                <label for="adresse">Adresse</label>
                <input type="text" id="adresse" name="adresse" value="<%= joueur.getAdresseUtilisateur() != null ? joueur.getAdresseUtilisateur() : "" %>">
            </div>

            <div class="field">
                <label for="telephone">Téléphone</label>
                <input type="text" id="telephone" name="telephone" value="<%= joueur.getTelephoneUtilisateur() != null ? joueur.getTelephoneUtilisateur() : "" %>">
            </div>

            <div class="btn-group">
                <button type="submit" class="btn btn-primary">Enregistrer</button>
                <a href="<%= contextPath %>/joueur/profil" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>
<div class="floating-panel-trigger">
    <button type="button" class="btn secondary" onclick="FloatingWindow && FloatingWindow.open()">Messages</button>
</div>
<jsp:include page="/jsp/components/floatingWindow.jsp"/>
</body>
</html>
