<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Gestion des événements</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<style>
.evt-form-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
	gap: 12px;
}

.evt-form-grid .field label {
	font-weight: 600;
	margin-bottom: 4px;
	display: block;
}

.evt-form-grid .field input, .evt-form-grid .field select {
	width: 100%;
	padding: 8px;
	border: 1px solid var(--border);
	border-radius: var(--radius);
	font-size: 0.95rem;
}

.evt-table {
	width: 100%;
	border-collapse: collapse;
}

.evt-table th, .evt-table td {
	padding: 10px;
	border-bottom: 1px solid var(--border);
	text-align: left;
}

.evt-table thead {
	background: #f8fafc;
}

.evt-table tr:hover {
	background: #f1f5f9;
}

.evt-actions {
	display: flex;
	gap: 8px;
	flex-wrap: wrap;
}
</style>
</head>

<body>
	<%@ include file="/jsp/header.jspf"%>

	<!-- ================= ACTIONS HAUT ================= -->

	<div class="container">
		<div class="card">
			<h2>Gestion des événements</h2>

			<!-- ===================== CREATION EVENEMENT ===================== -->
			<div class="card" style="margin-top: 12px;">
				<h3>Créer un événement</h3>

				<form method="post"
					action="${pageContext.request.contextPath}/evenementSecre"
					class="evt-form-grid">
					<input type="hidden" name="action" value="create">

					<div class="field">
						<label>Nom</label> <input type="text" name="nom" required>
					</div>

					<div class="field">
						<label>Lieu</label> <input type="text" name="lieu" required>
					</div>

					<div class="field">
						<label>Date</label> <input type="datetime-local" name="date"
							required>
					</div>

					<div class="field">
						<label>Type</label> <select name="type" required>
							<option value="">-- Choisir --</option>
							<option value="MATCH_OFFICIEL">Match officiel</option>
							<option value="ENTRAINEMENT">Entraînement</option>
							<option value="MATCH_AMICAL">Match amical</option>
							<option value="REUNION">Réunion</option>
							<option value="TOURNOI">Tournoi</option>
							<option value="AUTRE">Autre</option>
						</select>
					</div>

					<div class="form-actions">
						<button type="submit" class="btn btn-primary">Créer</button>
					</div>
				</form>
			</div>

			<!-- ===================== LISTE EVENEMENTS ===================== -->
			<div class="card" style="margin-top: 12px;">
				<h3>Liste des événements</h3>
				<div style="margin: 10px 0;">
					<input type="text" id="searchEvenement"
						placeholder="Rechercher un événement..."
						style="width: 260px; padding: 6px;">
				</div>

				<table class="evt-table">
					<thead>
						<tr
							data-search="${e.nomEvenement} ${e.lieuEvenement} ${e.typeEvenement}">
							<th>ID</th>
							<th>Nom</th>
							<th>Lieu</th>
							<th>Date</th>
							<th>Type</th>
							<th>Actions</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="e" items="${evenements}">
							<tr>
								<td>${e.idEvenement}</td>

								<td><input type="text" name="nom" value="${e.nomEvenement}"
									form="update${e.idEvenement}"></td>

								<td><input type="text" name="lieu"
									value="${e.lieuEvenement}" form="update${e.idEvenement}">
								</td>

								<td><input type="datetime-local" name="date"
									value="${e.dateForInput}" form="update${e.idEvenement}">
								</td>

								<td><select name="type" form="update${e.idEvenement}">
										<option value="MATCH_OFFICIEL"
											${e.typeEvenement == 'MATCH_OFFICIEL' ? 'selected' : ''}>Match
											officiel</option>
										<option value="ENTRAINEMENT"
											${e.typeEvenement == 'ENTRAINEMENT' ? 'selected' : ''}>Entraînement</option>
										<option value="MATCH_AMICAL"
											${e.typeEvenement == 'MATCH_AMICAL' ? 'selected' : ''}>Match
											amical</option>
										<option value="REUNION"
											${e.typeEvenement == 'REUNION' ? 'selected' : ''}>Réunion</option>
										<option value="TOURNOI"
											${e.typeEvenement == 'TOURNOI' ? 'selected' : ''}>Tournoi</option>
										<option value="AUTRE"
											${e.typeEvenement == 'AUTRE' ? 'selected' : ''}>Autre</option>
								</select></td>

								<td class="actions">
									<!-- FORM UPDATE -->
									<form id="update${e.idEvenement}" method="post"
										action="${pageContext.request.contextPath}/evenementSecre">
										<input type="hidden" name="action" value="update"> <input
											type="hidden" name="id" value="${e.idEvenement}">
									</form>

									<button type="submit" class="btn" form="update${e.idEvenement}"
										style="min-width: 110px;">Modifier</button> <!-- FORM DELETE -->
									<!-- FORM DELETE -->
									<form method="post"
										action="${pageContext.request.contextPath}/evenementSecre"
										onsubmit="return confirm(
          'Voulez-vous vraiment supprimer l’événement : ${e.nomEvenement} ?\nCette action est définitive.'
      );">
										<input type="hidden" name="action" value="delete"> <input
											type="hidden" name="id" value="${e.idEvenement}">
										<button type="submit" class="btn danger"
											style="min-width: 110px;">Supprimer</button>
									</form>



								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</body>
<script>
function normalizeEvt(str) {
    return str.toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .replace(/\s+/g, "");
}

const searchEvt = document.getElementById("searchEvenement");
const evtRows = document.querySelectorAll(".evt-table tbody tr");

searchEvt.addEventListener("input", function () {
    const q = normalizeEvt(this.value);

    evtRows.forEach(row => {
        const nom   = row.querySelector('input[name="nom"]')?.value || "";
        const lieu  = row.querySelector('input[name="lieu"]')?.value || "";
        const type  = row.querySelector('select[name="type"]')?.selectedOptions[0]?.text || "";

        const text = normalizeEvt(nom + " " + lieu + " " + type);

        row.style.display = text.includes(q) ? "" : "none";
    });
});
</script>

</html>
