<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Déclarer une absence</title>
</head>
<body>

<h2>Mes enfants</h2>

<c:if test="${not empty requestScope.msg_absence}">
    <div style="padding:10px;background:#f0f0f0;margin-bottom:15px;">
        ${requestScope.msg_absence}
    </div>
</c:if>

<c:if test="${empty sessionScope.user.joueurs}">
    <p>Aucun enfant associé à votre compte.</p>
</c:if>

<c:forEach var="enfant" items="${sessionScope.user.joueurs}">
    <div style="padding:10px;border:1px solid #ddd;margin-bottom:10px;">
        <strong>${enfant.nomUtilisateur} ${enfant.prenomUtilisateur}</strong>

        <c:set var="isAbsent" value="${false}" />
        <c:forEach var="absence" items="${enfant.absences}">
            <c:if test="${not absence.absenceTerminee}">
                <c:set var="isAbsent" value="${true}" />
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${isAbsent}">
                <span style="margin-left:15px; color: grey;">(Déjà absent)</span>
            </c:when>
            <c:otherwise>
                <form method="post" action="CtrlAbsence" style="display:inline;margin-left:15px;">
                    <input type="hidden" name="id_enfant" value="${enfant.idUtilisateur}"/>
                    <button type="submit">Déclarer l'absence</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</c:forEach>

</body>
</html>