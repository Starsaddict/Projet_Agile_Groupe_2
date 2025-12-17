<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
            <%-- Récupération de l'absence active --%>
            <c:set var="activeAbsence" value="${null}" />
            <c:forEach var="absence" items="${enfant.absences}">
                <%
                    model.EtreAbsent absence = (model.EtreAbsent) pageContext.getAttribute("absence");
                    if (absence.isActive()) {
                        pageContext.setAttribute("activeAbsence", absence);
                    }
                %>
            </c:forEach>

            <div class="absence-card">
                <div class="absence-header">
                    <h3>${enfant.prenomUtilisateur} ${enfant.nomUtilisateur}</h3>
                    <span class="status-badge ${activeAbsence != null ? 'status-absent' : 'status-present'}">
                        ${activeAbsence != null ? 'Absent' : 'Présent'}
                    </span>
                </div>

                <c:choose>
                    <c:when test="${activeAbsence != null}">
                        <div class="absence-info">
                            <div class="info-label">
                                <svg class="icon-info" fill="currentColor" viewBox="0 0 20 20">
                                    <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"/>
                                </svg>
                                <strong>Absence en cours depuis le ${activeAbsence.absenceDebut.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}</strong>
                            </div>

                            <c:if test="${activeAbsence.typeAbsence == 'COURTE' && activeAbsence.absenceFin != null}">
                                <p>Date de fin prévue : ${activeAbsence.absenceFin.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}</p>
                            </c:if>

                            <c:if test="${not empty activeAbsence.motif}">
                                <div class="motif-box">
                                    <strong>Motif :</strong> ${activeAbsence.motif}
                                </div>
                            </c:if>

                            <%-- Absence longue avec ou sans certificat --%>
                            <c:if test="${activeAbsence.typeAbsence == 'LONGUE'}">
                                <%
                                    model.EtreAbsent activeAbs = (model.EtreAbsent) pageContext.getAttribute("activeAbsence");
                                    pageContext.setAttribute("hasCertificat", activeAbs.hasCertificat());
                                %>
                                <c:choose>
                                    <c:when test="${hasCertificat}">
                                        <div class="certificat-info">
                                            <div>
                                                <p><strong>Certificat médical déposé</strong></p>
                                                <p>L'enfant est absent jusqu'au ${activeAbsence.absenceFin.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}</p>
                                                <a href="DownloadCertificat?id=${activeAbsence.idEtreAbsent}"
                                                               class="btn btn-secondary btn-download"
                                                               download>
                                                                <svg class="btn-icon" fill="currentColor" viewBox="0 0 20 20">
                                                                    <path fill-rule="evenodd" d="M3 17a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zm3.293-7.707a1 1 0 011.414 0L9 10.586V3a1 1 0 112 0v7.586l1.293-1.293a1 1 0 111.414 1.414l-3 3a1 1 0 01-1.414 0l-3-3a1 1 0 010-1.414z" clip-rule="evenodd"/>
                                                                </svg>
                                                                Télécharger le certificat
                                                            </a>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Cette absence nécessite un certificat médical pour être clôturée.</p>
                                        <form method="post" action="CtrlAbsence" enctype="multipart/form-data" class="upload-form">
                                            <input type="hidden" name="action" value="upload" />
                                            <input type="hidden" name="id_absence" value="${activeAbsence.idEtreAbsent}" />

                                            <div class="field">
                                                <label for="date_fin_${enfant.idUtilisateur}">Date de fin d'absence</label>
                                                <input type="date" id="date_fin_${enfant.idUtilisateur}" name="date_fin_certificat" required />
                                            </div>

                                            <div class="help-box">
                                                <svg class="icon-info" fill="currentColor" viewBox="0 0 20 20">
                                                    <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"/>
                                                </svg>
                                                <div>
                                                    <strong>Informations</strong>
                                                    <p class="formats">Formats acceptés : PDF, JPG, PNG (5 Mo max)</p>
                                                </div>
                                            </div>

                                            <div class="file-input-container">
                                                <label for="certificat_${enfant.idUtilisateur}" class="file-label">
                                                    <svg class="upload-icon" fill="currentColor" viewBox="0 0 20 20">
                                                        <path d="M5.5 13a3.5 3.5 0 01-.369-6.98 4 4 0 117.753-1.977A4.5 4.5 0 1113.5 13H11V9.413l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 001.414 1.414L9 9.414V13H5.5z"/>
                                                        <path d="M9 13h2v5a1 1 0 11-2 0v-5z"/>
                                                    </svg>
                                                    <span class="file-text">Cliquez pour sélectionner un fichier</span>
                                                    <span class="file-name"></span>
                                                </label>
                                                <input type="file" id="certificat_${enfant.idUtilisateur}" name="certificat" class="file-input" accept=".pdf,.jpg,.jpeg,.png" required />
                                            </div>

                                            <button type="submit" class="btn btn-primary">
                                                <svg class="btn-icon" fill="currentColor" viewBox="0 0 20 20">
                                                    <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                                                </svg>
                                                Soumettre le certificat
                                            </button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="absence-info no-absence">
                            <svg class="icon-check" fill="currentColor" viewBox="0 0 20 20">
                                <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
                            </svg>
                            <p><strong>Aucune absence en cours</strong></p>
                        </div>

                        <div class="btn-group">
                            <button class="btn btn-primary" onclick="openModal('COURTE', ${enfant.idUtilisateur})">
                                <svg class="btn-icon" fill="currentColor" viewBox="0 0 20 20">
                                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-11a1 1 0 10-2 0v2H7a1 1 0 100 2h2v2a1 1 0 102 0v-2h2a1 1 0 100-2h-2V7z" clip-rule="evenodd"/>
                                </svg>
                                Déclarer une absence courte
                            </button>
                            <button class="btn btn-secondary" onclick="openModal('LONGUE', ${enfant.idUtilisateur})">
                                <svg class="btn-icon" fill="currentColor" viewBox="0 0 20 20">
                                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-11a1 1 0 10-2 0v2H7a1 1 0 100 2h2v2a1 1 0 102 0v-2h2a1 1 0 100-2h-2V7z" clip-rule="evenodd"/>
                                </svg>
                                Déclarer une absence longue
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </div>
</div>

<%-- Modal unique pour les deux types d'absences --%>
<div id="absenceModal" class="modal">
    <div class="modal-content">
        <span class="modal-close" onclick="closeModal()">&times;</span>
        <h3 id="modalTitle"></h3>
        <p id="modalInfo" class="modal-info"></p>

        <form method="post" action="CtrlAbsence" id="absenceForm">
            <input type="hidden" name="action" value="declare" />
            <input type="hidden" name="id_enfant" id="modal_id_enfant"/>
            <input type="hidden" name="type_absence" id="modal_type_absence"/>

            <div class="field">
                <label for="date_debut">Date de début</label>
                <input type="date" id="date_debut" name="date_debut" required />
            </div>

            <div class="field" id="date_fin_field">
                <label for="date_fin">Date de fin</label>
                <input type="date" id="date_fin" name="date_fin" />
            </div>

            <div class="field">
                <label for="motif">Motif (optionnel)</label>
                <textarea id="motif" name="motif" rows="3" maxlength="500" placeholder=""></textarea>
            </div>

            <div class="btn-group-modal">
                <button type="submit" class="btn btn-primary" id="submitBtn">Confirmer</button>
                <button type="button" class="btn btn-secondary" onclick="closeModal()">Annuler</button>
            </div>
        </form>
    </div>
</div>

<script>
const modalConfig = {
    COURTE: {
        title: 'Déclarer une absence courte',
        info: '',
        showDateFin: true,
        dateFin Required: true,
        motifPlaceholder: 'Raison de l\'absence...',
        submitText: 'Confirmer l\'absence'
    },
    LONGUE: {
        title: 'Déclarer une absence longue (blessure)',
        info: 'Cette absence nécessitera un certificat médical pour être clôturée. Vous pourrez indiquer la date de fin lors du dépôt du certificat.',
        showDateFin: false,
        dateFinRequired: false,
        motifPlaceholder: 'Description de la blessure...',
        submitText: 'Confirmer la blessure'
    }
};

function openModal(type, idEnfant) {
    const config = modalConfig[type];
    const modal = document.getElementById('absenceModal');
    const dateFinField = document.getElementById('date_fin_field');
    const dateFin = document.getElementById('date_fin');

    document.getElementById('modal_id_enfant').value = idEnfant;
    document.getElementById('modal_type_absence').value = type;
    document.getElementById('modalTitle').textContent = config.title;
    document.getElementById('modalInfo').textContent = config.info;
    document.getElementById('modalInfo').style.display = config.info ? 'block' : 'none';
    document.getElementById('motif').placeholder = config.motifPlaceholder;
    document.getElementById('submitBtn').textContent = config.submitText;

    dateFinField.style.display = config.showDateFin ? 'block' : 'none';
    dateFin.required = config.dateFinRequired;

    const today = new Date().toISOString().split('T')[0];
    document.getElementById('date_debut').value = today;
    document.getElementById('date_debut').min = today;
    dateFin.value = '';
    dateFin.min = today;

    modal.style.display = 'flex';
    document.body.classList.add('modal-open');
}

function closeModal() {
    document.getElementById('absenceModal').style.display = 'none';
    document.body.classList.remove('modal-open');
    document.getElementById('absenceForm').reset();
}

document.getElementById('date_debut').addEventListener('change', function() {
    document.getElementById('date_fin').min = this.value;
});

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

window.onclick = function(event) {
    const modal = document.getElementById('absenceModal');
    if (event.target === modal) closeModal();
};

document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') closeModal();
});
</script>

</body>
</html>