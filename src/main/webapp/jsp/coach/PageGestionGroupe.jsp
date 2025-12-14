<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Groupe, model.Joueur"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestion des groupes</title>

<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f3f4f6;
	margin: 0;
	padding: 0;
}

.container {
	max-width: 900px;
	margin: 40px auto;
	background-color: #ffffff;
	padding: 30px 40px;
	border-radius: 10px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
}

h1 {
	margin-top: 0;
	font-size: 24px;
	color: #111827;
}

h2 {
	margin-top: 25px;
	font-size: 20px;
	color: #1f2933;
	border-left: 4px solid #2563eb;
	padding-left: 10px;
}

.subtitle {
	color: #6b7280;
	font-size: 14px;
	margin-top: -5px;
	margin-bottom: 20px;
}

label {
	font-weight: 500;
	color: #374151;
}

input[type="text"] {
	padding: 6px 8px;
	border-radius: 6px;
	border: 1px solid #d1d5db;
}

input[type="checkbox"] {
	margin-right: 6px;
}

.players-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-top: 10px;
	margin-bottom: 6px;
}

.players-header input {
	width: 260px;
}

.players-list {
	padding: 10px 12px;
	border-radius: 8px;
	border: 1px solid #e5e7eb;
	max-height: 220px;
	overflow-y: auto;
	background-color: #f9fafb;
	font-size: 14px;
}

.player-item {
	margin-bottom: 4px;
}

.empty-msg {
	color: #6b7280;
	font-style: italic;
}

    .page-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 20px;
}

.header-actions {
    display: flex;
    gap: 10px;
}

.btn-primary, .btn-secondary, .btn-danger {
	border: none;
	padding: 8px 16px;
	border-radius: 6px;
	font-size: 14px;
	cursor: pointer;
}

.btn-primary {
	background-color: #2563eb;
	color: white;
}

.btn-secondary {
	background-color: #e5e7eb;
}

.btn-danger {
	background-color: #ef4444;
	color: white;
}

.btn-row {
	margin-top: 15px;
	display: flex;
	gap: 10px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 15px;
	font-size: 14px;
}

table thead th {
	background-color: #2563eb;
	color: white;
	padding: 8px 10px;
}

table tbody td {
	border-bottom: 1px solid #e5e7eb;
	padding: 8px 10px;
}
</style>
</head>

<body>

	<%
	List<Joueur> joueurs = (List<Joueur>) request.getAttribute("joueurs");
	List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupes");
	%>

	<div class="container">
		<div class="page-header">
        <div>
            <h1>Gestion des groupes</h1>
		<p class="subtitle">Créer un nouveau groupe et gérer les groupes
			existants.</p>
        </div>

        <div class="header-actions">
            <form action="CtrlCoach" method="get">
                <input type="hidden" name="action" value="Home">
                <button type="submit" class="btn-primary">Home</button>
            </form>

            <form action="CtrlLogout" method="get">
                <button type="submit" class="btn-primary">Déconnexion</button>
            </form>
        </div>
    </div> 
		<!-- ===================== Création ===================== -->
		<h2>Créer un groupe</h2>

		<form id="creerGroupeForm" action="CtrlCoach" method="post">
			<input type="hidden" name="action" value="EnregistrerGroupe" />

			<div>
				<label>Nom du groupe :</label> <input type="text" name="nomGroupe"
					required />
			</div>

			<div class="players-header">
				<label>Joueurs :</label> <input type="text" id="searchJoueur"
					placeholder="Rechercher un joueur..." />
			</div>

			<div class="players-list" id="playersList">
				<%
				if (joueurs != null && !joueurs.isEmpty()) {
					for (Joueur j : joueurs) {
						String nom = j.getNomUtilisateur() == null ? "" : j.getNomUtilisateur();
						String prenom = j.getPrenomUtilisateur() == null ? "" : j.getPrenomUtilisateur();
						String search = (nom + " " + prenom + " " + prenom + " " + nom).toLowerCase();
				%>
				<div class="player-item" data-search="<%=search%>">
					<label> <input type="checkbox" name="joueursIds"
						value="<%=j.getIdUtilisateur()%>" /> <%=nom%> <%=prenom%>
					</label>
				</div>
				<%
				}
				} else {
				%>
				<div class="empty-msg">Aucun joueur disponible.</div>
				<%
				}
				%>

				<div id="noResultMsg" class="empty-msg" style="display: none;">
					Aucun résultat</div>
			</div>

			<div class="btn-row">
				<button type="submit" class="btn-primary">Créer</button>
			</div>
		</form>

		<!-- ===================== Liste groupes ===================== -->
		<h2>Groupes existants</h2>

		<table>
			<thead>
				<tr>
					<th>Nom</th>
					<th>Joueurs</th>
					<th>Action</th>
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
						if (g.getJoueurs() != null && !g.getJoueurs().isEmpty()) {
							for (Joueur j : g.getJoueurs()) {
						%> <%=j.getNomUtilisateur()%> <%=j.getPrenomUtilisateur()%><br />
						<%
						}
						} else {
						%> <span class="empty-msg">Aucun joueur</span> <%
 }
 %>
					</td>
					<td>
						<form action="CtrlCoach" method="post">
							<input type="hidden" name="action" value="SupprimerGroupe" /> <input
								type="hidden" name="idGroupe" value="<%=g.getIdGroupe()%>" />
							<button type="submit" class="btn-danger"
								onclick="return confirm('Supprimer ce groupe ?');">
								Supprimer</button>
						</form>
					</td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="3" class="empty-msg">Aucun groupe trouvé.</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
		
		<div class="btn-row">
			<form action="CtrlCoach" method="get">
				<input type="hidden" name="action" value="PageCoach">
				<button type="submit" class="btn-secondary">Espace Coach</button>
			</form>
		</div>

	</div>

	<script>
function normalize(str) {
    return str.toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .replace(/\s+/g, "");
}

const searchInput = document.getElementById("searchJoueur");
const players = document.querySelectorAll(".player-item");
const noResult = document.getElementById("noResultMsg");

searchInput.addEventListener("input", function () {
    const q = normalize(this.value);
    let count = 0;

    players.forEach(p => {
        const data = normalize(p.dataset.search);
        const show = data.includes(q);
        p.style.display = show ? "" : "none";
        if (show) count++;
    });

    noResult.style.display = count === 0 ? "block" : "none";
});

document.getElementById("creerGroupeForm").addEventListener("submit", function(e) {
    if (document.querySelectorAll('input[name="joueursIds"]:checked').length === 0) {
        alert("Veuillez sélectionner au moins un joueur !");
        e.preventDefault();
    }
});
</script>

</body>
</html>
