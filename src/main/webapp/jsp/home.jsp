<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="model.Groupe, model.Joueur, model.Evenement"%>
<%@ page import="java.util.List"%>
<%
if (session.getAttribute("user") == null) {
    response.sendRedirect("Login");
    return;
}
%>
<c:set var="roles" value="${sessionScope.roles}" />
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .center-stat {
            justify-content: center;
        }
    </style>
</head>

<body>
<%@ include file="/jsp/header.jspf" %>

<div class="container">
    <div class="card">
        <div class="top-bar">
            <div>
                <h1>Tableau de bord</h1>
                <p>Contenu affiché selon votre rôle</p>
            </div>
            <div>
                <c:if test="${not empty sessionScope.user}">
                    <span class="pill blue">${sessionScope.user.emailUtilisateur}</span>
                </c:if>
            </div>
        </div>

        <div class="section-grid">
            <!-- ================= SECTION COACH ================= -->
            <c:if test="${not empty roles and fn:contains(roles, 'Coach')}">
                <%
                List<Evenement> evenements = (List<Evenement>) request.getAttribute("evenements");
                List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupes");
                %>
                <div class="card">
                    <span class="pill blue">Coach</span>
                    <h2>Bienvenue, Coach ${sessionScope.user.prenomUtilisateur} ${sessionScope.user.nomUtilisateur}!</h2>

                    <!-- Section Evenements -->
                    <h2>Évènements officiels à venir</h2>
                    <p>
                        Vous pouvez choisir ou modifier les convocations.
                        <% if (evenements != null) { %>
                            <span class="badge"><%=evenements.size()%> évènement(s)</span>
                        <% } %>
                    </p>

                    <table>
                        <thead>
                            <tr>
                                <th>Titre</th>
                                <th>Date</th>
                                <th>Type</th>
                                <th>Groupe</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            if (evenements != null && !evenements.isEmpty()) {
                                for (Evenement e : evenements) {
                            %>
                            <tr>
                                <td><%=e.getNomEvenement()%></td>
                                <td><%=e.getDateEvenement()%></td>
                                <td><%=e.getTypeEvenement()%></td>
                                <td>
                                    <% if (e.getGroupe() != null) { %>
                                        <%=e.getGroupe().getNomGroupe()%>
                                    <% } else { %>
                                        <span class="empty">Aucun groupe convoqué.</span>
                                    <% } %>
                                </td>
                                <td>
                                    <form action="CtrlConvoquer" method="get" style="display: inline;">
                                        <input type="hidden" name="action" value="selectionEvenement">
                                        <input type="hidden" name="idEvenement" value="<%=e.getIdEvenement()%>">
                                        <button type="submit" class="btn btn-primary">À convoquer</button>
                                    </form>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="5" class="empty">Aucun évènement officiel à venir.</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>

                    <!-- Section Groupes -->
                    <h2>Groupes existants</h2>
                    <p>
                        Vue simple des groupes existants.
                        <% if (groupes != null) { %>
                            <span class="badge"><%=groupes.size()%> groupe(s)</span>
                        <% } %>
                    </p>

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
                                <td><%=g.getNomGroupe()%></td>
                                <td>
                                    <%
                                    List<Joueur> joueurs = g.getJoueurs();
                                    if (joueurs != null && !joueurs.isEmpty()) {
                                        for (int i = 0; i < joueurs.size(); i++) {
                                            Joueur j = joueurs.get(i);
                                    %>
                                        <%=j.getPrenomUtilisateur()%> <%=j.getNomUtilisateur()%><%=(i < joueurs.size() - 1) ? ", " : ""%>
                                    <%
                                        }
                                    } else {
                                    %>
                                        <span class="empty">Aucun joueur dans ce groupe.</span>
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
                                <td colspan="2" class="empty">Aucun groupe trouvé.</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>

                    <div class="links">
                        <a class="btn btn-primary" href="CtrlCoach?action=GestionGroupe">Gestion des groupes</a>
                    </div>

                    <!-- Section Statistiques -->
                    <h2>Statistiques</h2>
                    <p>Analyse du taux de présence des joueurs aux entraînements.</p>

                    <div class="links center-stat">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/CtrlStatistique?action=presenceEntrainement">
                            📊 Taux de présence aux entraînements
                        </a>
                    </div>
                </div>
            </c:if>

            <!-- ================= SECTION PARENT ================= -->
            <c:if test="${not empty roles and fn:contains(roles, 'Parent')}">
                <div class="card">
                    <span class="pill green">Parent</span>
                    <h2>Services Parent</h2>
                    <p>Accès rapide aux covoiturages, au profil et aux absences de ses enfants.</p>
                    <div class="links">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/parent/covoiturage">Covoiturage</a>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/parent/profil">Profil parent</a>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/CtrlFrontAbsence">Gérer les absences</a>
                    </div>
                </div>
            </c:if>

            <!-- ================= SECTION SECRETAIRE ================= -->
            <c:if test="${not empty roles and fn:contains(roles, 'Secretaire')}">
                <div class="card">
                    <span class="pill pink">Secrétaire</span>
                    <h2>Administration</h2>
                    <p>Créer/éditer des comptes et gérer les événements.</p>
                    <div class="links">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/evenementSecre">Gestion des événements</a>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/secretaire/profil">Gestion des profils</a>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/secretaire/convoquer?type=match">Convoquer des groupes</a>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/secretaire/enregistrement">Enregistrer les présences</a>
                    </div>
                </div>
            </c:if>

            <!-- ================= SECTION JOUEUR ================= -->
            <c:if test="${not empty roles and fn:contains(roles, 'Joueur')}">
                <div class="card">
                    <span class="pill blue">Joueur</span>
                    <h2>Vue Joueur</h2>
                    <p>Consultez votre profil.</p>
                    <div class="links">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/joueur/profil">Profil</a>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>