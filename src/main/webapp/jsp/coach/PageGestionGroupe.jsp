<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Créer Groupe</h2>

<form id="creerGroupeForm" action="CtrlCoach" method="post">
    <input type="hidden" name="action" value="EnregistrerGroupe"/>

    Nom du groupe :
    <input type="text" name="nomGroupe" required /><br/><br/>

    Joueurs : <br/>
    <c:forEach var="j" items="${joueurs}">
        <input type="checkbox" name="joueursIds" value="${j.idUtilisateur}" />
        ${j.nomUtilisateur} ${j.prenomUtilisateur}<br/>
    </c:forEach>

    <br/>
    <button type="submit">Créer</button>
</form>
<hr>
<h2>Groupes existants</h2>

<table border="1">
    <tr>
        <th>Nom</th>
        <th>Joueurs</th>
        <th>Action</th>
    </tr>

    <c:forEach var="g" items="${groupes}">
        <tr>
            <td>${g.nomGroupe}</td>
            <td>
                <c:forEach var="j" items="${g.joueurs}">
                    ${j.nomUtilisateur} ${j.prenomUtilisateur}<br/>
                </c:forEach>
            </td>
            <td>
                <form action="CtrlCoach" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="SupprimerGroupe"/>
                    <input type="hidden" name="idGroupe" value="${g.idGroupe}"/>
                    <button type="submit" onclick="return confirm('Voulez-vous vraiment supprimer ce groupe ?');">
                        Supprimer
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<br>
<form action="CtrlCoach" method="get">
    <input type="hidden" name="action" value="PageCoach"/>
    <button type="submit">Retour à l'accueil Coach</button>
</form>

<script>
document.getElementById("creerGroupeForm").addEventListener("submit", function(event) {
    const checkboxes = document.querySelectorAll('input[name="joueursIds"]:checked');
    if (checkboxes.length === 0) {
        alert("Veuillez sélectionner au moins un joueur !");
        event.preventDefault(); // Empêche l'envoi du formulaire
    }
});
</script>
