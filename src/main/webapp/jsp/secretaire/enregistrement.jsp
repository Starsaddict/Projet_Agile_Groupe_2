<%--
  Created by IntelliJ IDEA.
  User: kaiyangzhang
  Date: 2025/12/17
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Evenement" %>
<%
    List<Evenement> events = (List<Evenement>) request.getAttribute("events");
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>Enregistrement</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <div class="action-buttons" style="justify-content: space-between; align-items: center;">
            <h1 style="margin:0;">Liste des évènements</h1>
            <a href="<%= contextPath %>/jsp/home.jsp" class="btn secondary">← Retour à l’accueil</a>
        </div>

        <% if (events != null && !events.isEmpty()) { %>
            <table class="table">
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Lieu</th>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Présence</th>
                </tr>
                </thead>
                <tbody>
                <% for (Evenement evt : events) { %>
                    <tr>
                        <td data-label="Nom"><%= evt.getNomEvenement() %></td>
                        <td data-label="Lieu"><%= evt.getLieuEvenement() %></td>
                        <td data-label="Date"><%= evt.getDateEvenement() %></td>
                        <td data-label="Type"><%= evt.getTypeEvenement() %></td>
                        <td data-label="Présence">
                            <form action="<%= contextPath %>/secretaire/enregistrement" method="post" style="margin: 0;">
                                <input type="hidden" name="id" value="<%= evt.getIdEvenement() %>">
                                <button type="submit" class="btn">Gérer les présences</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        <% } else { %>
            <div class="empty">Aucun évènement à afficher.</div>
        <% } %>
    </div>
</div>
</body>
</html>
