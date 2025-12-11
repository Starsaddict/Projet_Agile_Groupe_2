<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Créer Groupe</h2>

<form action="CtrlCoach" method="post">
    <input type="hidden" name="action" value="EnregistrerGroupe"/>

    Nom du groupe :
    <input type="text" name="nomGroupe" /><br/><br/>

    Joueurs : <br/>
    <c:forEach var="j" items="${joueurs}">
        <input type="checkbox" name="joueursIds" value="${j.idUtilisateur}" />
        ${j.nomUtilisateur} ${j.prenomUtilisateur}<br/>
    </c:forEach>

    <br/>
    <button type="submit">Créer</button>
</form>

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
