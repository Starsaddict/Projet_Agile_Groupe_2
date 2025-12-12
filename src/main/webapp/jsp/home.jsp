<!-- File: `src/main/webapp/jsp/home.jsp` -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Accueil</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body class="page home">
  <div class="container card">
    <h2>${welcomeMsg}</h2>

    <c:choose>
      <c:when test="${sessionScope.user.typeU == 'Joueur'}">
        <p>Accès rapide : <a href="${pageContext.request.contextPath}/joueur/dashboard">Mon tableau de bord</a></p>
      </c:when>
      <c:when test="${sessionScope.user.typeU == 'Parent'}">
        <p>Accès parent : <a href="${pageContext.request.contextPath}/parent/enfants">Mes enfants</a></p>
      </c:when>
      <c:when test="${sessionScope.user.typeU == 'Coach'}">
        <p>Accès coach : <a href="${pageContext.request.contextPath}/coach/agenda">Mon agenda</a></p>
      </c:when>
      <c:when test="${sessionScope.user.typeU == 'Secretaire'}">
        <p>Accès secrétaire : <a href="${pageContext.request.contextPath}/secretaire/profil">Profil secrétariat</a></p>
      </c:when>
      <c:otherwise>
        <p>Vous n'êtes pas connecté. <a href="${pageContext.request.contextPath}/CtrlFrontLogin">Se connecter</a></p>
      </c:otherwise>
    </c:choose>

    <c:if test="${not empty sessionScope.user}">
      <p><a href="${pageContext.request.contextPath}/CtrlLogout">Se déconnecter</a></p>
    </c:if>
  </div>
</body>
</html>
