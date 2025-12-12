<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des événements</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <style>
        body {
            background-color: #f4f6f9;
        }

        .page-title {
            font-weight: bold;
            margin-bottom: 30px;
        }

        .box {
            background: #ffffff;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        table th, table td {
            vertical-align: middle !important;
        }

        .actions {
            display: flex;
            justify-content: center;
            gap: 8px;
        }

        .btn-action {
            min-width: 95px;
            height: 36px;
            font-size: 14px;
            border-radius: 6px;
        }

        .btn-update {
            background-color: #f0ad4e;
            color: #fff;
        }

        .btn-delete {
            background-color: #d9534f;
            color: #fff;
        }

        .btn-update:hover,
        .btn-delete:hover {
            opacity: 0.9;
        }
    </style>
    
    <style>
    .footer-actions {
        margin-top: 30px;
        display: flex;
        justify-content: center;
    }

    .btn-retour {
        background-color: #6c757d;
        color: white;
        padding: 10px 20px;
        border-radius: 8px;
        text-decoration: none;
        font-weight: 500;
    }

    .btn-retour:hover {
        background-color: #5a6268;
        color: white;
        text-decoration: none;
    }
</style>
    
</head>

<body class="container mt-4">

<h2 class="page-title">📅 Gestion des événements</h2>

<!-- ===================== CREATION EVENEMENT ===================== -->
<div class="box">
    <h4>Créer un événement</h4>

    <form method="post" action="/ProjetAgile/evenementSecre" class="row g-3 mt-2">
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
            <select class="form-control" name="type" required>
                <option value="">-- Choisir un type --</option>
                <option value="MATCH_OFFICIEL">Match officiel</option>
                <option value="ENTRAINEMENT">Entraînement</option>
                <option value="MATCH_AMICAL">Match amical</option>
                <option value="REUNION">Réunion</option>
                <option value="TOURNOI">Tournoi</option>
                <option value="AUTRE">Autre événement</option>
            </select>
        </div>

        <div class="col-12">
            <button class="btn btn-primary">Créer l’événement</button>
        </div>
    </form>
</div>

<!-- ===================== LISTE EVENEMENTS ===================== -->
<div class="box">
    <h4>Liste des événements</h4>

    <table class="table table-bordered table-hover mt-3">
        <thead class="table-light">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Lieu</th>
            <th>Date</th>
            <th>Type</th>
            <th class="text-center">Actions</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="e" items="${evenements}">
            <tr>

                <td>${e.idEvenement}</td>

                <td>
                    <input class="form-control"
                           form="update${e.idEvenement}"
                           name="nom"
                           value="${e.nomEvenement}">
                </td>

                <td>
                    <input class="form-control"
                           form="update${e.idEvenement}"
                           name="lieu"
                           value="${e.lieuEvenement}">
                </td>

                <td>
                    <input type="datetime-local"
                           class="form-control"
                           form="update${e.idEvenement}"
                           name="date"
                           value="${e.dateEvenement}">
                </td>

                <td>
                    <select class="form-control"
                            form="update${e.idEvenement}"
                            name="type">
                        <option value="MATCH_OFFICIEL" ${e.typeEvenement=='MATCH_OFFICIEL'?'selected':''}>Match officiel</option>
                        <option value="ENTRAINEMENT" ${e.typeEvenement=='ENTRAINEMENT'?'selected':''}>Entraînement</option>
                        <option value="MATCH_AMICAL" ${e.typeEvenement=='MATCH_AMICAL'?'selected':''}>Match amical</option>
                        <option value="REUNION" ${e.typeEvenement=='REUNION'?'selected':''}>Réunion</option>
                        <option value="TOURNOI" ${e.typeEvenement=='TOURNOI'?'selected':''}>Tournoi</option>
                        <option value="AUTRE" ${e.typeEvenement=='AUTRE'?'selected':''}>Autre</option>
                    </select>
                </td>

                <td class="actions">

                    <form id="update${e.idEvenement}"
                          method="post"
                          action="/ProjetAgile/evenementSecre">
                        <input type="hidden" name="id" value="${e.idEvenement}">
                        <input type="hidden" name="action" value="update">
                    </form>

                    <button class="btn btn-action btn-update"
                            form="update${e.idEvenement}">
                        Modifier
                    </button>

                    <form method="post"
                          action="/ProjetAgile/evenementSecre"
                          onsubmit="return confirm('Supprimer cet événement ?');">
                        <input type="hidden" name="id" value="${e.idEvenement}">
                        <input type="hidden" name="action" value="delete">
                        <button class="btn btn-action btn-delete">
                            Supprimer
                        </button>
                    </form>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="footer-actions">
    <a href="/ProjetAgile/jsp/secretaire.jsp" class="btn-retour">
        ⬅ Retour à l’accueil secrétaire
    </a>
</div>


</body>
</html>
