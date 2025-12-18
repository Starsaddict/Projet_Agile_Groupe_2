<!DOCTYPE html>
<html lang="fr">
<head>
    <%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Confirmation de présence - Match</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="container">
        <div class="card">
            <h1>Convocation – Match officiel</h1>

            <div class="info">
                <strong>Joueur :</strong>
                ${convocation.joueur.prenomUtilisateur} ${convocation.joueur.nomUtilisateur}
            </div>

            <div class="info">
                <strong>Match :</strong> ${convocation.evenement.nomEvenement}
            </div>

            <div class="info">
                <strong>Date :</strong> ${convocation.evenement.dateEvenement}
            </div>

            <c:if test="${convocation.confirmePresence != null}">
                <div class="absence-info" style="margin-bottom:1rem;">
                    <div class="info-label">
                        <strong>Réponse actuelle :</strong>
                    </div>
                    <c:choose>
                        <c:when test="${convocation.confirmePresence}">
                            <span class="status-badge status-present">
                                OUI
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span class="status-badge status-absent">
                                NON
                            </span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <c:if test="${convocation.confirmePresence == null}">
                <div class="absence-info no-absence" style="margin-bottom:1rem;">
                    <p><strong>Aucune réponse n’a encore été enregistrée.</strong></p>
                </div>
            </c:if>

            <form method="post">
                <input type="hidden" name="token" value="${convocation.token}" />

                <div class="btn-group">
                    <button type="submit" name="peutJouer" value="oui" class="btn btn-primary">
                        ✅ Le joueur peut jouer
                    </button>

                    <button type="submit" name="peutJouer" value="non" class="btn danger">
                        ❌ Le joueur ne peut pas jouer
                    </button>
                </div>
            </form>

            <div class="note" style="margin-top:1rem;">
                ⚠️ La dernière réponse enregistrée sera prise en compte.
                <br/>
                Cette réponse peut être modifiée à tout moment.
            </div>
        </div>
    </div>

</body>
</html>