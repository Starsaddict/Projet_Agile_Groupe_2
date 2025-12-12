<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Espace Parent</title>

    <!-- Bootstrap -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <!-- Bootstrap Icons -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body {
            background-color: #f4f6f9;
        }

        .page-wrapper {
            max-width: 1000px;
            margin: 70px auto;
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
        <h1 class="page-title">👨‍👩‍👧 Espace Parent</h1>
        <p class="text-muted mt-2">
            Gestion des déplacements, convocations et documents médicaux
        </p>
    </div>

    <!-- Actions -->
    <div class="row g-4">

        <!-- Proposer covoiturage -->
        <div class="col-md-6 col-lg-3">
            <a href="${pageContext.request.contextPath}/jsp/covoiturage/proposer.jsp"
               class="text-decoration-none text-dark">

                <div class="card action-card p-4 text-center">
                    <div class="icon-box bg-primary text-white">
                        <i class="bi bi-car-front-fill"></i>
                    </div>
                    <h5>Proposer un covoiturage</h5>
                    <p class="text-muted small">
                        Offrir un trajet pour un événement
                    </p>
                </div>

            </a>
        </div>

        <!-- Modifier profil -->
        <div class="col-md-6 col-lg-3">
            <a href="${pageContext.request.contextPath}/jsp/parent/modifierProfil.jsp"
               class="text-decoration-none text-dark">

                <div class="card action-card p-4 text-center">
                    <div class="icon-box bg-warning text-white">
                        <i class="bi bi-person-gear"></i>
                    </div>
                    <h5>Modifier le profil</h5>
                    <p class="text-muted small">
                        Mettre à jour vos informations
                    </p>
                </div>

            </a>
        </div>

        <!-- Valider convocation -->
        <div class="col-md-6 col-lg-3">
            <a href="${pageContext.request.contextPath}/jsp/convocation/liste.jsp"
               class="text-decoration-none text-dark">

                <div class="card action-card p-4 text-center">
                    <div class="icon-box bg-success text-white">
                        <i class="bi bi-envelope-check-fill"></i>
                    </div>
                    <h5>Convocations</h5>
                    <p class="text-muted small">
                        Valider ou refuser une convocation
                    </p>
                </div>

            </a>
        </div>

        <!-- Certificat médical -->
        <div class="col-md-6 col-lg-3">
            <a href="${pageContext.request.contextPath}/jsp/certificat/deposer.jsp"
               class="text-decoration-none text-dark">

                <div class="card action-card p-4 text-center">
                    <div class="icon-box bg-danger text-white">
                        <i class="bi bi-heart-pulse-fill"></i>
                    </div>
                    <h5>Certificat médical</h5>
                    <p class="text-muted small">
                        Déposer et déclarer un certificat
                    </p>
                </div>

            </a>
        </div>

    </div>

</div>

</body>
</html>
