<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String uploadError = (String) request.getAttribute("uploadError");
%>
<html>
<head>
    <title>Import en masse (Excel)</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="top-bar">
        <div>
            <h1>Import en masse (Excel)</h1>
            <p>Importez vos comptes via un fichier Excel (.xls ou .xlsx)</p>
        </div>
        <div class="links">
            <a href="<%= contextPath %>/secretaire/profil" class="btn btn-secondary">← Retour au profil</a>
        </div>
    </div>

    <% if (uploadError != null) { %>
        <div class="alert error"><%= uploadError %></div>
    <% } %>

    <div class="card help-box" style="margin-bottom: 1.5rem;">
        <div class="info-label">
            <svg class="icon icon-info" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            <strong>Instructions d'import</strong>
        </div>
        <p>Assurez-vous que votre fichier Excel respecte le modèle attendu avant de lancer l'import.</p>
        <p class="formats">Formats acceptés : .xls ou .xlsx</p>
    </div>

    <div class="card">
        <div class="upload-header" style="margin-bottom: 1.5rem;">
            <div>
                <h2>Uploader un fichier Excel</h2>
                <p class="text-muted" style="color: var(--text-muted); margin-top: 0.5rem;">
                    Sélectionnez votre fichier Excel contenant les comptes à importer
                </p>
            </div>
        </div>
        
        <form action="<%= contextPath %>/secretaire/profil/batchCreate" method="post" enctype="multipart/form-data" class="upload-form">
            <div class="field">
                <label for="excelFile">Fichier Excel</label>
                <div class="file-input-container">
                    <input type="file" id="excelFile" name="excelFile" accept=".xls,.xlsx" required class="file-input" />
                    <div class="file-label">
                        <svg class="upload-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path>
                        </svg>
                        <span>Cliquez pour sélectionner un fichier Excel</span>
                        <span class="file-name"></span>
                    </div>
                </div>
            </div>
            
            <div class="form-actions" style="display: flex; justify-content: flex-end; margin-top: 2rem;">
                <button type="submit" class="btn btn-primary">
                    <svg class="btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-8l-4-4m0 0L8 8m4-4v12"></path>
                    </svg>
                    Uploader et Importer
                </button>
            </div>
        </form>
    </div>
</div>

<script>
// JavaScript pour afficher le nom du fichier sélectionné
document.getElementById('excelFile').addEventListener('change', function(e) {
    const fileName = this.files[0]?.name;
    const fileNameElement = document.querySelector('.file-name');
    const fileLabel = document.querySelector('.file-label span:not(.file-name)');
    
    if (fileName) {
        fileNameElement.textContent = fileName;
        fileNameElement.style.display = 'inline';
        fileLabel.textContent = 'Fichier sélectionné : ';
    } else {
        fileNameElement.style.display = 'none';
        fileLabel.textContent = 'Cliquez pour sélectionner un fichier Excel';
    }
});
</script>

</body>
</html>