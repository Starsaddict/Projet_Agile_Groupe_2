<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Espace Parent</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f6f8;
        }

        header {
            background: #2c3e50;
            color: white;
            padding: 20px;
            text-align: center;
            font-size: 26px;
        }

        .container {
            max-width: 1000px;
            margin: 40px auto;
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 30px;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 30px;
            text-align: center;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            transition: transform 0.2s;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card h2 {
            margin-bottom: 15px;
            color: #2c3e50;
        }

        .card p {
            color: #555;
            margin-bottom: 20px;
        }

        .card a {
            display: inline-block;
            text-decoration: none;
            background: #3498db;
            color: white;
            padding: 12px 25px;
            border-radius: 6px;
            font-size: 16px;
        }

        .card a:hover {
            background: #2980b9;
        }

        footer {
            text-align: center;
            margin-top: 50px;
            color: #888;
        }
    </style>
</head>

<body>

<header>
    👨‍👩‍👧 Espace Parent
</header>

<div class="container">

    <div class="card">
        <h2>🚗 Proposer un covoiturage</h2>
        <p>Proposer ou gérer un trajet pour les événements.</p>
        <a href="<%=request.getContextPath()%>/jsp/covoiturage.jsp">Accéder</a>
    </div>

    <div class="card">
        <h2>👤 Modifier le profil</h2>
        <p>Modifier vos informations personnelles.</p>
        <a href="<%=request.getContextPath()%>/parent/profil">Accéder</a>
    </div>

    <div class="card">
        <h2>📅 Valider une convocation</h2>
        <p>Confirmer la présence à un événement.</p>
        <a href="<%=request.getContextPath()%>/jsp/convocation.jsp">Accéder</a>
    </div>

    <div class="card">
        <h2>📄 Certificat médical</h2>
        <p>Déposer et déclarer un certificat médical.</p>
        <a href="<%=request.getContextPath()%>/jsp/certificat.jsp">Accéder</a>
    </div>

</div>

<footer>
    © Projet Agile — Espace Parent
</footer>

</body>
</html>
