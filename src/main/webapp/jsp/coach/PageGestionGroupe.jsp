<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Groupe, model.Joueur"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestion des groupes</title>

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

    form {
        margin-top: 10px;
        margin-bottom: 15px;
    }

    label {
        font-weight: 500;
        color: #374151;
    }

    input[type="text"] {
        padding: 6px 8px;
        border-radius: 6px;
        border: 1px solid #d1d5db;
        width: 250px;
        margin-left: 8px;
    }

    input[type="checkbox"] {
        margin-right: 4px;
    }

    .players-list {
        margin-top: 5px;
        padding: 10px 12px;
        border-radius: 8px;
        border: 1px solid #e5e7eb;
        max-height: 220px;
        overflow-y: auto;
        background-color: #f9fafb;
        font-size: 14px;
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

    .btn-primary, .btn-secondary, .btn-danger {
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

    .btn-danger {
        background-color: #ef4444;
        color: #ffffff;
    }

    .btn-danger:hover {
        background-color: #dc2626;
        transform: translateY(-1px);
    }

    .btn-row {
        margin-top: 15px;
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

<%
    List<Joueur> joueurs = (List<Joueur>) request.getAttribute("joueurs");
    List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupes");
%>

<div class="container">
    <h1>Gestion des groupes</h1>
    <p class="subtitle">Créer un nouveau groupe et gérer les groupes existants.</p>

    <!-- Création d'un groupe -->
    <h2>Créer un groupe</h2>

    <form id="creerGroupeForm" action="CtrlCoach" method="post">
        <input type="hidden" name="action" value="EnregistrerGroupe" />

        <div>
            <label>Nom du groupe :</label>
            <input type="text" name="nomGroupe" required />
        </div>

        <br />

        <div>
            <label>Joueurs :</label>
            <div class="players-list">
                <%
                    if (joueurs != null && !joueurs.isEmpty()) {
                        for (Joueur j : joueurs) {
                %>
                    <div>
                        <input type="checkbox" name="joueursIds" value="<%= j.getIdUtilisateur() %>" />
                        <%= j.getNomUtilisateur() %> <%= j.getPrenomUtilisateur() %>
                    </div>
                <%
                        }
                    } else {
                %>
                    <div class="empty-msg">Aucun joueur disponible.</div>
                <%
                    }
                %>
            </div>
        </div>

        <div class="btn-row">
            <button type="submit" class="btn-primary">Créer</button>
        </div>
    </form>

    <hr>

    <!-- Groupes existants -->
    <h2>Groupes existants</h2>

    <table>
        <thead>
            <tr>
                <th>Nom</th>
                <th>Joueurs</th>
                <th>Action</th>
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
                        List<Joueur> joueursG = g.getJoueurs();
                        if (joueursG != null && !joueursG.isEmpty()) {
                            for (Joueur j : joueursG) {
                    %>
                        <%= j.getNomUtilisateur() %> <%= j.getPrenomUtilisateur() %><br />
                    <%
                            }
                        } else {
                    %>
                        <span class="empty-msg">Aucun joueur dans ce groupe.</span>
                    <%
                        }
                    %>
                </td>
                <td>
                    <form action="CtrlCoach" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="SupprimerGroupe" />
                        <input type="hidden" name="idGroupe" value="<%= g.getIdGroupe() %>" />
                        <button type="submit"
                                class="btn-danger"
                                onclick="return confirm('Voulez-vous vraiment supprimer ce groupe ?');">
                            Supprimer
                        </button>
                    </form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="3" class="empty-msg">Aucun groupe trouvé.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="btn-row">
        <form action="CtrlCoach" method="get">
            <input type="hidden" name="action" value="PageCoach" />
            <button type="submit" class="btn-secondary">Retour à l'accueil</button>
        </form>
    </div>
</div>

<script>
    document.getElementById("creerGroupeForm").addEventListener("submit", function(event) {
        const checkboxes = document.querySelectorAll('input[name="joueursIds"]:checked');
        if (checkboxes.length === 0) {
            alert("Veuillez sélectionner au moins un joueur !");
            event.preventDefault();
        }
    });
</script>

</body>
</html>
