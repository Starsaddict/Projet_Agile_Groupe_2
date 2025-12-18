<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    // Sécurité : utilisateur connecté
    if (session.getAttribute("user") == null) {
        response.sendRedirect("Login");
        return;
    }

    Long totalGlobal = (Long) request.getAttribute("totalPresences");
    Long presentsGlobal = (Long) request.getAttribute("presencesEffectives");
    Double taux = (Double) request.getAttribute("tauxPresence");

    if (taux == null) {
        taux = 0.0;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Statistiques de présence</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .subtitle { text-align: center; color: var(--text-muted); margin-bottom: 20px; }
        .stat-box {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
            gap: 12px;
            margin: 12px 0 24px;
        }
        .stat {
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            padding: 12px;
            text-align: center;
            box-shadow: var(--shadow-sm);
        }
        .stat strong {
            display: block;
            font-size: 26px;
            color: var(--text);
        }
        .taux {
            text-align: center;
            font-size: 32px;
            font-weight: bold;
            margin: 12px 0 4px;
        }
        .taux.bon { color: #16a34a; }
        .taux.moyen { color: #f59e0b; }
        .taux.faible { color: #dc2626; }
        .qualite {
            text-align: center;
            font-size: 16px;
            font-weight: 500;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        th, td {
            padding: 8px;
            border-bottom: 1px solid var(--border);
            text-align: center;
        }
        th { background: #f3f4f6; }
        .info {
            text-align: center;
            color: var(--text-muted);
            font-size: 14px;
            margin-top: 16px;
        }
    </style>
</head>

<body>
<%@ include file="/jsp/header.jspf" %>
<div class="container">

    <div class="card">
    <h1>Statistiques de présence</h1>
    <p class="subtitle">Entraînements uniquement</p>

    <!-- ================= STATS GLOBALES ================= -->
    <div class="stat-box">
        <div class="stat">
            <strong><%= totalGlobal != null ? totalGlobal : 0 %></strong>
            présences renseignées
        </div>
        <div class="stat">
            <strong><%= presentsGlobal != null ? presentsGlobal : 0 %></strong>
            présents
        </div>
    </div>

    <%
        String niveau;
        String message;

        if (taux >= 80) {
            niveau = "bon";
            message = "Excellent taux de présence 👍";
        } else if (taux >= 50) {
            niveau = "moyen";
            message = "Taux de présence moyen ⚠️";
        } else {
            niveau = "faible";
            message = "Taux de présence faible ❌";
        }
    %>

    <div class="taux <%= niveau %>">
        <%= String.format("%.1f", taux) %> %
    </div>

    <div class="qualite">
        <%= message %>
    </div>

    <!-- ================= STATS PAR JOUEUR ================= -->
    <h2>Présence par joueur (entraînements)</h2>

    <table>
        <thead>
            <tr>
                <th>Joueur</th>
                <th>Présents</th>
                <th>Total</th>
                <th>Taux</th>
            </tr>
        </thead>
        <tbody>
        <%
            List<Object[]> rows =
                (List<Object[]>) request.getAttribute("statsParJoueur");

            if (rows != null && !rows.isEmpty()) {
                for (Object[] r : rows) {
                    String nom = (String) r[0];
                    String prenom = (String) r[1];
                    long totalJoueur = (Long) r[2];
                    long presentsJoueur = (Long) r[3];
                    double tauxJoueur = totalJoueur > 0
                            ? (presentsJoueur * 100.0) / totalJoueur
                            : 0;
        %>
            <tr>
                <td><%= prenom %> <%= nom %></td>
                <td><%= presentsJoueur %></td>
                <td><%= totalJoueur %></td>
                <td><%= String.format("%.1f", tauxJoueur) %> %</td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4">Aucune donnée disponible</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <p class="info">
        Le taux de présence est calculé uniquement à partir des présences
        réellement enregistrées lors des entraînements.
    </p>

    <!-- ================= ACTIONS ================= -->
    <div class="actions" style="text-align:center; margin-top:24px;">
        <a class="btn" href="CtrlCoach?action=Home">
            Retour à l’accueil Coach
        </a>
    </div>

</div>
</div>
</body>
</html>
