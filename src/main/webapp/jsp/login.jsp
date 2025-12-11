<!DOCTYPE html>
<html lang="fr">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Connexion</title>
  <style>
    body { font-family: Arial, Helvetica, sans-serif; background:#f5f5f5; }
    .container { max-width:420px;margin:80px auto;background:#fff;padding:24px;border-radius:6px;box-shadow:0 2px 8px rgba(0,0,0,0.06); }
    .field { margin-bottom:12px; }
    label { display:block;margin-bottom:6px;font-weight:600;font-size:0.95rem; }
    input[type="email"], input[type="password"] { width:100%;padding:10px;border:1px solid #ccc;border-radius:4px;font-size:1rem; }
    .btn { display:inline-block;padding:10px 16px;background:#1677ff;color:#fff;border:none;border-radius:4px;cursor:pointer;font-weight:600; }
    .error { background:#ffecec;color:#a00;padding:10px;border-radius:4px;margin-bottom:12px;border:1px solid #f5c2c2; }
    .note { font-size:0.9rem;color:#555;margin-top:12px; }
  </style>
</head>
<body>
  <div class="container">
    <h2>Se connecter en tant que ${role_connexion}</h2>

    <c:if test="${not empty requestScope.error or not empty param.error}">
      <div class="error">
        ${requestScope.error != null ? requestScope.error : param.error}
      </div>
    </c:if>

    <form method="post" action="CtrlLogin" accept-charset="UTF-8">
      <div class="field">
        <label for="email">Adresse e-mail</label>
        <input id="email" name="email" type="email" required maxlength="254" />
      </div>

      <div class="field">
        <label for="password">Mot de passe</label>
        <input id="password" name="password" type="password" required  />
      </div>

      <input type="hidden" name="role_connexion" value="${role_connexion}" />

      <button type="submit" class="btn">Connexion</button>
    </form>

    ${msg_connection != null ? msg_connection : ""}

  </div>
</body>
</html>