<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Evenement"%>
<%@ page import="model.Groupe"%>
<%@ page import="model.Joueur"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sélection des groupes</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<style>
/* Ajustements spécifiques */
.page-header {
	display: flex;
	justify-content: space-between;
	align-items: flex-start;
	margin-bottom: 16px;
}
.header-actions { display: flex; gap: 10px; }
.section-card {
	background: var(--bg-card);
	border: 1px solid var(--border);
	border-radius: var(--radius);
	padding: 1rem 1.25rem;
	box-shadow: var(--shadow-sm);
	margin-bottom: 16px;
}
.section-card h2 { margin-bottom: 0.5rem; }
table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 12px;
	font-size: 14px;
}
table thead th {
	background-color: var(--primary);
	color: #ffffff;
	text-align: left;
	padding: 8px 10px;
}
table tbody td {
	border-bottom: 1px solid var(--border);
	padding: 8px 10px;
	vertical-align: top;
}
table tbody tr:nth-child(even) { background-color: #f9fafb; }
table tbody tr:hover { background-color: #eef2ff; }
.btn-row {
    display: flex;
    justify-content: center; 
    align-items: center;
    gap: 14px;
    margin-top: 20px;
}
.empty-msg { color: var(--text-muted); font-style: italic; }
hr { border: none; border-top: 1px solid var(--border); margin: 20px 0; }
tr.absent-row { background: #fee2e2 !important; }
tr.absent-row:hover { background: #fecaca !important; }
.absent-badge {
	display: inline-block;
	padding: 2px 8px;
	border-radius: 999px;
	font-size: 12px;
	font-weight: 700;
	color: #991b1b;
	background: #ffe4e6;
	margin-left: 8px;
}
.absent-hint { color: #991b1b; font-size: 12px; margin-top: 6px; }

/* ===== Messages succès / erreur ===== */
.success {
    background: #d1fae5;        /* vert clair */
    color: #065f46;             /* vert foncé */
    border-left: 4px solid #10b981;
    padding: 12px 16px;
    border-radius: 6px;
    margin: 12px 0;
    font-weight: 500;
}

.error {
    background: #fee2e2;        /* rouge clair */
    color: #991b1b;             /* rouge foncé */
    border-left: 4px solid #ef4444;
    padding: 12px 16px;
    border-radius: 6px;
    margin: 12px 0;
    font-weight: 500;
}

</style>

</head>
<body>
<%@ include file="/jsp/header.jspf" %>
	<div class="container">
		<div class="page-header">
			<div>
				<h1>Sélection des groupes à convoquer</h1>
			</div>
		</div>

		<%
		String messageSucces = (String) request.getAttribute("messageSucces");
		String messageErreur = (String) request.getAttribute("messageErreur");

		if (messageSucces != null) {
		%>
		<p class="success"><%=messageSucces%></p>
		<%
		}
		if (messageErreur != null) {
		%>
		<p class="error"><%=messageErreur%></p>
		<%
		}

		Evenement evt = (Evenement) request.getAttribute("evenementSelectionne");
		%>

		<div class="section-card">
		<h2>Événement sélectionné</h2>

		<%
		if (evt == null) {
		%>
		<p class="empty-msg">Aucun événement sélectionné.</p>
		<%
		} else {
		%>
		<p>
			<strong>ID :</strong>
			<%=evt.getIdEvenement()%></p>
		<p>
			<strong>Nom :</strong>
			<%=evt.getNomEvenement()%></p>
		<p>
			<strong>Date / Heure :</strong>
			<%=evt.getDateEvenement()%></p>
		<%
		}
		%>

		</div>

		<div class="section-card">
		<h2>Choisissez les groupes à convoquer</h2>

		<%
		List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupesCoach");

		if (groupes == null || groupes.isEmpty()) {
		%>
		<p class="empty-msg">Aucun groupe disponible.</p>
		<%
		} else if (evt != null) {

		Groupe groupeSelectionne = evt.getGroupe();

		if (groupeSelectionne != null) {
		%>
		<p>
			<strong>Groupe déjà convoqué :</strong>
			<%=groupeSelectionne.getIdGroupe()%>
			-
			<%=groupeSelectionne.getNomGroupe()%>
		</p>
		<%
		} else {
		%>
		<p class="empty-msg">Aucun groupe n'a encore été convoqué pour cet
			événement.</p>
		<%
		}
		%>

		<form action="CtrlConvoquer" method="post">
			<input type="hidden" name="action" value="validerConvocation">
			<input type="hidden" name="idEvenement"
				value="<%=evt.getIdEvenement()%>">

			<table>
				<thead>
					<tr>
						<th>Sélection</th>
						<th>ID Groupe</th>
						<th>Nom du groupe</th>
						<th>Membres</th>
					</tr>
				</thead>
				<%@ page import="java.util.Set"%>
				<%@ page import="java.util.Map"%>

				<%
				Set<Long> groupesIndisponibles = (Set<Long>) request.getAttribute("groupesIndisponibles");
				Map<Long, String> detailAbsencesParGroupe = (Map<Long, String>) request.getAttribute("detailAbsencesParGroupe");

				if (groupesIndisponibles == null)
					groupesIndisponibles = java.util.Collections.emptySet();
				if (detailAbsencesParGroupe == null)
					detailAbsencesParGroupe = java.util.Collections.emptyMap();
				%>

				<tbody>
					<%
					for (Groupe g : groupes) {
						boolean checked = false;

						if (groupeSelectionne != null && groupeSelectionne.getIdGroupe() != null
						&& groupeSelectionne.getIdGroupe().equals(g.getIdGroupe())) {
							checked = true;
						}

						boolean disabled = groupesIndisponibles.contains(g.getIdGroupe());
						String detail = detailAbsencesParGroupe.get(g.getIdGroupe());
					%>

					<tr class="<%=disabled ? "absent-row" : ""%>">
						<td><input type="radio" name="idGroupe"
							value="<%=g.getIdGroupe()%>" <%=checked ? "checked" : ""%>
							<%=disabled ? "disabled" : ""%>></td>

						<td><%=g.getIdGroupe()%></td>

						<td><%=g.getNomGroupe()%> <%
 if (disabled) {
 %> <span
							class="absent-badge">Absent</span> <%
 if (detail != null && !detail.isBlank()) {
 %>
							<div class="absent-hint"><%=detail%></div> <%
 }
 %> <%
 }
 %></td>

						<td>
							<%
							for (Joueur j : g.getJoueurs()) {
							%>
							<%= j.getNomUtilisateur() %> <%= j.getPrenomUtilisateur() %><br />
							<%
							}
							%>

						</td>
					</tr>

					<%
					}
					%>

				</tbody>
			</table>

			<div class="btn-row">
				<button type="submit" class="btn">Valider</button>
			</div>

		</form>

		<%
		}
		%>
		</div>
	</div>

</body>
</html>
