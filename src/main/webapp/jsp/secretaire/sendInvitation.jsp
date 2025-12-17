<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String error = request.getParameter("error");
    String success = request.getParameter("success");
%>
<html>
<head>
    <title>Envoyer une invitation</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <div class="action-buttons" style="justify-content: space-between; align-items: center;">
            <h1 style="margin:0;">Inviter un parent</h1>
            <a href="<%= contextPath %>/secretaire/profil" class="btn secondary">← Retour</a>
        </div>

        <p>Renseignez l'email du parent pour lui envoyer un code d'inscription. Il pourra ensuite créer son compte et ceux de ses enfants.</p>

        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>
        <% if ("invitation".equals(success)) { %>
            <div class="alert success">Invitation envoyée avec succès.</div>
        <% } %>

        <form method="post" action="<%= contextPath %>/secretaire/profil/sendInvitation" class="card" style="margin-top:12px; background:#f9f9f9;">
            <div class="field">
                <label>Email du parent</label>
                <input type="email" name="email" placeholder="parent@example.com" required style="width:100%;">
            </div>
            <div class="action-buttons">
                <button type="submit" class="btn">Envoyer l'invitation</button>
                <a href="<%= contextPath %>/secretaire/profil" class="btn secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
