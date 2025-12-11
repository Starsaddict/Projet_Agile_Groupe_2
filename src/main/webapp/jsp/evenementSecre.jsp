<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des événements</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body class="container mt-4">

<h2>Créer un événement</h2>

<form method="post" action="/ProjetAgile/evenementSecre" class="row g-3">
    <input type="hidden" name="action" value="create">

    <div class="col-md-6">
        <label>Nom</label>
        <input class="form-control" name="nom" required>
    </div>

    <div class="col-md-6">
        <label>Lieu</label>
        <input class="form-control" name="lieu" required>
    </div>

    <div class="col-md-6">
        <label>Date</label>
        <input type="datetime-local" class="form-control" name="date" required>
    </div>

    <div class="col-md-6">
        <label>Type</label>
        <input class="form-control" name="type" required>
    </div>

    <div class="col-12">
        <button class="btn btn-primary">Créer</button>
    </div>
</form>

<hr>

<h2>Liste des événements</h2>

<table class="table table-bordered table-striped">
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

            <!-- FORM UPDATE -->
            <form method="post" action="/ProjetAgile/evenementSecre" class="d-flex gap-2">

                <input type="hidden" name="id" value="${e.idEvenement}">
                <input type="hidden" name="action" value="update">

                <td>${e.idEvenement}</td>

                <td><input class="form-control" name="nom" value="${e.nomEvenement}"></td>

                <td><input class="form-control" name="lieu" value="${e.lieuEvenement}"></td>

                <td>
                    <input type="datetime-local"
                           class="form-control"
                           name="date"
                           value="${e.dateEvenement}">
                </td>

                <td><input class="form-control" name="type" value="${e.typeEvenement}"></td>

                <td class="d-flex gap-2">

                    <!-- Bouton modifier -->
                    <button class="btn btn-warning btn-sm">Modifier</button>

            </form>

            <!-- FORM DELETE (Séparé) -->
            <form method="post" action="/ProjetAgile/evenementSecre"
                  onsubmit="return confirm('Supprimer cet événement ?');">

                <input type="hidden" name="id" value="${e.idEvenement}">
                <input type="hidden" name="action" value="delete">

                <button class="btn btn-danger btn-sm">Supprimer</button>
            </form>

        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
