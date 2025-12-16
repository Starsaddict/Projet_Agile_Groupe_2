<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Gestion des absences</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="container">
    <div class="card">
        <h1>Gestion des absences</h1>

        <c:if test="${not empty requestScope.msg_absence}">
            <div class="alert ${requestScope.msg_absence.contains('Erreur') ? 'error' : 'success'}">
                ${requestScope.msg_absence}
            </div>
        </c:if>

        <c:if test="${empty sessionScope.user.joueurs}">
            <p class="empty">Aucun enfant associé à votre compte.</p>
        </c:if>

        <c:forEach var="enfant" items="${sessionScope.user.joueurs}">
            <div class="absence-card">
                <div class="absence-header">
                    <h3>${enfant.prenomUtilisateur} ${enfant.nomUtilisateur}</h3>

                    <c:set var="isAbsent" value="${false}" />
                    <c:set var="openAbsenceId" value="" />
                    <c:set var="absenceDebut" value="" />
                    <c:forEach var="absence" items="${enfant.absences}">
                        <c:if test="${not absence.absenceTerminee}">
                            <c:set var="isAbsent" value="${true}" />
                            <c:set var="openAbsenceId" value="${absence.idEtreAbsent}" />
                            <c:set var="absenceDebut" value="${absence.absenceDebut}" />
                        </c:if>
                    </c:forEach>

                    <c:if test="${isAbsent}">
                        <span class="status-badge status-absent">Absent</span>
                    </c:if>
                    <c:if test="${not isAbsent}">
                        <span class="status-badge status-present">Présent</span>
                    </c:if>
                </div>

                <c:choose>
                    <c:when test="${isAbsent}">
                        <%
                            Object absenceDebutObj = pageContext.getAttribute("absenceDebut");
                            String dateFormatee = "";
                            if (absenceDebutObj instanceof LocalDateTime) {
                                LocalDateTime ldt = (LocalDateTime) absenceDebutObj;
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                dateFormatee = ldt.format(formatter);
                            }
                            pageContext.setAttribute("dateFormatee", dateFormatee);
                        %>
                        <div class="absence-info">
                            <p class="info-label">
                                <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                                    <path fill-rule="evenodd" d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z" clip-rule="evenodd" />
                                </svg>
                                <strong>Date de début :</strong>
                                <span>${dateFormatee}</span>
                            </p>
                            <div class="help-box">
                                <svg class="icon-info" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                                    <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd" />
                                </svg>
                                <div>
                                    <p><strong>Comment clôturer cette absence ?</strong></p>
                                    <p>Téléchargez un certificat médical ou un justificatif d'absence pour terminer la déclaration.</p>
                                    <p class="formats">Formats acceptés : PDF, JPG, PNG (max. 1 Mo)</p>
                                </div>
                            </div>
                        </div>

                        <form method="post" action="CtrlAbsence" enctype="multipart/form-data" class="upload-form">
                            <input type="hidden" name="action" value="upload" />
                            <input type="hidden" name="id_absence" value="${openAbsenceId}" />

                            <div class="file-input-container">
                                <label for="certificat-${enfant.idUtilisateur}" class="file-label">
                                    <svg class="upload-icon" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12" />
                                    </svg>
                                    <span class="file-text">Choisir un fichier</span>
                                    <span class="file-name"></span>
                                </label>
                                <input type="file"
                                       id="certificat-${enfant.idUtilisateur}"
                                       name="certificat"
                                       class="file-input"
                                       accept="application/pdf,image/jpeg,image/jpg,image/png"
                                       required />
                            </div>

                            <button type="submit" class="btn btn-primary">
                                <svg class="btn-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                                    <path d="M8 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM15 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0z" />
                                    <path d="M3 4a1 1 0 00-1 1v10a1 1 0 001 1h1.05a2.5 2.5 0 014.9 0H10a1 1 0 001-1V5a1 1 0 00-1-1H3zM14 7a1 1 0 00-1 1v6.05A2.5 2.5 0 0115.95 16H17a1 1 0 001-1v-5a1 1 0 00-.293-.707l-2-2A1 1 0 0015 7h-1z" />
                                </svg>
                                Envoyer le certificat
                            </button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="absence-info no-absence">
                            <svg class="icon-check" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                                <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
                            </svg>
                            <p>Aucune absence en cours. Vous pouvez déclarer une absence si nécessaire.</p>
                        </div>
                        <form method="post" action="CtrlAbsence">
                            <input type="hidden" name="action" value="declare" />
                            <input type="hidden" name="id_enfant" value="${enfant.idUtilisateur}"/>
                            <button type="submit" class="btn btn-secondary">
                                <svg class="btn-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-12a1 1 0 10-2 0v4a1 1 0 00.293.707l2.828 2.829a1 1 0 101.415-1.415L11 9.586V6z" clip-rule="evenodd" />
                                </svg>
                                Déclarer une absence
                            </button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    document.querySelectorAll('.file-input').forEach(input => {
        input.addEventListener('change', function(e) {
            const fileName = e.target.files[0]?.name || '';
            const fileNameSpan = this.previousElementSibling.querySelector('.file-name');
            const fileText = this.previousElementSibling.querySelector('.file-text');
            if (fileName) {
                fileNameSpan.textContent = fileName;
                fileText.style.display = 'none';
                fileNameSpan.style.display = 'inline';
            } else {
                fileNameSpan.textContent = '';
                fileText.style.display = 'inline';
                fileNameSpan.style.display = 'none';
            }
        });
    });
</script>

</body>
</html>
