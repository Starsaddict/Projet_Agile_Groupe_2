<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des événements</title>

    <!-- CSS personnalisé (sans framework) -->
    <link rel="stylesheet" href="/ProjetAgile/css/evenements.css">
</head>

<body>

<h2 class="page-title">Gestion des événements</h2>

<!-- ===================== CREATION EVENEMENT ===================== -->
<div class="box">
    <h3>Créer un événement</h3>

    <form method="post" action="${pageContext.request.contextPath}/evenementSecre" class="form-grid">
        <input type="hidden" name="action" value="create">

        <div class="form-group">
            <label>Nom</label>
            <input type="text" name="nom" required>
        </div>

        <div class="form-group">
            <label>Lieu</label>
            <input type="text" name="lieu" required>
        </div>

        <div class="form-group">
            <label>Date</label>
            <input type="datetime-local" name="date" required>
        </div>

        <div class="form-group">
            <label>Type</label>
            <select name="type" required>
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
<div class="box">
    <h3>Liste des événements (du plus proche au plus lointain)</h3>

    <table>
        <thead>
        <tr>
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

                <td>
                    <input type="text"
                           name="nom"
                           value="${e.nomEvenement}"
                           form="update${e.idEvenement}">
                </td>

                <td>
                    <input type="text"
                           name="lieu"
                           value="${e.lieuEvenement}"
                           form="update${e.idEvenement}">
                </td>

                <td>
                    <input type="datetime-local"
                           name="date"
                           value="${e.dateForInput}"
                           form="update${e.idEvenement}">
                </td>

                <td>
                    <select name="type" form="update${e.idEvenement}">
                        <option value="MATCH_OFFICIEL" ${e.typeEvenement == 'MATCH_OFFICIEL' ? 'selected' : ''}>Match officiel</option>
                        <option value="ENTRAINEMENT" ${e.typeEvenement == 'ENTRAINEMENT' ? 'selected' : ''}>Entraînement</option>
                        <option value="MATCH_AMICAL" ${e.typeEvenement == 'MATCH_AMICAL' ? 'selected' : ''}>Match amical</option>
                        <option value="REUNION" ${e.typeEvenement == 'REUNION' ? 'selected' : ''}>Réunion</option>
                        <option value="TOURNOI" ${e.typeEvenement == 'TOURNOI' ? 'selected' : ''}>Tournoi</option>
                        <option value="AUTRE" ${e.typeEvenement == 'AUTRE' ? 'selected' : ''}>Autre</option>
                    </select>
                </td>

                <td class="actions">

                    <!-- FORM UPDATE -->
                    <form id="update${e.idEvenement}"
                          method="post"
                          action="${pageContext.request.contextPath}/evenementSecre">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="${e.idEvenement}">
                    </form>

                    <button type="submit"
                            class="btn btn-update"
                            form="update${e.idEvenement}">
                        Modifier
                    </button>

                    <!-- FORM DELETE -->
                    <form method="post"
                          action="${pageContext.request.contextPath}/evenementSecre"
                          onsubmit="return confirm('Supprimer cet événement ?');">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${e.idEvenement}">
                        <button type="submit" class="btn btn-delete">
                            Supprimer
                        </button>
                    </form>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
