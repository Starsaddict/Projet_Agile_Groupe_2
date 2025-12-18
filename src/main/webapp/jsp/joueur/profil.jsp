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
<html>
<head>
    <title>Mon Profil - Joueur</title>
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
        <div class="action-buttons" style="justify-content: space-between; align-items: center;">
            <a href="<%= contextPath %>/jsp/home.jsp" class="btn secondary">⬅ Retour à l’accueil</a>
            <form action="<%= contextPath %>/CtrlLogout" method="post" style="margin:0;">
                <button type="submit" class="btn danger">🚪 Déconnexion</button>
            </form>
            </div>


            <h1>Mon Profil</h1>

        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>

        <% if ("1".equals(success)) { %>
            <div class="alert success">Profil mis à jour avec succès.</div>
        <% } %>

        <div class="card" style="margin-top:16px;">
            <h2><%= joueur.getPrenomUtilisateur() %> <%= joueur.getNomUtilisateur() %> <span style="font-size: 0.75em; color: var(--text-muted);">(Joueur)</span></h2>
            <p><strong>Email:</strong> <%= joueur.getEmailUtilisateur() != null ? joueur.getEmailUtilisateur() : "-" %></p>
            <p><strong>Numéro licence:</strong> <%= joueur.getNumeroJoueur() != null ? joueur.getNumeroJoueur() : "-" %></p>
            <p><strong>Adresse:</strong> <%= joueur.getAdresseUtilisateur() != null ? joueur.getAdresseUtilisateur() : "-" %></p>
            <p><strong>Téléphone:</strong> <%= joueur.getTelephoneUtilisateur() != null ? joueur.getTelephoneUtilisateur() : "-" %></p>
            <div class="action-buttons">
                <a href="<%= contextPath %>/joueur/profil/edit" class="btn">Modifier</a>
            </div>
        </div>
    </div>
</div>
<div class="floating-panel-trigger">
    <button type="button" class="btn secondary" onclick="FloatingWindow && FloatingWindow.open()">Messages</button>
</div>
<jsp:include page="/jsp/components/floatingWindow.jsp"/>
</body>
</html>
