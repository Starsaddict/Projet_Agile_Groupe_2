<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Utilisateur" %>
<%
    String contextPath = request.getContextPath();
    String error = request.getParameter("error");
    List<Utilisateur> newAccounts = (List<Utilisateur>) request.getAttribute("newAccounts");
    if (newAccounts == null || newAccounts.isEmpty()) {
        response.sendRedirect(contextPath + "/Login?error=Session+expiree");
        return;
    }
%>
<html>
<head>
    <title>Créer votre profil</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <h1>Créer votre profil</h1>
        <p>Pour sécuriser les comptes nouvellement créés, choisissez un mot de passe pour chaque compte listé ci-dessous.</p>

        <% if (error != null && !error.isEmpty()) { %>
            <div class="alert error"><%= error %></div>
        <% } %>

        <form method="post" action="<%= contextPath %>/creerProfil">
            <% for (Utilisateur u : newAccounts) { %>
                <div class="form-section" style="background:#f9f9f9; padding:15px; border-radius:8px; margin-bottom:16px;">
                    <input type="hidden" name="userId" value="<%= u.getIdUtilisateur() %>">
                    <h2 style="margin-top:0;">
                        <%= u.getPrenomUtilisateur() != null ? u.getPrenomUtilisateur() : "" %>
                        <%= u.getNomUtilisateur() != null ? u.getNomUtilisateur() : "" %>
                    </h2>
                    <div class="field">
                        <label>Mot de passe</label>
                        <input type="password" name="mdp" required>
                    </div>
                    <div class="field">
                        <label>Confirmer le mot de passe</label>
                        <input type="password" name="mdpConfirm" required>
                    </div>
                </div>
            <% } %>

            <div class="action-buttons">
                <button type="submit" class="btn">Enregistrer les mots de passe</button>
                <a href="<%= contextPath %>/Login" class="btn secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
