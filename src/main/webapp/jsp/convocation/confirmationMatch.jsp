<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Confirmation de présence - Match</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            padding: 40px;
        }

        .container {
            max-width: 600px;
            margin: auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            text-align: center;
        }

        h2 {
            margin-bottom: 20px;
        }

        .info {
            margin-bottom: 15px;
            font-size: 16px;
        }

        .status {
            margin: 20px 0;
            font-size: 18px;
            font-weight: bold;
        }

        .oui {
            color: green;
        }

        .non {
            color: red;
        }

        form button {
            padding: 12px 25px;
            margin: 10px;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn-oui {
            background-color: #4CAF50;
            color: white;
        }

        .btn-non {
            background-color: #F44336;
            color: white;
        }

        .note {
            margin-top: 20px;
            font-size: 13px;
            color: #555;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Convocation – Match officiel</h2>

    <div class="info">
        <strong>Joueur :</strong>
        ${convocation.joueur.prenomUtilisateur}
        ${convocation.joueur.nomUtilisateur}
    </div>

    <div class="info">
        <strong>Match :</strong>
        ${convocation.evenement.nomEvenement}
    </div>

    <div class="info">
        <strong>Date :</strong>
        ${convocation.evenement.dateEvenement}
    </div>

    <!-- Affichage de la réponse actuelle -->
    <c:if test="${convocation.confirmePresence != null}">
        <div class="status">
            Réponse actuelle :
            <span class="${convocation.confirmePresence ? 'oui' : 'non'}">
                ${convocation.confirmePresence ? "OUI – Le joueur peut jouer" : "NON – Le joueur ne peut pas jouer"}
            </span>
        </div>
    </c:if>

    <c:if test="${convocation.confirmePresence == null}">
        <div class="status">
            Aucune réponse n’a encore été enregistrée.
        </div>
    </c:if>

    <!-- Formulaire OUI / NON -->
    <form method="post">
        <input type="hidden" name="token" value="${convocation.token}" />

        <button type="submit" name="peutJouer" value="oui" class="btn-oui">
            ✅ Le joueur peut jouer
        </button>

        <button type="submit" name="peutJouer" value="non" class="btn-non">
            ❌ Le joueur ne peut pas jouer
        </button>
    </form>

    <div class="note">
        ⚠️ La dernière réponse enregistrée sera prise en compte.
        <br/>
        Cette réponse peut être modifiée à tout moment.
    </div>

</div>

</body>
</html>
