<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Espace Secrétaire</title>

    <!-- Bootstrap -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <!-- Icons -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body {
            background-color: #f4f6f9;
        }

        .page-wrapper {
            max-width: 900px;
            margin: 80px auto;
        }

        .page-title {
            font-weight: 700;
        }

        .action-card {
            border-radius: 14px;
            transition: all 0.25s ease;
            height: 100%;
        }

        .action-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.12);
        }

        .icon-box {
            width: 70px;
            height: 70px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2rem;
            margin: 0 auto 15px;
        }
    </style>
</head>

<body>

<div class="page-wrapper">

    <!-- Header -->
    <div class="text-center mb-5">
        <h1 class="page-title">👩‍💼 Espace Secrétaire</h1>
        <p class="text-muted mt-2">
            Gestion des événements et des profils
        </p>
    </div>

    <!-- Actions -->
    <div class="row g-4">

        <!-- Événements -->
        <div class="col-md-6">
            <a href="${pageContext.request.contextPath}/evenementSecre"
               class="text-decoration-none text-dark">

                <div class="card action-card p-4 text-center">
                    <div class="icon-box bg-primary text-white">
                        <i class="bi bi-calendar-event"></i>
                    </div>

                    <h4 class="mt-3">Gérer les événements</h4>
                    <p class="text-muted mb-0">
                        Créer, modifier ou supprimer des événements
                    </p>
                </div>

            </a>
        </div>

        <!-- Profils -->
        <div class="col-md-6">
            <a href="${pageContext.request.contextPath}/jsp/secretaire/profil.jsp"
   class="text-decoration-none text-dark">

    <div class="card action-card p-4 text-center">
        <div class="icon-box bg-success text-white">
            <i class="bi bi-person-plus-fill"></i>
        </div>

        <h4 class="mt-3">Créer un profil</h4>
        <p class="text-muted mb-0">
            Ajouter ou modifier un compte utilisateur
        </p>
    </div>

</a>

        </div>

    </div>

</div>

</body>
</html>
