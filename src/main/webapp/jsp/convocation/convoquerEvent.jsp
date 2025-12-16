<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Convocations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/evenements.css">
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="top-actions">
    <a href="${ctx}/jsp/home.jsp" class="btn btn-retour">⬅ Retour à l’accueil</a>
    <form method="post" action="${ctx}/CtrlLogout" class="logout-form">
        <button type="submit" class="btn btn-logout">🚪 Déconnexion</button>
    </form>
</div>

<h2 class="page-title">
    Convocations - <c:out value="${mode == 'match' ? 'Matches' : 'Autres événements'}" />
</h2>

<div class="box">
    <div class="actions-row">
        <span>Sélectionnez le type d’événement :</span>
        <a class="btn ${mode == 'match' ? 'btn-primary' : ''}" href="${ctx}/secretaire/convoquer?type=match">Matches</a>
        <a class="btn ${mode != 'match' ? 'btn-primary' : ''}" href="${ctx}/secretaire/convoquer?type=event">Autres événements</a>
    </div>

    <c:choose>
        <c:when test="${empty evenements}">
            <p class="empty-text">Aucun événement correspondant.</p>
        </c:when>
        <c:otherwise>
            <div class="events-grid">
                <c:forEach var="evt" items="${evenements}">
                    <div class="event-card">
                        <div class="event-head">
                            <div>
                                <h3 class="event-title">
                                    <c:out value="${evt.nomEvenement}" />
                                </h3>
                                <div class="event-meta">
                                    <div><strong>Lieu :</strong> <c:out value="${evt.lieuEvenement}" /></div>
                                    <div><strong>Date :</strong> <c:out value="${evt.dateEvenement}" /></div>
                                    <c:if test="${not empty evt.groupe}">
                                        <div><strong>Groupe :</strong> <c:out value="${evt.groupe.nomGroupe}" /></div>
                                    </c:if>
                                </div>
                            </div>
                            <span class="pill"><c:out value="${evt.typeEvenement}" /></span>
                        </div>

                        <div class="players">
                            <c:choose>
                                <c:when test="${not empty evt.groupe and not empty evt.groupe.joueurs}">
                                    <c:forEach var="j" items="${evt.groupe.joueurs}">
                                        <div class="player-badge">
                                            <c:out value="${j.prenomUtilisateur}" /> <c:out value="${j.nomUtilisateur}" />
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <p class="empty-text">Aucun joueur associé pour l’instant.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="event-actions">
                            <form method="post" action="${ctx}/secretaire/convoquer">
                                <input type="hidden" name="idEvenement" value="${evt.idEvenement}">
                                <input type="hidden" name="type" value="${mode}">
                                <button type="submit" class="btn btn-primary">Convoquer</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
