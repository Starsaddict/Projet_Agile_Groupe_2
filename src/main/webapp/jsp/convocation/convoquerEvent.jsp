<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Convocations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="container">
    <!-- Header avec titre et actions -->
    <div class="top-bar">
        <div>
            <h1>Convocations</h1>
            <p>
                <c:out value="${mode == 'match' ? 'Matches' : 'Autres événements'}" />
                - Sélectionnez un événement à convoquer
            </p>
        </div>
        <div class="links">
            <a href="${ctx}/secretaire/profil" class="btn btn-secondary">Retour au profil</a>
            <form method="post" action="${ctx}/CtrlLogout" style="display: inline;">
                <button type="submit" class="btn btn-secondary" style="display: inline-flex; align-items: center; gap: 0.5rem;">
                    <svg class="btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" width="16" height="16">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
                    </svg>
                    Déconnexion
                </button>
            </form>
        </div>
    </div>

    <!-- Filtre par type d'événement -->
    <div class="card" style="margin-bottom: 1.5rem;">
        <div style="display: flex; align-items: center; gap: 1rem; flex-wrap: wrap;">
            <div style="display: flex; align-items: center; gap: 0.5rem;">
                <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" width="20" height="20">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                </svg>
                <strong>Filtrer par type :</strong>
            </div>
            <div style="display: flex; gap: 0.5rem; flex-wrap: wrap;">
                <a class="btn ${mode == 'match' ? 'btn-primary' : 'btn-secondary'}" 
                   href="${ctx}/secretaire/convoquer?type=match"
                   style="display: inline-flex; align-items: center; gap: 0.5rem;">
                    <svg class="btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" width="16" height="16">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"></path>
                    </svg>
                    Matches
                </a>
                <a class="btn ${mode != 'match' ? 'btn-primary' : 'btn-secondary'}" 
                   href="${ctx}/secretaire/convoquer?type=event"
                   style="display: inline-flex; align-items: center; gap: 0.5rem;">
                    <svg class="btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" width="16" height="16">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                    </svg>
                    Autres événements
                </a>
            </div>
        </div>
    </div>

    <!-- Liste des événements -->
    <c:choose>
        <c:when test="${empty evenements}">
            <div class="card" style="text-align: center; padding: 3rem 2rem;">
                <div class="empty">
                    <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" width="48" height="48" style="margin-bottom: 1rem;">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                    </svg>
                    <h3 style="color: var(--text-muted); margin-bottom: 0.5rem;">Aucun événement disponible</h3>
                    <p style="color: var(--text-muted);">Il n'y a actuellement aucun événement à convoquer pour ce type.</p>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="section-grid" style="grid-template-columns: repeat(auto-fill, minmax(350px, 1fr)); gap: 1.5rem;">
                <c:forEach var="evt" items="${evenements}">
                    <div class="card" style="display: flex; flex-direction: column; height: 100%; padding: 1.5rem;">
                        <!-- En-tête de l'événement -->
                        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 1rem;">
                            <div style="flex: 1;">
                                <h2 style="font-size: 1.25rem; margin-bottom: 0.5rem; color: var(--text);">
                                    <c:out value="${evt.nomEvenement}" />
                                </h2>
                                <div style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 0.5rem;">
                                    <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" width="16" height="16">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
                                    </svg>
                                    <span style="font-size: 0.875rem; color: var(--text-muted);">
                                        <c:out value="${evt.lieuEvenement}" />
                                    </span>
                                </div>
                                <div style="display: flex; align-items: center; gap: 0.5rem;">
                                    <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" width="16" height="16">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                                    </svg>
                                    <span style="font-size: 0.875rem; color: var(--text-muted);">
                                        <c:out value="${fn:replace(evt.dateEvenement, '-', '/')}" />
                                    </span>
                                </div>
                                <c:if test="${mode == 'match' and not empty evt.groupe}">
                                    <div style="display: flex; align-items: center; gap: 0.5rem; margin-top: 0.5rem;">
                                        <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" width="16" height="16">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"></path>
                                        </svg>
                                        <span style="font-size: 0.875rem; color: var(--text-muted);">
                                            Groupe : <strong><c:out value="${evt.groupe.nomGroupe}" /></strong>
                                        </span>
                                    </div>
                                </c:if>
                            </div>
                            <span class="pill ${mode == 'match' ? 'blue' : 'green'}">
                                <c:out value="${evt.typeEvenement}" />
                            </span>
                        </div>

                        <c:if test="${mode == 'match'}">
                            <!-- Section joueurs -->
                            <div style="margin: 1.5rem 0; flex: 1;">
                                <div style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 0.75rem;">
                                    <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" width="18" height="18">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197m13.5 2.5l-2.914 2.914a2 2 0 01-2.828 0l-2.829-2.829"></path>
                                    </svg>
                                    <h3 style="font-size: 1rem; margin: 0; color: var(--text-muted);">Joueurs concernés</h3>
                                </div>
                                
                                <c:choose>
                                    <c:when test="${not empty evt.groupe and not empty evt.groupe.joueurs}">
                                        <div style="display: flex; flex-wrap: wrap; gap: 0.5rem;">
                                            <c:forEach var="j" items="${evt.groupe.joueurs}" varStatus="st">
                                                <c:if test="${st.index < 4}">
                                                    <span class="player-badge">
                                                        <c:out value="${j.prenomUtilisateur}" /> <c:out value="${j.nomUtilisateur}" />
                                                    </span>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${fn:length(evt.groupe.joueurs) > 4}">
                                                <span class="player-badge" style="background: var(--primary); color: white;">
                                                    +<c:out value="${fn:length(evt.groupe.joueurs) - 4}" /> autres
                                                </span>
                                            </c:if>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="empty" style="padding: 1rem; background: var(--bg-body); border-radius: var(--radius); text-align: center;">
                                            <p style="margin: 0; color: var(--text-muted); font-size: 0.875rem;">
                                                Aucun joueur associé à ce groupe
                                            </p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:if>

                        <!-- Bouton convoquer -->
                        <div style="margin-top: auto;">
                            <form method="post" action="${ctx}/secretaire/convoquer" style="margin: 0;">
                                <input type="hidden" name="idEvenement" value="${evt.idEvenement}">
                                <input type="hidden" name="type" value="${mode}">
                                <button type="submit" class="btn btn-primary" style="width: 100%; display: flex; align-items: center; justify-content: center; gap: 0.5rem;">
                                    <svg class="btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" width="16" height="16">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"></path>
                                    </svg>
                                    Convoquer
                                </button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<style>
    /* Styles spécifiques à cette page */
    .player-badge {
        display: inline-flex;
        align-items: center;
        padding: 0.375rem 0.75rem;
        background: var(--bg-body);
        border: 1px solid var(--border);
        border-radius: 2rem;
        font-size: 0.8125rem;
        color: var(--text);
        font-weight: 500;
    }
    
    .player-badge:hover {
        background: var(--border);
        transform: translateY(-1px);
        transition: all 0.2s;
    }
    
    .card:hover {
        transform: translateY(-2px);
        transition: all 0.2s;
    }
    
    .pill {
        font-size: 0.75rem;
        padding: 0.25rem 0.75rem;
        border-radius: 2rem;
        font-weight: 600;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }
    
    .pill.blue {
        background: var(--primary);
        color: white;
    }
    
    .pill.green {
        background: var(--success);
        color: white;
    }
    
    /* Responsive */
    @media (max-width: 768px) {
        .section-grid {
            grid-template-columns: 1fr !important;
        }
        
        .top-bar .links {
            flex-direction: column;
            width: 100%;
        }
        
        .top-bar .links .btn {
            width: 100%;
            justify-content: center;
        }
        
        .card {
            padding: 1.25rem !important;
        }
    }
    
    @media (max-width: 480px) {
        .container {
            padding: 1rem 0.75rem;
        }
        
        h1 {
            font-size: 1.5rem;
        }
    }
</style>

</body>
</html>
