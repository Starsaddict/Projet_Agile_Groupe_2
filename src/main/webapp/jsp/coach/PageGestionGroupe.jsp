<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.Groupe, model.Joueur"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestion des groupes</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<style>
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
.players-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-top: 10px;
	margin-bottom: 6px;
}
.players-header input { width: 260px; }
.players-list {
	padding: 10px 12px;
	border-radius: 8px;
	border: 1px solid var(--border);
	max-height: 220px;
	overflow-y: auto;
	background-color: #f9fafb;
	font-size: 14px;
}
.player-item { margin-bottom: 4px; }
.empty-msg { color: var(--text-muted); font-style: italic; }
table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 15px;
	font-size: 14px;
}
table thead th {
	background-color: var(--primary);
	color: white;
	padding: 8px 10px;
	text-align: left;
}
table tbody td {
	border-bottom: 1px solid var(--border);
	padding: 12px 10px;
	vertical-align: top;
}
td.td-action { width: 260px; }
.action-cell {
	display: flex;
	gap: 10px;
	align-items: flex-start;
	justify-content: flex-end;
	margin: 0;
	padding-top: 2px;
}
.action-cell form { margin: 0; }
.avatar {
  width: 34px;
  height: 34px;
  border-radius: 50%;
  object-fit: cover;
  border: 1px solid var(--border);
  vertical-align: middle;
}
.player-line {
	display: inline-flex;
	align-items: center;
	gap: 8px;
	margin-bottom: 4px;
}
</style> 
</head>

<body>
	<%@ include file="/jsp/header.jspf" %>

	<%
	List<Joueur> joueurs = (List<Joueur>) request.getAttribute("joueurs");
	List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupes");
	%>
	<%
	Groupe groupeAEditer = (Groupe) request.getAttribute("groupeAEditer");
	Long idEdit = (groupeAEditer != null) ? groupeAEditer.getIdGroupe() : null;
	%>

	<div class="container">
		<div class="page-header">
	
			<div>
				<h1>Gestion des groupes</h1>
				<p class="subtitle">Créer/Modifier un nouveau groupe et gérer les groupes existants.</p>
			</div>
		</div>
		<!-- ===================== Création ===================== -->
		<div class="section-card">
		<h2>Créer/Modifier un groupe</h2>

		<form id="creerGroupeForm" action="CtrlCoach" method="post">
			<input type="hidden" name="action"
				value="<%=(groupeAEditer != null) ? "ModifierGroupe" : "EnregistrerGroupe"%>" />

			<%
			if (groupeAEditer != null) {
			%>
			<input type="hidden" name="idGroupe"
				value="<%=groupeAEditer.getIdGroupe()%>" />
			<%
			}
			%>

			<div>
				<label>Nom du groupe :</label> <input type="text" name="nomGroupe"
					required
					value="<%=(groupeAEditer != null && groupeAEditer.getNomGroupe() != null) ? groupeAEditer.getNomGroupe() : ""%>" />

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
					<label class="player-line"> <%
 boolean checked = false;
 if (groupeAEditer != null && groupeAEditer.getJoueurs() != null) {
 	for (Joueur jj : groupeAEditer.getJoueurs()) {
 		if (jj.getIdUtilisateur().equals(j.getIdUtilisateur())) {
 	checked = true;
 	break;
 		}
 	}
 }
 %> <%
 String pic = j.getProfilePicRoute();
 if (pic == null || pic.isBlank()) {
 	pic = "/img/joueur_avatar/default.png";
 }
 String src = request.getContextPath() + pic;
 %> <input
						type="checkbox" name="joueursIds"
						value="<%=j.getIdUtilisateur()%>" <%=checked ? "checked" : ""%> />
						<%=nom%> <%=prenom%> <img class="avatar" src="<%=src%>"
						alt="avatar"
						onerror="this.onerror=null;this.src='<%=request.getContextPath()%>/img/joueur_avatar/default.png';" />
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
				<button type="submit" class="btn">
					<%=(groupeAEditer != null) ? "Valider la modification" : "Créer"%>
				</button>

				<%
				if (groupeAEditer != null) {
				%>
				<form action="CtrlCoach" method="get" style="margin: 0;">
					<input type="hidden" name="action" value="GestionGroupe" />
					<button type="submit" class="btn secondary">Annuler</button>
				</form>
				<%
				}
				%>
			</div>
		</form>
		</div>

		<!-- ===================== Liste groupes ===================== -->
		<div class="section-card">
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
					<td class="td-action">
						<div class="action-cell">
							<!-- Modifier -->
							<form action="CtrlCoach" method="get" style="margin: 0;">
								<input type="hidden" name="action" value="EditerGroupe" /> <input
									type="hidden" name="idGroupe" value="<%=g.getIdGroupe()%>" />
								<button type="submit" class="btn secondary">Modifier</button>
							</form>

							<!-- Supprimer -->
							<form action="CtrlCoach" method="post" style="margin: 0;">
								<input type="hidden" name="action" value="SupprimerGroupe" /> <input
									type="hidden" name="idGroupe" value="<%=g.getIdGroupe()%>" />
								<%
								Map<Long, List<String>> evenementsParGroupe = (Map<Long, List<String>>) request.getAttribute("evenementsParGroupe");

								List<String> evts = evenementsParGroupe != null ? evenementsParGroupe.get(g.getIdGroupe()) : null;

								String msg = "Supprimer ce groupe ?";
								if (evts != null && !evts.isEmpty()) {
									msg = "⚠ Ce groupe est déjà affecté aux événements suivants :\\n" + String.join("\\n- ", evts)
									+ "\\n\\nVoulez-vous vraiment le supprimer ?";
								}
								%>

								<button type="submit" class="btn danger"
									onclick="return confirm('<%=msg%>');">Supprimer</button>

							</form>
						</div>
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
