<%--
  Created by IntelliJ IDEA.
  User: kaiyangzhang
  Date: 2025/12/9
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accueil</title>
</head>
<body>

    <p>Bienvenue !</p>

    <p>Se connecter en tant que :</p>
        <ul>
            <li><a href="CtrlFrontLogin?role=Joueur">Joueur</a></li>
            <li><a href="CtrlFrontLogin?role=Coach">Coach</a></li>
            <li><a href="CtrlFrontLogin?role=Parent">Parent</a></li>
            <li><a href="CtrlFrontLogin?role=Secretaire">Secrétaire</a></li>
        </ul>

    ${message != null ? message : ""}


</body>
</html>
