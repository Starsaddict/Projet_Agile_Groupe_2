<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Accueil</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" />
</head>
<body class="page index">
  <div class="container">
    <div class="card">
      <h1>Se connecter en tant que</h1>

      <% String message = (String) request.getAttribute("message");
         if (message != null && !message.isEmpty()) { %>
          <div class="alert error"><%= message %></div>
      <% } %>

      <div class="role-list">
        <a href="CtrlFrontLogin?role=Joueur">Joueur</a>
        <a href="CtrlFrontLogin?role=Coach">Coach</a>
        <a href="CtrlFrontLogin?role=Parent">Parent</a>
        <a href="CtrlFrontLogin?role=Secretaire">Secrétaire</a>
      </div>
    </div>
  </div>
</body>
</html>