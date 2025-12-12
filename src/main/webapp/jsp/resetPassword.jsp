<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Réinitialiser le mot de passe</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/secretaire-profil.css">
</head>
<body>
<div class="container">
    <h1>Réinitialiser le mot de passe</h1>

    <div class="form-card">
        <c:if test="${not empty error}">
            <div class="alert error">${error}</div>
        </c:if>

        <c:if test="${not empty utilisateur}">
            <div class="info-box">
                Mise à jour pour <strong>${utilisateur.prenomUtilisateur} ${utilisateur.nomUtilisateur}</strong>
                (${utilisateur.emailUtilisateur})
            </div>
            <form method="post" action="${pageContext.request.contextPath}/resetPassword">
                <input type="hidden" name="uid" value="${uid}" />
                <div class="field">
                    <label for="newPassword">Nouveau mot de passe</label>
                    <input type="password" id="newPassword" name="newPassword" required />
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn">Enregistrer</button>
                </div>
            </form>
        </c:if>

        <c:if test="${empty utilisateur and empty error}">
            <div class="alert error">Utilisateur introuvable.</div>
        </c:if>
    </div>
</div>
</body>
</html>
