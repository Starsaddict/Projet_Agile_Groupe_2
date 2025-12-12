<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Convocation des groupes</title>
</head>
<body>
    <h1>Convocation des groupes</h1>

    <h2>Événements à venir</h2>

    <c:choose>
        <c:when test="${empty evenements}">
            <p>Aucun événement à venir.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="5" cellspacing="0">
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Date</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="e" items="${evenements}">
                    <tr>
                        <td>${e.idEvenement}</td>
                        <td>${e.nomEvenement}</td>
                        <td>${e.dateEvenement}</td>
                        <td>
                            <form action="CtrlConvoquer" method="get">
                                <input type="hidden" name="action" value="selectionEvenement">
                                <input type="hidden" name="idEvenement" value="${e.idEvenement}">
                                <button type="submit">À convoquer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

    <br>
    <form action="CtrlCoach" method="get">
        <input type="hidden" name="action" value="PageCoach">
        <button type="submit">Retour à l'accueil Coach</button>
    </form>
</body>
</html>
