<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="model.Groupe, model.Joueur, model.Evenement"%>
<%@ page import="java.util.List"%>
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" />
<style>
.pill {
	display: inline-block;
	padding: 4px 10px;
	border-radius: 999px;
	font-size: 12px;
	color: #fff;
}

.pill.blue {
	background: var(--primary);
}

.pill.green {
	background: var(--success);
}

.pill.pink {
	background: #f472b6;
}

.section-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
	gap: 1rem;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 0.75rem;
	font-size: 0.9rem;
}

th, td {
	padding: 0.5rem;
	border-bottom: 1px solid var(--border);
	text-align: left;
}

.empty {
	color: var(--text-muted);
	font-style: italic;
}



.links {
	display: flex;
	gap: 0.5rem;
	flex-wrap: wrap;
	margin-top: 0.75rem;
}

.badge {
	display: inline-block;
	padding: 3px 8px;
	border-radius: 9999px;
	font-size: 11px;
	background-color: #e5f2ff;
	color: #1d4ed8;
	margin-left: 6px;
}

.section-grid > .card > h2:first-of-type {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 6px;
}

.section-grid > .card > .pill {
  display: inline-flex;  
  align-items: center;
  vertical-align: middle;
  margin: 0;  
}

.section-grid > .card > h2:first-of-type + .pill {
  margin-left: 10px;
  transform: translateY(-2px);
}

.section-grid > .card > h2 {
  position: relative;
}

.section-grid > .card > p + .badge {
  float: right;
  margin-top: -44px; 
}

.section-grid > .card > p + .badge::after {
  content: "";
  display: block;
  clear: both;
}

.section-grid > .card > table + .links {
  justify-content: center;
}

.section-grid > .card > h2:not(:first-of-type) {
  padding-left: 14px;
  margin-top: 22px;
}

.section-grid > .card > h2:not(:first-of-type)::before {
  content: "";
  position: absolute;
  left: 0;
  top: 50%;
  width: 4px;
  height: 70%;
  transform: translateY(-50%);
  border-radius: 999px;
  background: var(--primary);
}

.section-grid > .card > h2:not(:first-of-type)::after {
  content: "";
  position: absolute;
  left: -2px;
  top: 50%;
  width: 8px;
  height: 8px;
  transform: translateY(-50%);
  border-radius: 999px;
  background: #c7d2fe;
}

.section-grid > .card > h2 + p {
  margin-top: 6px;
}

.center-stat {
    justify-content: center;
}

</style>
</head>
<body class="page">
     <%@ include file="/jsp/header.jspf" %>
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
				</div>
			</div>

			<div class="section-grid">
				<c:if test="${not empty roles and fn:contains(roles, 'Coach')}">
					<%
					List<Evenement> evenements = (List<Evenement>) request.getAttribute("evenements");
					List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupes");
					%>
					<div class="card">
						<h2>Bienvenue, Coach ${sessionScope.user.prenomUtilisateur}
							${sessionScope.user.nomUtilisateur}!</h2>
						<div class="pill blue">Coach</div>

						<!-- Section Evenements -->
						<h2>Évènements officiels à venir</h2>
						<p>Vous pouvez choisir ou modifier les convocations.</p>
						<%
						if (evenements != null) {
						%>
						<span class="badge"><%=evenements.size()%> évènement(s)</span>
						<%
						}
						%>
						<table>
							<thead>
								<tr>
									<th>Titre</th>
									<th>Date</th>
									<th>Type</th>
									<th>Groupe</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<%
								if (evenements != null && !evenements.isEmpty()) {
									for (Evenement e : evenements) {
								%>
								<tr>
									<td><%=e.getNomEvenement()%></td>
									<td><%=e.getDateEvenement()%></td>
									<td><%=e.getTypeEvenement()%></td>
									<td>
										<%
										if (e.getGroupe() != null) {
										%> <%=e.getGroupe().getNomGroupe()%> <%
 } else {
 %> <span class="empty-msg">Aucun groupe convoqué.</span> <%
 }
 %>
									</td>
									<td>
										<form action="CtrlConvoquer" method="get" class="links">
											<input type="hidden" name="action" value="selectionEvenement">
											<input type="hidden" name="idEvenement"
												value="<%=e.getIdEvenement()%>">
											<button type="submit" class="btn">À convoquer</button>
										</form>
									</td>
								</tr>
								<%
								}
								} else {
								%>
								<tr>
									<td colspan="4" class="empty-msg">Aucun évènement officiel
										à venir.</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>

						<!-- Section Groupes -->
						<h2>Groupes existants</h2>
						<p>Vue simple des groupes existants.</p>
						<%
						if (groupes != null) {
						%>
						<span class="badge"><%=groupes.size()%> groupe(s)</span>
						<%
						}
						%>


						<table>
							<thead>
								<tr>
									<th>Nom du groupe</th>
									<th>Joueurs</th>
								</tr>
							</thead>
							<tbody>
								<%
								if (groupes != null && !groupes.isEmpty()) {
									for (Groupe g : groupes) {
								%>
								<tr>
									<td><%=g.getNomGroupe()%></td>
									<td>
										<%
										List<Joueur> joueurs = g.getJoueurs();
										if (joueurs != null && !joueurs.isEmpty()) {
											for (int i = 0; i < joueurs.size(); i++) {
												Joueur j = joueurs.get(i);
										%> <%=j.getPrenomUtilisateur()%> <%=j.getNomUtilisateur()%><%=(i < joueurs.size() - 1) ? ", " : ""%>
										<%
										}
										} else {
										%> <span class="empty-msg">Aucun joueur dans ce groupe.</span>
										<%
										}
										%>
									</td>
								</tr>
								<%
								}
								} else {
								%>
								<tr>
									<td colspan="2" class="empty-msg">Aucun groupe trouvé.</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
						<div class="links">
							<a class="btn" href="CtrlCoach?action=GestionGroupe">Gestion
								des groupes</a>
						</div>
						<!-- ================= STATISTIQUES ================= -->
<h2>Statistiques</h2>
<p>Analyse du taux de présence des joueurs aux entraînements.</p>

<div class="links center-stat">
    <a class="btn"
       href="${pageContext.request.contextPath}/CtrlStatistique?action=presenceEntrainement">
        📊 Taux de présence aux entraînements
    </a>
</div>

						
					</div>
				</c:if>
    <c:if test="${not empty roles and fn:contains(roles, 'Parent')}">
        <div class="card">
            <div class="pill green">Parent</div>
            <h2>Services Parent</h2>
            <p>Accès rapide aux covoiturages, convocations et profil.</p>
            <div class="links">
                <a class="btn" href="${pageContext.request.contextPath}/parent/covoiturage">Covoiturage</a>
                <a class="btn" href="${pageContext.request.contextPath}/parent/profil">Profil parent</a>
                <a class="btn" href="${pageContext.request.contextPath}/CtrlFrontAbsence">Gérer les absences</a>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty roles and fn:contains(roles, 'Secretaire')}">
        <div class="card">
            <div class="pill pink">Secrétaire</div>
            <h2>Administration</h2>
            <p>Créer/éditer des comptes et gérer les événements.</p>
            <div class="links">
                <a class="btn" href="${pageContext.request.contextPath}/evenementSecre">Gestion des événements</a>
                <a class="btn" href="${pageContext.request.contextPath}/secretaire/profil">Gestion des profils</a>
                <a class="btn" href="${pageContext.request.contextPath}/secretaire/convoquer?type=match">Convoquer les Matches</a>
                <a class="btn" href="${pageContext.request.contextPath}/secretaire/enregistrement">Enregister les entraintements</a>

            </div>
            <div style="margin-top:8px;">
        </div>
    </c:if>

    <c:if test="${not empty roles and fn:contains(roles, 'Joueur')}">
        <div class="card">
            <div class="pill blue">Joueur</div>
            <h2>Vue Joueur</h2>
            <p>Consultez vos convocations et informations d'équipe.</p>
            <div class="links">
                <a class="btn" href="${pageContext.request.contextPath}/joueur/profil">Ma profil</a>
            </div>
        </div>
    </c:if>
        </div>
    </div>
</div>
</body>
</html>
