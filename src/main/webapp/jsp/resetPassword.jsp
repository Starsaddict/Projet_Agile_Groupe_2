<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Réinitialiser le mot de passe</title>
    <style>
        body { font-family: Arial, Helvetica, sans-serif; background:#f7f7f7; margin:0; padding:0; }
        .container { max-width:480px; margin:80px auto; background:#fff; padding:24px; border-radius:8px; box-shadow:0 4px 16px rgba(0,0,0,0.08); }
        h2 { margin-top:0; }
        .info { color:#444; margin-bottom:12px; }
        .field { margin-bottom:14px; }
        label { display:block; margin-bottom:6px; font-weight:600; }
        input[type="password"] { width:100%; padding:10px; border:1px solid #ccc; border-radius:4px; font-size:1rem; }
        .btn { display:inline-block; padding:10px 16px; background:#2b7cff; color:#fff; border:none; border-radius:4px; cursor:pointer; font-weight:600; }
        .alert { padding:10px 12px; border-radius:4px; margin-bottom:12px; }
        .alert.error { background:#ffecec; color:#a40000; border:1px solid #f5c2c2; }
        .alert.success { background:#ecffef; color:#0c6b1f; border:1px solid #b9f5c5; }
    </style>
</head>
<body>
<div class="container">
    <h2>Réinitialiser le mot de passe</h2>

    <c:if test="${not empty error}">
        <div class="alert error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert success">${success}</div>
    </c:if>

    <c:if test="${not empty utilisateur}">
        <div class="info">
            Mise à jour pour <strong>${utilisateur.prenomUtilisateur} ${utilisateur.nomUtilisateur}</strong>
            (${utilisateur.emailUtilisateur})
        </div>
        <form method="post" action="${pageContext.request.contextPath}/resetPassword">
            <input type="hidden" name="uid" value="${uid}" />
            <div class="field">
                <label for="newPassword">Nouveau mot de passe</label>
                <input type="password" id="newPassword" name="newPassword" required />
            </div>
            <button type="submit" class="btn">Enregistrer</button>
        </form>
    </c:if>

    <c:if test="${empty utilisateur and empty error}">
        <div class="alert error">Utilisateur introuvable.</div>
    </c:if>
</div>
</body>
</html>
