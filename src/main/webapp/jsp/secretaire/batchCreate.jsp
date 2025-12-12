<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String uploadError = (String) request.getAttribute("uploadError");
%>
<html>
<head>
    <title>Import en masse (Excel)</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/secretaire-profil.css">
</head>
<body>
<div class="container">
    <h1>Import en masse (Excel)</h1>

    <% if (uploadError != null) { %>
        <div class="alert error"><%= uploadError %></div>
    <% } %>

    <div class="info-box">
        <strong>Importez vos comptes via un fichier Excel (.xls ou .xlsx).</strong><br/>
        Vérifiez que le modèle est respecté avant de lancer l'import.
    </div>

    <div class="menu-section">
        <div class="form-card upload-card">
            <div class="upload-header">
                <div>
                    <h2>Uploader un fichier</h2>
                    <p class="helper-text">Formats acceptés : .xls ou .xlsx. Assurez-vous d'utiliser le modèle attendu.</p>
                </div>
            </div>
            <form action="<%= contextPath %>/secretaire/profil/batchCreate" method="post" enctype="multipart/form-data" class="upload-form">
                <div class="field">
                    <label for="excelFile">Choisir un fichier Excel</label>
                    <input type="file" id="excelFile" name="excelFile" accept=".xls,.xlsx" required class="file-input" />
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn">Uploader</button>
                </div>
            </form>
        </div>
    </div>

    <div class="menu-section">
        <div class="menu">
            <ul>
                <li>
                    <a href="<%= contextPath %>/secretaire/profil">Retour au profil</a>
                    <p class="description">Revenir à la gestion du secrétariat</p>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
