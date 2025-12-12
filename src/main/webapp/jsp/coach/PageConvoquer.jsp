<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Evenement"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Convocation des groupes</title>

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

        .subtitle {
            color: #6b7280;
            font-size: 14px;
            margin-top: -5px;
            margin-bottom: 20px;
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
            color: #ffffff;
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
            margin-top: 8px;
        }

        hr {
            border: none;
            border-top: 1px solid #e5e7eb;
            margin: 25px 0;
        }
    </style>
</head>
<body>

<%
    // Récupérer la liste des événements passée par le contrôleur
    List<Evenement> evenements = (List<Evenement>) request.getAttribute("evenements");
%>

<div class="container">
    <h1>Convocation des groupes</h1>
    <p class="subtitle">Sélectionnez un événement pour convoquer les groupes.</p>

    <h2>Événements à venir</h2>

    <%
        if (evenements == null || evenements.isEmpty()) {
    %>
        <p class="empty-msg">Aucun événement à venir.</p>
    <%
        } else {
    %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Date</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (Evenement e : evenements) {
            %>
                <tr>
                    <td><%= e.getIdEvenement() %></td>
                    <td><%= e.getNomEvenement() %></td>
                    <td><%= e.getDateEvenement() %></td>
                    <td>
                        <form action="CtrlConvoquer" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="selectionEvenement">
                            <input type="hidden" name="idEvenement" value="<%= e.getIdEvenement() %>">
                            <button type="submit" class="btn-primary">À convoquer</button>
                        </form>
                    </td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    <%
        }
    %>

    <div class="btn-row">
        <form action="CtrlCoach" method="get">
            <input type="hidden" name="action" value="PageCoach">
            <button type="submit" class="btn-secondary">Retour à l'accueil</button>
        </form>
    </div>
</div>

</body>
</html>
