<!-- File: `src/main/webapp/jsp/home.jsp` -->
<html lang="fr">
<head>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"
  %>
  <meta charset="UTF-8"/>
  <title>Accueil</title>
  <style>
    body{font-family:Arial,Helvetica,sans-serif;background:#f5f5f5;padding:40px}
    .card{max-width:480px;margin:40px auto;background:#fff;padding:20px;border-radius:6px;box-shadow:0 2px 8px rgba(0,0,0,0.06)}
  </style>
</head>
<body>
  <div class="card">
    <h2>${welcomeMsg}</h2>

    <c:choose>
      <c:when test="${sessionScope.user.typeU == 'Joueur'}">
        <p>Accès rapide : <a href="/joueur/dashboard">Mon tableau de bord</a></p>
      </c:when>
      <c:when test="${sessionScope.user.typeU == 'Parent'}">
        <p>Accès parent : 
          <a href="<%= request.getContextPath() %>/parent/profil">Mes profils</a> | 
          <a href="/parent/enfants">Mes enfants</a>
        </p>
      </c:when>
      <c:when test="${sessionScope.user.typeU == 'Coach'}">
        <p>Accès coach : <a href="/coach/agenda">Mon agenda</a></p>
      </c:when>
      <c:when test="${sessionScope.user.typeU == 'Secretaire'}">
        <p>Accès secrétaire : <a href="/secretaire/profil">Profil secrétariat</a></p>
      </c:when>
      <c:otherwise>
        <p>Vous n'êtes pas connecté. <a href="CtrlFrontLogin">Se connecter</a></p>
      </c:otherwise>
    </c:choose>

    <c:if test="${not empty sessionScope.user}">
      <p><a href="CtrlLogout">Se déconnecter</a></p>
    </c:if>
  </div>
</body>
</html>