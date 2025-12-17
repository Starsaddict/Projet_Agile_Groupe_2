<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Covoiturage</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f3f4f6;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 1100px;
            margin: 40px auto;
            background-color: #ffffff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        }

        h1 {
            margin-top: 0;
            font-size: 26px;
            color: #111827;
        }

        h2 {
            margin-top: 30px;
            font-size: 20px;
            color: #1f2933;
            border-left: 4px solid #2563eb;
            padding-left: 10px;
        }

        .subtitle {
            color: #6b7280;
            font-size: 14px;
            margin-bottom: 20px;
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

        .card {
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            padding: 15px 18px;
            margin-top: 15px;
            background-color: #fafafa;
        }

        .card p {
            margin: 6px 0;
            font-size: 14px;
        }

        label {
            font-size: 14px;
            color: #374151;
        }

        input, select {
            width: 100%;
            padding: 7px 8px;
            margin-top: 4px;
            border-radius: 6px;
            border: 1px solid #d1d5db;
            font-size: 14px;
        }

        .form-row {
            margin-bottom: 12px;
        }

        .btn-primary, .btn-secondary, .btn-danger {
            border: none;
            padding: 7px 14px;
            border-radius: 6px;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.15s ease, transform 0.05s ease;
        }

        .btn-primary {
            background-color: #2563eb;
            color: white;
        }

        .btn-primary:hover {
            background-color: #1d4ed8;
            transform: translateY(-1px);
        }

        .btn-secondary {
            background-color: #e5e7eb;
            color: #111827;
        }

        .btn-secondary:hover {
            background-color: #d1d5db;
        }

        .btn-danger {
            background-color: #dc2626;
            color: white;
        }

        .btn-danger:hover {
            background-color: #b91c1c;
        }

		.actions {
		    display: flex;
		    gap: 10px;
		    margin-top: 12px;
		    align-items: center;
		    flex-wrap: wrap;
		}
		
		.actions form {
		    display: flex;
		    align-items: center;
		    gap: 8px;
		}
		
		.actions input[type="number"] {
		    width: 70px;
		}


        .empty-msg {
            color: #6b7280;
            font-style: italic;
            font-size: 14px;
        }

        hr {
            border: none;
            border-top: 1px solid #e5e7eb;
            margin: 30px 0;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Covoiturage</h1>
    <p class="subtitle">Créer, rejoindre ou gérer les covoiturages liés aux événements.</p>

    <!-- Création -->
    <h2>Créer un covoiturage</h2>

    <form method="post" action="${pageContext.request.contextPath}/parent/covoiturage">
        <input type="hidden" name="action" value="creer">

        <div class="form-row">
            <label>Événement</label>
            <select name="idEvenement" required>
                <option value="">-- Sélectionner un événement --</option>
                <c:forEach var="e" items="${evenements}">
                    <option value="${e.idEvenement}">${e.nomEvenement}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-row">
            <label>Lieu de départ</label>
            <input type="text" name="lieuDepart" required>
        </div>

        <div class="form-row">
            <label>Date et heure</label>
            <input type="datetime-local" name="date" required>
        </div>

        <div class="form-row">
            <label>Nombre de places</label>
            <input type="number" name="nbPlaces" min="1" required>
        </div>

        <button type="submit" class="btn-primary">Créer le covoiturage</button>
    </form>

    <hr>

    <!-- Liste -->
    <h2>
        Covoiturages existants
        <span class="badge">${covoiturages.size()}</span>
    </h2>

    <c:forEach var="c" items="${covoiturages}">
        <c:set var="maReservation" value="${c.getReservationParUtilisateur(sessionScope.user)}" />

        <div class="card">
            <p><b>Événement :</b> ${c.evenement.nomEvenement}</p>
            <p><b>Départ :</b> ${c.lieuDepartCovoiturage}</p>
            <p><b>Date :</b> ${c.dateCovoiturage}</p>
            <p><b>Places :</b> ${c.placesReservees} / ${c.nbPlacesMaxCovoiturage}</p>
            <p><b>Conducteur :</b> ${c.conducteur.prenomUtilisateur} ${c.conducteur.nomUtilisateur}</p>

            <!-- Conducteur -->
            <c:if test="${c.conducteur.idUtilisateur == sessionScope.user.idUtilisateur}">
                <form method="post" action="${pageContext.request.contextPath}/parent/covoiturage" class="actions">
                    <input type="hidden" name="action" value="supprimer">
                    <input type="hidden" name="idCovoiturage" value="${c.idCovoiturage}">
                    <button class="btn-danger">Supprimer</button>
                </form>
            </c:if>

            <!-- Déjà réservé -->
            <c:if test="${maReservation != null}">
                <div class="actions">
                    <form method="post" action="${pageContext.request.contextPath}/parent/covoiturage">
                        <input type="hidden" name="action" value="rejoindre">
                        <input type="hidden" name="idCovoiturage" value="${c.idCovoiturage}">
                        <input type="number" name="nbPlaces" min="1"
                               max="${c.nbPlacesMaxCovoiturage}"
                               value="${maReservation.nbPlaces}" required>
                        <button class="btn-primary">Modifier</button>
                    </form>

                    <form method="post" action="${pageContext.request.contextPath}/parent/covoiturage">
                        <input type="hidden" name="action" value="quitter">
                        <input type="hidden" name="idCovoiturage" value="${c.idCovoiturage}">
                        <button class="btn-secondary">Quitter</button>
                    </form>
                </div>
            </c:if>

            <!-- Rejoindre -->
            <c:if test="${maReservation == null && c.conducteur.idUtilisateur != sessionScope.user.idUtilisateur && c.placesRestantes > 0}">
                <form method="post" action="${pageContext.request.contextPath}/parent/covoiturage" class="actions">
                    <input type="hidden" name="action" value="rejoindre">
                    <input type="hidden" name="idCovoiturage" value="${c.idCovoiturage}">
                    <input type="number" name="nbPlaces" min="1" max="${c.placesRestantes}" value="1" required>
                    <button class="btn-primary">Rejoindre</button>
                </form>
            </c:if>

            <c:if test="${c.placesRestantes == 0}">
                <p class="empty-msg">Complet</p>
            </c:if>
        </div>
    </c:forEach>

</div>

</body>
</html>
