<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("Login");
        return;
    }
%>
<c:set var="roles" value="${sessionScope.roles}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Accueil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <style>
        .pill { display: inline-block; padding: 4px 10px; border-radius: 999px; font-size: 12px; color: #fff; }
        .pill.blue { background: var(--primary); }
        .pill.green { background: var(--success); }
        .pill.pink { background: #f472b6; }
        .section-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(260px, 1fr)); gap: 1rem; }
        table { width: 100%; border-collapse: collapse; margin-top: 0.75rem; font-size: 0.9rem; }
        th, td { padding: 0.5rem; border-bottom: 1px solid var(--border); text-align: left; }
        .empty { color: var(--text-muted); font-style: italic; }
        .top-bar { display: flex; justify-content: space-between; align-items: center; gap: 1rem; margin-bottom: 1rem; }
        .top-bar p { margin: 0; color: var(--text-muted); }
        .links { display: flex; gap: 0.5rem; flex-wrap: wrap; margin-top: 0.75rem; }
    </style>
</head>
<body class="page">
<div class="container">
    <div class="card">
        <div class="top-bar">
            <div>
                <h1>Tableau de bord</h1>
                <p>Contenus affichés selon vos rôles</p>
            </div>
            <div>
                <c:if test="${not empty sessionScope.user}">
                    <span class="pill blue">${sessionScope.user.emailUtilisateur}</span>
                </c:if>
                <form action="CtrlLogout" method="get" style="display: inline;">
                    <button class="btn secondary" type="submit">Déconnexion</button>
                </form>
            </div>
        </div>

        <div class="section-grid">
    <c:if test="${not empty roles and fn:contains(roles, 'Coach')}">
        <div class="card">
            <div class="pill blue">Coach</div>
            <h2>Vos prochains matchs</h2>
            <p>Vue rapide sur les évènements officiels à venir.</p>
            <table>
                <thead>
                <tr><th>Titre</th><th>Date</th><th>Type</th><th>Groupe</th></tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty requestScope.evenements}">
                        <c:forEach var="evt" items="${requestScope.evenements}" end="4">
                            <tr>
                                <td>${evt.nomEvenement}</td>
                                <td>${evt.dateEvenement}</td>
                                <td>${evt.typeEvenement}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty evt.groupe}">
                                            ${evt.groupe.nomGroupe}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="empty">Aucun groupe</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="4" class="empty">Pas d'évènement à afficher.</td></tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <div class="links">
                <a class="btn" href="CtrlCoach?action=PageCoach">Espace Coach</a>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty roles and fn:contains(roles, 'Parent')}">
        <div class="card">
            <div class="pill green">Parent</div>
            <h2>Services Parent</h2>
            <p>Accès rapide aux covoiturages, convocations et profil.</p>
            <div class="links">
                <a class="btn" href="${pageContext.request.contextPath}/jsp/covoiturage.jsp">Covoiturage</a>
                <a class="btn" href="${pageContext.request.contextPath}/jsp/convocation.jsp">Convocations</a>
                <a class="btn" href="${pageContext.request.contextPath}/parent/profil">Profil parent</a>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty roles and fn:contains(roles, 'Secretaire')}">
        <div class="card">
            <div class="pill pink">Secrétaire</div>
            <h2>Administration</h2>
            <p>Créer/éditer des comptes et gérer les événements.</p>
            <div class="links">
                <a class="btn" href="${pageContext.request.contextPath}/evenementSecre"> Gérer les événements</a>
                <a class="btn" href="${pageContext.request.contextPath}/secretaire/profil">Gestion des profils</a>
            </div>
            <div style="margin-top:8px;">
                <form method="post" action="${pageContext.request.contextPath}/secretaire/profil/sendInvitation" style="display:flex;gap:8px;align-items:center;">
                    <input type="email" name="email" placeholder="Envoyer code à email parent" required />
                    <button type="submit" class="btn">Envoyer</button>
                </form>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty roles and fn:contains(roles, 'Joueur')}">
        <div class="card">
            <div class="pill blue">Joueur</div>
            <h2>Vue Joueur</h2>
            <p>Consultez vos convocations et informations d'équipe.</p>
            <div class="links">
                <a class="btn" href="${pageContext.request.contextPath}/jsp/convocation.jsp">Mes convocations</a>
            </div>
        </div>
    </c:if>
        </div>
    </div>
</div>

</body>
</html>
