<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Créer un groupe</h2>

<form action="CtrlCoach?action=EnregistrerGroupe" method="post">
    Nom du groupe :
    <input type="text" name="nomGroupe" required>

    <h3>Choisir des joueurs</h3>

    <c:forEach var="j" items="${joueurs}">
        <input type="checkbox" name="joueursIds" value="${j.idUtilisateur}">
        ${j.nomUtilisateur} ${j.prenomUtilisateur} <br>
    </c:forEach>

    <button type="submit">Créer le groupe</button>
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
                    ${j.nomUtilisateur} ${j.prenomUtilisateur} <br>
                </c:forEach>
            </td>
            <td>
                <a href="CtrlCoach?action=ModifierGroupe&id=${g.idGroupe}">
                    Modifier
                </a>
            </td>
        </tr>
    </c:forEach>

</table>
