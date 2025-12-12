<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Groupe, model.Joueur, model.Evenement"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PageCoach</title>
</head>
<body>
	<%
	model.Utilisateur user = (model.Utilisateur) session.getAttribute("user");
	if (user == null) {
		response.sendRedirect("Login");
		return;
	}
	%>

	<h1>
		Bienvenue, Coach
		<%=user.getPrenomUtilisateur()%>
		<%=user.getNomUtilisateur()%>
		!
	</h1>

	<%
	// Récupérer les données envoyées par le contrôleur
	List<Evenement> evenements = (List<Evenement>) request.getAttribute("evenements");
	List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupes");
	%>

	<!-- Affichage des évènements -->
	<h2>Évènements à venir</h2>

	<table border="1" cellpadding="5" cellspacing="0">
		<tr>
			<th>Titre</th>
			<th>Date</th>
			<th>Type</th>
			<th>Groupe</th>
		</tr>

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
			    %> Aucun groupe convoqué. <%
						 }
				 %>
			</td>
		</tr>
		<%
		}
		} else {
		%>
		<tr>
			<td colspan="4">Aucun évènement trouvé.</td>
		</tr>
		<%
		}
		%>
	</table>
	<br>
	<form action="CtrlCoach" method="get">
		<input type="hidden" name="action" value="ConvocationGroupe">
		<button type="submit">Convoquer</button>
	</form>
	<hr>

	<!-- Affichage des groupes et de leurs joueurs -->
	<h2>Groupes existants</h2>
	<table border="1" cellpadding="5" cellspacing="0">
		<tr>
			<th>Nom du groupe</th>
			<th>Joueurs</th>
		</tr>

		<%
		if (groupes != null && !groupes.isEmpty()) {
			for (Groupe g : groupes) {
		%>
		<tr>
			<!-- Nom du groupe -->
			<td><%=g.getNomGroupe()%></td>

			<!-- Liste des joueurs du groupe -->
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
				%> Aucun joueur convoqué.<%
				}
				%>
			</td>
		</tr>
		<%
		}
		} else {
		%>
		<tr>
			<td colspan="2">Aucun groupe trouvé.</td>
		</tr>
		<%
		}
		%>
	</table>
	<br>
	<form action="CtrlCoach" method="get">
		<input type="hidden" name="action" value="GestionGroupe">
		<button type="submit">Créer un groupe</button>
	</form>

</body>
</html>
