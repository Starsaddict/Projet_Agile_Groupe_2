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
        }

        /* ===== HEADER ===== */
        .header {
            background: #1f2937;
            padding: 12px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .page-header {
		    display: flex;
		    justify-content: space-between;
		    align-items: flex-start;
		    margin-bottom: 20px;
		}

        .header-title {
            color: white;
            font-size: 18px;
            font-weight: bold;
        }

		.header-actions {
		    display: flex;
		    gap: 10px;
		    margin-left: auto;
		}

        /* ===== CONTAINER ===== */
        .container {
            max-width: 1100px;
            margin: 40px auto;
            background-color: #ffffff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        }

        h1 { font-size: 26px; color: #111827; }
        h2 {
            margin-top: 30px;
            font-size: 20px;
            border-left: 4px solid #2563eb;
            padding-left: 10px;
        }

        .subtitle {
            color: #6b7280;
            font-size: 14px;
            margin-bottom: 20px;
        }

        .badge {
            background: #e5f2ff;
            color: #1d4ed8;
            font-size: 11px;
            padding: 3px 8px;
            border-radius: 9999px;
        }

        label { font-size: 14px; }

        input {
            width: 100%;
            padding: 7px;
            border-radius: 6px;
            border: 1px solid #d1d5db;
            margin-top: 4px;
        }

        .form-row { margin-bottom: 12px; }

        /* ===== BUTTONS ===== */
		.btn-primary {
		    border: none;
		    padding: 8px 16px;
		    border-radius: 6px;
		    font-size: 14px;
		    cursor: pointer;
		    background-color: #2563eb;
		    color: #ffffff;
		}
		
		.btn-primary:hover {
		    background-color: #1d4ed8;
		    transform: translateY(-1px);
		}

        .btn-secondary {
            background: #e5e7eb;
            border: none;
            padding: 7px 14px;
            border-radius: 6px;
            cursor: pointer;
        }

        .btn-danger {
            background: #dc2626;
            color: white;
            border: none;
            padding: 7px 14px;
            border-radius: 6px;
            cursor: pointer;
        }

        /* ===== EVENTS ===== */
        .event-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
            gap: 12px;
            margin-top: 10px;
        }

        .event-card {
            border: 2px solid #e5e7eb;
            border-radius: 8px;
            padding: 12px;
            cursor: pointer;
            background: white;
        }

        .event-card:hover { border-color: #2563eb; }
        .event-card input { display: none; }
        .event-card input:checked + div {
            border-left: 4px solid #2563eb;
            padding-left: 8px;
        }

        /* ===== COVOITURAGES ===== */
        .card {
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            padding: 15px;
            margin-top: 15px;
            background: #fafafa;
        }

        .actions {
            display: flex;
            gap: 10px;
            margin-top: 10px;
            align-items: center;
        }

        .actions form {
            display: flex;
            gap: 8px;
            align-items: center;
        }

        .actions input[type="number"] { width: 70px; }

        .empty-msg {
            color: #6b7280;
            font-style: italic;
        }
        
        .badge-ok {
		    background-color: #dcfce7;
		    color: #166534;
		}
		
		.badge-full {
		    background-color: #fee2e2;
		    color: #991b1b;
		}
        
        
    </style>
</head>

<body>


<div class="container">

	<div class="page-header">
	    <div>
	        <h1>Covoiturage</h1>
	        <p class="subtitle">Créer, rejoindre ou gérer les covoiturages liés aux événements.</p>
	    </div>
	
	    <div class="header-actions">
	        <form method="get" action="${pageContext.request.contextPath}/parent/home">
	            <button type="submit" class="btn-primary">Home</button>
	        </form>
	
	        <form method="get" action="${pageContext.request.contextPath}/CtrlLogout">
	            <button type="submit" class="btn-primary">Déconnexion</button>
	        </form>
	    </div>
	</div>


    <!-- ================= CREATION ================= -->
    <h2>Créer un covoiturage</h2>

    <form method="post" action="${pageContext.request.contextPath}/parent/covoiturage">
        <input type="hidden" name="action" value="creer">

        <input type="text" id="searchEvent" placeholder="Rechercher un événement...">

        <div class="event-list">
            <c:forEach var="e" items="${evenements}">
                <label class="event-card">
                    <input type="radio" name="idEvenement"
                           value="${e.idEvenement}" required>
                    <div>
                        <b>${e.nomEvenement}</b>
                        <p>Date : ${e.dateEvenement}</p>
                        <p>Lieu : ${e.lieuEvenement}</p>
                        <p>Type : ${e.typeEvenement}</p>
                    </div>
                </label>
            </c:forEach>
        </div>
        <div id="noEventMsg" class="empty-msg" style="display:none;">
		    Aucun événement trouvé.
		</div>

        <div class="form-row">
            <label>Lieu de départ</label>
            <input type="text" name="lieuDepart" required>
        </div>

        <div class="form-row">
            <label>Heure de départ</label>
            <input type="time" name="heure" required>
        </div>

        <div class="form-row">
            <label>Nombre de places</label>
            <input type="number" name="nbPlaces" min="1" required>
        </div>

        <button class="btn btn-primary">Créer</button>
    </form>

    <!-- ================= LISTE ================= -->
    <h2>Covoiturages <span class="badge">${covoiturages.size()}</span></h2>

    <input type="text" id="searchCovoiturage"
           placeholder="Rechercher un covoiturage...">

    <c:set var="userId" value="${sessionScope.user.idUtilisateur}" />
    <div id="noCovoiturageMsg" class="empty-msg" style="display:none;">
    Aucun covoiturage trouvé.
	</div>
    

    <!-- ===== MES COVOITURAGES CREES ===== -->
	<h3>Mes covoiturages créés</h3>
	
	<c:forEach var="c" items="${covoiturages}">
	    <c:if test="${c.conducteur.idUtilisateur == userId}">
	
	        <div class="card covoiturage-card">
	            <p><b>Événement :</b> ${c.evenement.nomEvenement}</p>
	            <p><b>Date :</b> ${c.dateCovoiturage}</p>
	            <p><b>Départ :</b> ${c.lieuDepartCovoiturage}</p>
	
	            <p>
	                <b>Places :</b>
	                ${c.placesReservees} / ${c.nbPlacesMaxCovoiturage}
	
	                <c:if test="${c.placesRestantes == 0}">
	                    <span class="badge badge-full">Complet</span>
	                </c:if>
	                <c:if test="${c.placesRestantes > 0}">
	                    <span class="badge badge-ok">${c.placesRestantes} restantes</span>
	                </c:if>
	            </p>
	
	            <div class="actions">
	                <form method="post" action="${pageContext.request.contextPath}/parent/covoiturage">
	                    <input type="hidden" name="action" value="supprimer">
	                    <input type="hidden" name="idCovoiturage" value="${c.idCovoiturage}">
	                    <button type="submit" class="btn btn-danger">Supprimer</button>
	                </form>
	            </div>
	        </div>
	
	    </c:if>
	</c:forEach>


    <!-- ===== COVOITURAGES PROPOSES ===== -->
    <h3>Covoiturages proposés</h3>

    <c:forEach var="c" items="${covoiturages}">
        <c:if test="${c.conducteur.idUtilisateur != userId}">

            <c:set var="maReservation"
                   value="${c.getReservationParUtilisateur(sessionScope.user)}"/>

            <div class="card covoiturage-card">
                <p><b>Événement :</b> ${c.evenement.nomEvenement}</p>
                <p><b>Date :</b> ${c.dateCovoiturage}</p>
                <p><b>Départ :</b> ${c.lieuDepartCovoiturage}</p>
                <p><b>Conducteur :</b>
                    ${c.conducteur.prenomUtilisateur}
                    ${c.conducteur.nomUtilisateur}
                </p>

                <p>
                    <b>Places :</b>
                    ${c.placesReservees} / ${c.nbPlacesMaxCovoiturage}

                    <c:if test="${c.placesRestantes == 0}">
                        <span class="badge badge-full">Complet</span>
                    </c:if>
                    <c:if test="${c.placesRestantes > 0}">
                        <span class="badge badge-ok">
                            ${c.placesRestantes} restantes
                        </span>
                    </c:if>
                </p>

                <!-- ===== DEJA RESERVE ===== -->
                <c:if test="${maReservation != null}">
                    <p class="empty-msg">
                        Vous avez réservé
                        <b>${maReservation.nbPlaces}</b> place(s)
                    </p>

                    <div class="actions">
                        <form method="post"
                              action="${pageContext.request.contextPath}/parent/covoiturage">
                            <input type="hidden" name="action" value="rejoindre">
                            <input type="hidden" name="idCovoiturage"
                                   value="${c.idCovoiturage}">
                            <input type="number" name="nbPlaces"
                                   value="${maReservation.nbPlaces}"
                                   min="1" max="${c.nbPlacesMaxCovoiturage}">
                            <button class="btn btn-primary">Modifier</button>
                        </form>

                        <form method="post"
                              action="${pageContext.request.contextPath}/parent/covoiturage">
                            <input type="hidden" name="action" value="quitter">
                            <input type="hidden" name="idCovoiturage"
                                   value="${c.idCovoiturage}">
                            <button class="btn btn-secondary">Quitter</button>
                        </form>
                    </div>
                </c:if>

                <!-- ===== PAS ENCORE RESERVE ===== -->
                <c:if test="${maReservation == null && c.placesRestantes > 0}">
                    <form method="post"
                          action="${pageContext.request.contextPath}/parent/covoiturage"
                          class="actions">
                        <input type="hidden" name="action" value="rejoindre">
                        <input type="hidden" name="idCovoiturage"
                               value="${c.idCovoiturage}">
                        <input type="number" name="nbPlaces"
                               min="1" max="${c.placesRestantes}" value="1">
                        <button class="btn btn-primary">Rejoindre</button>
                    </form>
                </c:if>

            </div>

        </c:if>
    </c:forEach>

</div>

<!-- ================= JS ================= -->
<script>
const eventCards = document.querySelectorAll(".event-card");
const noEventMsg = document.getElementById("noEventMsg");

document.getElementById("searchEvent").addEventListener("input", e => {
    let visibleCount = 0;

    eventCards.forEach(c => {
        if (c.innerText.toLowerCase().includes(e.target.value.toLowerCase())) {
            c.style.display = "block";
            visibleCount++;
        } else {
            c.style.display = "none";
        }
    });

    noEventMsg.style.display = visibleCount === 0 ? "block" : "none";
});


const covoiturageCards = document.querySelectorAll(".covoiturage-card");
const noCovoiturageMsg = document.getElementById("noCovoiturageMsg");

document.getElementById("searchCovoiturage").addEventListener("input", e => {
    let visibleCount = 0;

    covoiturageCards.forEach(c => {
        if (c.innerText.toLowerCase().includes(e.target.value.toLowerCase())) {
            c.style.display = "block";
            visibleCount++;
        } else {
            c.style.display = "none";
        }
    });

    noCovoiturageMsg.style.display = visibleCount === 0 ? "block" : "none";
});

</script>

</body>
</html>
