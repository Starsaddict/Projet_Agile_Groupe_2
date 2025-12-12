<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Groupe, model.Joueur, model.Evenement"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PageCoach</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f3f4f6;
        margin: 0;
        padding: 0;
    }

    .container {
        max-width: 1100px;
        margin: 40px auto;
        background-color: #ffffff;
        padding: 30px 40px;
        border-radius: 10px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.08);
    }

    h1 {
        margin-top: 0;
        font-size: 26px;
        color: #111827;
    }

    h2 {
        margin-top: 30px;
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
        padding: 10px;
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

    .btn-row {
        margin-top: 20px;
        display: flex;
        gap: 10px;
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

    .section-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .badge {
        display: inline-block;
        padding: 3px 8px;
        border-radius: 9999px;
        font-size: 11px;
        background-color: #e5f2ff;
        color: #1d4ed8;
        margin-left: 6px;
    }

    .empty-msg {
        color: #6b7280;
        font-style: italic;
        padding: 8px 0;
    }
</style>
</head>
<body>

<%
    model.Utilisateur user = (model.Utilisateur) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("Login");
        return;
    }

    List<Evenement> evenements = (List<Evenement>) request.getAttribute("evenements");
    List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupes");
%>

<div class="container">

    <!-- Titre & info coach -->
    <h1>
        Bienvenue, Coach
        <%= user.getPrenomUtilisateur() %>
        <%= user.getNomUtilisateur() %> !
    </h1>
    <p class="subtitle">Tableau de bord – aperçu des prochains évènements et des groupes existants.</p>

	<div class="btn-row">
        <form action="CtrlLogout" method="get">
            <button type="submit" class="btn-primary">Déconnection</button>
        </form>
    </div>
    
    <!-- Section Évènements -->
    <div class="section-header">
        <h2>Évènements à venir</h2>
        <% if (evenements != null) { %>
            <span class="badge"><%= evenements.size() %> évènement(s)</span>
        <% } %>
    </div>

    <table>
        <thead>
            <tr>
                <th>Titre</th>
                <th>Date</th>
                <th>Type</th>
                <th>Groupe</th>
            </tr>
        </thead>
        <tbody>
        <%
            if (evenements != null && !evenements.isEmpty()) {
                for (Evenement e : evenements) {
        %>
            <tr>
                <td><%= e.getNomEvenement() %></td>
                <td><%= e.getDateEvenement() %></td>
                <td><%= e.getTypeEvenement() %></td>
                <td>
                    <%
                        if (e.getGroupe() != null) {
                    %>
                        <%= e.getGroupe().getNomGroupe() %>
                    <%
                        } else {
                    %>
                        <span class="empty-msg">Aucun groupe convoqué.</span>
                    <%
                        }
                    %>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4" class="empty-msg">Aucun évènement trouvé.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="btn-row">
        <form action="CtrlCoach" method="get">
            <input type="hidden" name="action" value="ConvocationGroupe">
            <button type="submit" class="btn-secondary">Convoquer un groupe</button>
        </form>
    </div>

    <hr>

    <!-- Section Groupes -->
    <div class="section-header">
        <h2>Groupes existants</h2>
        <% if (groupes != null) { %>
            <span class="badge"><%= groupes.size() %> groupe(s)</span>
        <% } %>
    </div>

    <table>
        <thead>
            <tr>
                <th>Nom du groupe</th>
                <th>Joueurs</th>
            </tr>
        </thead>
        <tbody>
        <%
            if (groupes != null && !groupes.isEmpty()) {
                for (Groupe g : groupes) {
        %>
            <tr>
                <td><%= g.getNomGroupe() %></td>
                <td>
                    <%
                        List<Joueur> joueurs = g.getJoueurs();
                        if (joueurs != null && !joueurs.isEmpty()) {
                            for (int i = 0; i < joueurs.size(); i++) {
                                Joueur j = joueurs.get(i);
                    %>
                                <%= j.getPrenomUtilisateur() %> <%= j.getNomUtilisateur() %><%= (i < joueurs.size() - 1) ? ", " : "" %>
                    <%
                            }
                        } else {
                    %>
                            <span class="empty-msg">Aucun joueur dans ce groupe.</span>
                    <%
                        }
                    %>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="2" class="empty-msg">Aucun groupe trouvé.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="btn-row">
        <form action="CtrlCoach" method="get">
            <input type="hidden" name="action" value="GestionGroupe">
            <button type="submit" class="btn-secondary">Créer un groupe</button>
        </form>
    </div>

</div>

</body>
</html>
