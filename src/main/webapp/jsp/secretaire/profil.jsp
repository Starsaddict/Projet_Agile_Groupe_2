<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil Secretaire</title>
</head>
<body>
    <h1>Profil secrétaire</h1>
    <% String success = request.getParameter("success"); %>
    <% if ("1".equals(success)) { %>
        <div style="color:green;">Compte famille créé avec succès</div>
    <% } %>
    <ul>
        <li><a href="<%=request.getContextPath()%>/secretaire/profil/creerCompteFamile">Créer une famille</a></li>
        <li><a href="<%=request.getContextPath()%>/secretaire/profil/modifier">Modifier un compte</a></li>
    </ul>
</body>
</html>
