<!-- File: `src/main/webapp/jsp/secretaire/profil.jsp` -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Profil Secretaire</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body class="page secretaire-profil">
  <div class="container card">
    <h1>Profil secrétaire</h1>
    <% String success = request.getParameter("success"); %>
    <% String batchSuccess = (String) request.getAttribute("batchSuccess"); %>
    <% if ("1".equals(success)) { %>
        <div class="alert success">Compte famille créé avec succès</div>
    <% } %>
    <% if (batchSuccess != null) { %>
        <div class="alert success"><%= batchSuccess %></div>
    <% } %>
    <ul>
        <li><a href="<%=request.getContextPath()%>/secretaire/profil/creerCompteFamile">Créer une famille</a></li>
        <li><a href="<%=request.getContextPath()%>/secretaire/profil/modifier">Modifier un compte</a></li>
        <li><a href="<%=request.getContextPath()%>/secretaire/profil/batchCreate">batch create ( avec Excel )</a></li>
    </ul>
  </div>
</body>
</html>