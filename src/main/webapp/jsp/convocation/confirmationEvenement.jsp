<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Confirmation de présence</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            padding: 40px;
        }
        .container {
            max-width: 600px;
            margin: auto;
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            text-align: center;
        }
        button {
            padding: 12px 25px;
            margin: 10px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            border: none;
        }
        .oui { background: #4CAF50; color: white; }
        .non { background: #F44336; color: white; }
    </style>
</head>

<body>

<div class="container">
    <h2>Confirmation de présence</h2>

    <p><strong>Événement :</strong>
        ${convocation.evenement.nomEvenement}</p>

    <p><strong>Date :</strong>
        ${convocation.evenement.dateEvenement}</p>

    <p><strong>Joueur :</strong>
        ${convocation.joueur.prenomUtilisateur}
        ${convocation.joueur.nomUtilisateur}</p>
        
        <c:if test="${convocation.confirmePresence != null}">
    <p>
        <strong>Réponse actuelle :</strong>
        <span style="color:${convocation.confirmePresence ? 'green' : 'red'}">
            ${convocation.confirmePresence ? "PRÉSENT" : "ABSENT"}
        </span>
    </p>
</c:if>

<c:if test="${convocation.confirmePresence == null}">
    <p><em>Aucune réponse enregistrée.</em></p>
</c:if>
        

    <form method="post">
        <input type="hidden" name="token"
               value="${convocation.token}" />

        <button class="oui" name="presence" value="oui">
            ✅ Je serai présent
        </button>

        <button class="non" name="presence" value="non">
            ❌ Je ne serai pas présent
        </button>
    </form>

    <p style="font-size:12px;color:gray;">
        La dernière réponse enregistrée sera prise en compte.
    </p>
</div>

</body>
</html>
