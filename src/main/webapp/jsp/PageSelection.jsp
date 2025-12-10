<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Evenement" %>
<%@ page import="model.Groupe" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sélection des groupes</title>
</head>
<body>

    <h1>Sélection des groupes à convoquer</h1>

    <%
        String messageSucces = (String) request.getAttribute("messageSucces");
        String messageErreur = (String) request.getAttribute("messageErreur");
        if (messageSucces != null) {
    %>
        <p style="color:green;"><%= messageSucces %></p>
    <%
        }
        if (messageErreur != null) {
    %>
        <p style="color:red;"><%= messageErreur %></p>
    <%
        }

        Evenement evt = (Evenement) request.getAttribute("evenementSelectionne");
    %>

    <h2>Événement sélectionné</h2>
    <%
        if (evt == null) {
    %>
        <p>Aucun événement sélectionné.</p>
    <%
        } else {
    %>
        <p><strong>ID :</strong> <%= evt.getIdEvenement() %></p>
        <p><strong>Nom :</strong> <%= evt.getNomEvenement() %></p>
        <p><strong>Date / Heure :</strong> <%= evt.getDateEvenement() %></p>
    <%
        }
    %>

    <hr>

    <h2>Choisissez les groupes à convoquer</h2>

    <%
        List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupesCoach");
        if (groupes == null || groupes.isEmpty()) {
        	Groupe groupeSelectionne = null;
    %>
        <p>Aucun groupe disponible.</p>
    <%
        } else if (evt != null) {
        	Groupe groupeSelectionne = evt.getGroupe();
        	if (groupeSelectionne != null) {
    %>
            <p><strong>Groupe déjà convoqué :</strong>
               <%= groupeSelectionne.getIdGroupe() %> - 
               <%= groupeSelectionne.getNomGroupe() %>
            </p>
	<%
        } else {
	%>
            <p><em>Aucun groupe n'a encore été convoqué pour cet événement.</em></p>
	<%
        		}
    %>
        <form action="CtrlConvoquer" method="post">
            <input type="hidden" name="action" value="validerConvocation">
            <input type="hidden" name="idEvenement" value="<%= evt.getIdEvenement() %>">

            <table border="1" cellpadding="5" cellspacing="0">
                <tr>
                    <th>Sélection</th>
                    <th>ID Groupe</th>
                    <th>Nom du groupe</th>
                </tr>
                <%
                for (Groupe g : groupes) {

                    boolean checked = false;
                    if (groupeSelectionne != null &&
                        groupeSelectionne.getIdGroupe() != null &&
                        groupeSelectionne.getIdGroupe().equals(g.getIdGroupe())) {
                        checked = true;
                    }
           		 %>
                <tr>
                <td>
                    <input type="radio" name="idGroupe"
                           value="<%= g.getIdGroupe() %>"
                           <%= checked ? "checked" : "" %> >
                </td>
                <td><%= g.getIdGroupe() %></td>
                <td><%= g.getNomGroupe() %></td>
            </tr>
                <%
                    }
                %>
            </table>
            <br>
            <button type="submit">Valider</button>
        </form>
    <%
        }
    %>
    <br>
    <form action="CtrlCoach" method="get">
		<input type="hidden" name="action" value="ConvocationGroupe">
		<button type="submit">Revenir à convoquer</button>
	</form>
    <br>
    <form action="CtrlCoach" method="get">
		<input type="hidden" name="action" value="PageCoach">
		<button type="submit">Accueil</button>
	</form>
</body>
</html>
