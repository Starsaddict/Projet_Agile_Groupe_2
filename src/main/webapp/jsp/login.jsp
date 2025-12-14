<!-- File: `src/main/webapp/jsp/login.jsp` -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Connexion</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body class="page index">
  <div class="container card">
    <h2>Se connecter</h2>

    <c:if test="${not empty requestScope.error or not empty param.error}">
      <div class="alert error">
        ${requestScope.error != null ? requestScope.error : param.error}
      </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/CtrlLogin" accept-charset="UTF-8">
      <div class="field">
        <label for="email">Adresse e-mail</label>
        <input id="email" name="email" type="email" required maxlength="254" />
      </div>

      <div class="field">
        <label for="password">Mot de passe</label>
        <input id="password" name="password" type="password" required  />
      </div>

      <button type="submit" class="btn">Connexion</button>
    </form>

    <c:if test="${not empty msg_connection}">
      <div class="alert error">${msg_connection}</div>
    </c:if>

  </div>
</body>
</html>
