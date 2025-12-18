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
  <div style="display:flex; justify-content:center; align-items:center; min-height:100vh;">
  <div class="container card" style="max-width: 520px; text-align: center;">
    <div style="display:flex; flex-direction:column; align-items:center; gap:8px; margin-bottom:12px;">
      <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo" style="height:96px; width:auto;">
      <h2 style="margin:0;">Se connecter</h2>
    </div>

    <c:if test="${not empty requestScope.error or not empty param.error}">
      <div class="alert error">
        ${requestScope.error != null ? requestScope.error : param.error}
      </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/CtrlLogin" accept-charset="UTF-8">
      <div class="field">
        <label for="email">Adresse e-mail</label>
        <input id="email" name="email" type="email" required maxlength="254" placeholder="exemple@gmail.com"/>
      </div>

      <div class="field">
        <label for="password">Mot de passe</label>
        <input id="password" name="password" type="password" placeholder="Votre mot de passe" required  />
      </div>

      <button type="submit" class="btn" style="width:100%;">Connexion</button>
    </form>

    <c:if test="${not empty msg_connection}">
      <div class="alert error">${msg_connection}</div>
    </c:if>

  </div>
  </div>
</body>
</html>
