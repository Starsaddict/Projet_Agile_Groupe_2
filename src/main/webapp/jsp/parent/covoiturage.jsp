<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Covoiturage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>

<h2>Créer un covoiturage</h2>

<form method="post" action="${pageContext.request.contextPath}/parent/covoiturage">
    <input type="hidden" name="action" value="creer">

    <label>Événement</label><br>
    <select name="idEvenement" required>
        <option value="">-- Sélectionner un événement --</option>
        <c:forEach var="e" items="${evenements}">
            <option value="${e.idEvenement}">
                ${e.nomEvenement}
            </option>
        </c:forEach>
    </select><br><br>

    <label>Lieu de départ</label><br>
    <input type="text" name="lieuDepart" required><br><br>

    <label>Date et heure</label><br>
    <input type="datetime-local" name="date" required><br><br>

    <label>Nombre de places</label><br>
    <input type="number" name="nbPlaces" min="1" required><br><br>

    <button type="submit">Créer le covoiturage</button>
</form>

<hr>

<h2>Covoiturages existants</h2>

<c:forEach var="c" items="${covoiturages}">
    <div style="border:1px solid #ccc; padding:10px; margin:10px 0;">

        <p><b>Événement :</b> ${c.evenement.nomEvenement}</p>
        <p><b>Départ :</b> ${c.lieuDepartCovoiturage}</p>
        <p><b>Date :</b> ${c.dateCovoiturage}</p>
        <p><b>Places :</b> ${c.reservations.size()} / ${c.nbPlacesMaxCovoiturage}</p>
        <p><b>Conducteur :</b> ${c.conducteur.nomUtilisateur} ${c.conducteur.prenomUtilisateur}</p>

        <!-- Supprimer si conducteur -->
        <c:if test="${c.conducteur.idUtilisateur == sessionScope.user.idUtilisateur}">
            <form method="post" action="${pageContext.request.contextPath}/parent/covoiturage" style="margin-top:10px;">
                <input type="hidden" name="action" value="supprimer">
                <input type="hidden" name="idCovoiturage" value="${c.idCovoiturage}">
                <button type="submit">Supprimer mon covoiturage</button>
            </form>
        </c:if>

        <!-- Rejoindre si pas conducteur et places dispo -->
        <c:if test="${c.conducteur.idUtilisateur != sessionScope.user.idUtilisateur
                    && c.reservations.size() < c.nbPlacesMaxCovoiturage
                    && !c.reservations.contains(sessionScope.user)}">
            <form method="post" action="${pageContext.request.contextPath}/parent/covoiturage" style="margin-top:10px;">
                <input type="hidden" name="action" value="rejoindre">
                <input type="hidden" name="idCovoiturage" value="${c.idCovoiturage}">
                <button type="submit">Rejoindre</button>
            </form>
        </c:if>
        
		<c:if test="${c.conducteur.idUtilisateur != sessionScope.user.idUtilisateur 
            && c.reservations.contains(sessionScope.user)}">

		    <form method="post" action="${pageContext.request.contextPath}/parent/covoiturage" style="margin-top:10px;">
		        <input type="hidden" name="action" value="quitter">
		        <input type="hidden" name="idCovoiturage" value="${c.idCovoiturage}">
		        <button type="submit">Quitter le covoiturage</button>
		    </form>
		</c:if>
        
        

    </div>
</c:forEach>


</body>
</html>
