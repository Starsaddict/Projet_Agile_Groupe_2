<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Evenement" %>
<%@ page import="model.Groupe" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sélection des groupes</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f3f4f6;
        margin: 0;
        padding: 0;
    }

    .container {
        max-width: 900px;
        margin: 40px auto;
        background-color: #ffffff;
        padding: 30px 40px;
        border-radius: 10px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.08);
    }

    h1 {
        margin-top: 0;
        font-size: 24px;
        color: #111827;
    }

    h2 {
        margin-top: 25px;
        font-size: 20px;
        color: #1f2933;
        border-left: 4px solid #2563eb;
        padding-left: 10px;
    }

    .success {
        color: green;
        font-weight: bold;
    }

    .error {
        color: red;
        font-weight: bold;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 12px;
        font-size: 14px;
    }

    table thead th {
        background-color: #2563eb;
        color: #ffffff;
        text-align: left;
        padding: 8px 10px;
    }

    table tbody td {
        border-bottom: 1px solid #e5e7eb;
        padding: 8px 10px;
        vertical-align: top;
    }

    table tbody tr:nth-child(even) {
        background-color: #f9fafb;
    }

    table tbody tr:hover {
        background-color: #eef2ff;
    }

    .btn-primary, .btn-secondary {
        border: none;
        padding: 8px 16px;
        border-radius: 6px;
        font-size: 14px;
        cursor: pointer;
        transition: background-color 0.15s ease, transform 0.05s ease;
    }

    .btn-primary {
        background-color: #2563eb;
        color: white;
    }

    .btn-primary:hover {
        background-color: #1d4ed8;
        transform: translateY(-1px);
    }

    .btn-secondary {
        background-color: #e5e7eb;
        color: #111827;
    }

    .btn-secondary:hover {
        background-color: #d1d5db;
        transform: translateY(-1px);
    }

    .btn-row {
        margin-top: 20px;
        display: flex;
        gap: 10px;
    }

    .empty-msg {
        color: #6b7280;
        font-style: italic;
    }

    hr {
        border: none;
        border-top: 1px solid #e5e7eb;
        margin: 25px 0;
    }
</style>

</head>
<body>

<div class="container">

    <h1>Sélection des groupes à convoquer</h1>

    <%
        String messageSucces = (String) request.getAttribute("messageSucces");
        String messageErreur = (String) request.getAttribute("messageErreur");

        if (messageSucces != null) {
    %>
        <p class="success"><%= messageSucces %></p>
    <%
        }
        if (messageErreur != null) {
    %>
        <p class="error"><%= messageErreur %></p>
    <%
        }

        Evenement evt = (Evenement) request.getAttribute("evenementSelectionne");
    %>

    <h2>Événement sélectionné</h2>

    <%
        if (evt == null) {
    %>
        <p class="empty-msg">Aucun événement sélectionné.</p>
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
    %>
        <p class="empty-msg">Aucun groupe disponible.</p>
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
        <p class="empty-msg">Aucun groupe n'a encore été convoqué pour cet événement.</p>
    <%
            }
    %>

        <form action="CtrlConvoquer" method="post">
            <input type="hidden" name="action" value="validerConvocation">
            <input type="hidden" name="idEvenement" value="<%= evt.getIdEvenement() %>">

            <table>
                <thead>
                <tr>
                    <th>Sélection</th>
                    <th>ID Groupe</th>
                    <th>Nom du groupe</th>
                </tr>
                </thead>

                <tbody>
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
                </tbody>
            </table>

            <div class="btn-row">
                <button type="submit" class="btn-primary">Valider</button>
            </div>

        </form>

    <%
        }
    %>

    <div class="btn-row">
        <form action="CtrlCoach" method="get">
            <input type="hidden" name="action" value="ConvocationGroupe">
            <button type="submit" class="btn-secondary">Retour à la page précédente</button>
        </form>

        <form action="CtrlCoach" method="get">
            <input type="hidden" name="action" value="PageCoach">
            <button type="submit" class="btn-secondary">Retour à l'accueil</button>
        </form>
    </div>

</div>

</body>
</html>
