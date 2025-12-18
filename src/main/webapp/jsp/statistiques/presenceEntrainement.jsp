<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>

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

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f6f7fb;
        }

        .container {
            max-width: 800px;
            margin: 60px auto;
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.08);
        }

        h1 {
            text-align: center;
            margin-bottom: 6px;
        }

        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
        }

        .stat-box {
            display: flex;
            justify-content: space-around;
            margin-bottom: 25px;
        }

        .stat {
            text-align: center;
        }

        .stat strong {
            display: block;
            font-size: 26px;
            color: #2c3e50;
        }

        .taux {
            text-align: center;
            font-size: 38px;
            font-weight: bold;
            margin: 20px 0 5px;
        }

        .taux.bon {
            color: #16a34a;
        }

        .taux.moyen {
            color: #f59e0b;
        }

        .taux.faible {
            color: #dc2626;
        }

        .qualite {
            text-align: center;
            font-size: 16px;
            font-weight: 500;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 8px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }

        th {
            background: #f3f4f6;
        }

        .info {
            text-align: center;
            color: #555;
            font-size: 14px;
            margin-top: 25px;
        }

        .actions {
            text-align: center;
            margin-top: 30px;
        }

        .btn {
            display: inline-block;
            padding: 10px 18px;
            background: #1d4ed8;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }

        .btn:hover {
            background: #153ea8;
        }
    </style>
</head>

<body>
<div class="container">

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
    <div class="actions">
        <a class="btn" href="<%= request.getContextPath() %>/jsp/home.jsp">
            ⬅ Retour à l’accueil Coach
        </a>
    </div>

</div>
</body>
</html>
