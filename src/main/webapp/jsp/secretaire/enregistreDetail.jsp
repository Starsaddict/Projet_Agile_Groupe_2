<%--
  Created by IntelliJ IDEA.
  User: kaiyangzhang
  Date: 2025/12/17
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.ConvocationEvenement, model.Evenement, model.Joueur" %>
<%
    Evenement evenement = (Evenement) request.getAttribute("evenement");
    List<ConvocationEvenement> convocations = (List<ConvocationEvenement>) request.getAttribute("convocations");
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>Entegistre Detail</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
    <style>
        .event-meta {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
            gap: 8px 16px;
            margin-top: 8px;
        }
        .event-meta div {
            padding: 8px 10px;
            background: #f8f8f8;
            border-radius: 6px;
        }
        .player-row { display: flex; align-items: center; gap: 10px; }
        .avatar {
            width: 42px;
            height: 42px;
            border-radius: 50%;
            background: #e6e6e6;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            color: #555;
            overflow: hidden;
        }
        .avatar img { width: 100%; height: 100%; object-fit: cover; }
        .player-name { font-weight: 600; }
        .player-sub { color: #777; font-size: 0.9em; }
    </style>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="action-buttons" style="justify-content: space-between; align-items: center;">
            <h1 style="margin:0;">Détail des présences</h1>
            <a href="<%= contextPath %>/secretaire/enregistrement" class="btn secondary">← Retour</a>
        </div>

        <% if (evenement != null) { %>
            <div class="card" style="background:#f9f9f9; margin-top:12px;">
                <strong>Évènement sélectionné</strong>
                <div class="event-meta">
                    <div><strong>Nom :</strong> <%= evenement.getNomEvenement() %></div>
                    <div><strong>Lieu :</strong> <%= evenement.getLieuEvenement() %></div>
                    <div><strong>Date :</strong> <%= evenement.getDateEvenement() %></div>
                    <div><strong>Type :</strong> <%= evenement.getTypeEvenement() %></div>
                </div>
            </div>
        <% } else { %>
            <div class="alert error" style="margin-top:12px;">Aucun évènement sélectionné.</div>
        <% } %>

        <h2 style="margin-top:16px;">Participants</h2>
        <% if (convocations != null && !convocations.isEmpty()) { %>
            <table class="table">
                <thead>
                <tr>
                    <th>Joueur</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <% for (ConvocationEvenement ce : convocations) {
                       Joueur joueur = ce.getJoueur();
                       String prenom = joueur != null ? joueur.getPrenomUtilisateur() : "";
                       String nom = joueur != null ? joueur.getNomUtilisateur() : "";
                       String avatar = joueur != null ? joueur.getProfilePicRoute() : null;
                       String initial = "?";
                       if (prenom != null && !prenom.isEmpty()) {
                           initial = prenom.substring(0,1).toUpperCase();
                       } else if (nom != null && !nom.isEmpty()) {
                           initial = nom.substring(0,1).toUpperCase();
                       }
                       Boolean presenceReelle = ce.getPresenceReelle();
                       String nameColor = "inherit";
                       if (Boolean.TRUE.equals(presenceReelle)) {
                           nameColor = "green";
                       } else if (Boolean.FALSE.equals(presenceReelle)) {
                           nameColor = "red";
                       }
                %>
                    <tr>
                        <td data-label="Joueur">
                            <div class="player-row">
                                <div class="avatar">
                                    <% if (avatar != null && !avatar.isEmpty()) { %>
                                        <img src="<%= avatar %>" alt="Avatar">
                                    <% } else { %>
                                        <%= initial %>
                                    <% } %>
                                </div>
                                <div>
                                    <div class="player-name" style="color:<%= nameColor %>;"><%= nom %> <%= prenom %></div>
                                    <% if (joueur != null && joueur.getNumeroJoueur() != null) { %>
                                        <div class="player-sub">N° <%= joueur.getNumeroJoueur() %></div>
                                    <% } %>
                                </div>
                            </div>
                        </td>
                        <td data-label="Action">
                            <%
                                String evenementId = (evenement != null && evenement.getIdEvenement() != null)
                                        ? evenement.getIdEvenement().toString()
                                        : (ce.getEvenement() != null && ce.getEvenement().getIdEvenement() != null
                                            ? ce.getEvenement().getIdEvenement().toString()
                                            : "");
                                String joueurId = (joueur != null && joueur.getIdUtilisateur() != null)
                                        ? joueur.getIdUtilisateur().toString()
                                        : "";
                                boolean disablePresent = Boolean.TRUE.equals(presenceReelle);
                                boolean disableAbsent = Boolean.FALSE.equals(presenceReelle);
                            %>
                            <div class="action-buttons" style="gap:8px; justify-content:flex-start;">
                                <form action="<%= contextPath %>/secretaire/enregistrement/confirmPresent" method="post" style="margin:0;">
                                    <input type="hidden" name="id" value="<%= evenementId %>">
                                    <input type="hidden" name="joueurId" value="<%= joueurId %>">
                                    <input type="hidden" name="action" value="present">
                                    <button type="submit" class="btn" <%= disablePresent ? "disabled" : "" %>>Marquer présent</button>
                                </form>
                                <form action="<%= contextPath %>/secretaire/enregistrement/confirmPresent" method="post" style="margin:0;">
                                    <input type="hidden" name="id" value="<%= evenementId %>">
                                    <input type="hidden" name="joueurId" value="<%= joueurId %>">
                                    <input type="hidden" name="action" value="absent">
                                    <button type="submit" class="btn secondary" <%= disableAbsent ? "disabled" : "" %>>Marquer absent</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        <% } else { %>
            <div class="empty">Aucun joueur à afficher.</div>
        <% } %>
    </div>
</div>
</body>
</html>
