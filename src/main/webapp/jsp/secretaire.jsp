<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Espace Secrétaire</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #eef1f4;
        }

        header {
            background: #34495e;
            color: white;
            padding: 20px;
            text-align: center;
            font-size: 26px;
        }

        .container {
            max-width: 900px;
            margin: 50px auto;
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 30px;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 35px;
            text-align: center;
            box-shadow: 0 6px 15px rgba(0,0,0,0.1);
            transition: transform 0.2s;
        }

        .card:hover {
            transform: translateY(-6px);
        }

        .card h2 {
            color: #2c3e50;
            margin-bottom: 15px;
        }

        .card p {
            color: #555;
            margin-bottom: 25px;
        }

        .card a {
            text-decoration: none;
            background: #2980b9;
            color: white;
            padding: 12px 30px;
            border-radius: 6px;
            font-size: 16px;
            display: inline-block;
        }

        .card a:hover {
            background: #1f6391;
        }

        footer {
            margin-top: 60px;
            text-align: center;
            color: #888;
        }
    </style>
</head>

<body>

<header>
    🧑‍💼 Espace Secrétaire
</header>

<div class="container">

    <div class="card">
        <h2>📅 Gérer les événements</h2>
        <p>Créer, modifier et supprimer des événements.</p>
        <a href="<%=request.getContextPath()%>/evenementSecre">Accéder</a>
    </div>

    <div class="card">
        <h2>👤 Gestion des profils</h2>
        <p>Créer et modifier les comptes des membres.</p>
        <a href="<%=request.getContextPath()%>/jsp/secretaire/profil.jsp">Accéder</a>
    </div>

</div>

<footer>
    © Projet Agile — Espace Secrétaire
</footer>

</body>
</html>
