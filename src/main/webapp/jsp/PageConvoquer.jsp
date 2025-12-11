<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Evenement" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Convocation des groupes</title>
</head>
<body>
    <h1>Convocation des groupes</h1>
    <%
        List<Evenement> evenements = (List<Evenement>) request.getAttribute("evenements");
    %>

    <h2>Événements à venir</h2>

    <%
        if (evenements == null || evenements.isEmpty()) {
    %>
        <p>Aucun événement à venir.</p>
    <%
        } else {
    %>
        <table border="1" cellpadding="5" cellspacing="0">
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Date</th>
                <th>Action</th>
            </tr>
            <%
                for (Evenement e : evenements) {
            %>
            <tr>
                <td><%= e.getIdEvenement() %></td>
                <td><%= e.getNomEvenement() %></td>
                <td><%= e.getDateEvenement() %></td>
                <td>
                    <form action="CtrlConvoquer" method="get">
                        <input type="hidden" name="action" value="selectionEvenement">
                        <input type="hidden" name="idEvenement" value="<%= e.getIdEvenement() %>">
                        <button type="submit">À convoquer</button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>

    <br>
    
    <form action="CtrlCoach" method="get">
		<input type="hidden" name="action" value="PageCoach">
		<button type="submit">Accueil</button>
	</form>
</body>
</html>
