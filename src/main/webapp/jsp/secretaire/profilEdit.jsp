<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Utilisateur" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
    String error = (String) request.getAttribute("error");
    String contextPath = request.getContextPath();
    
    if (utilisateur == null) {
        response.sendRedirect(contextPath + "/secretaire/profil?error=Utilisateur non trouvé");
        return;
    }
    
    // Formater la date pour le formulaire
    String dateNaissanceStr = "";
    if (utilisateur.getDateNaissanceUtilisateur() != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateNaissanceStr = utilisateur.getDateNaissanceUtilisateur().format(formatter);
    }
%>
<html>
<head>
    <title>Modifier le profil - Secrétariat</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-container { max-width: 700px; margin: 0 auto; }
        h1 { color: #1976d2; border-bottom: 2px solid #1976d2; padding-bottom: 10px; }
        .form-group { margin-bottom: 20px; }
        .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"],
        input[type="email"],
        input[type="date"],
        input[type="tel"],
        textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }
        textarea {
            resize: vertical;
            min-height: 150px;
        }
        .alert { padding: 10px 12px; border-radius: 4px; margin: 10px 0; }
        .alert.error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .button-group { margin-top: 20px; }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 10px;
        }
        .btn-primary {
            background: #1976d2;
            color: white;
        }
        .btn-primary:hover { background: #125aa0; }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn-secondary:hover { background: #5a6268; }
        .info-box {
            background: #e3f2fd;
            border-left: 4px solid #1976d2;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .form-section {
            background: #f9f9f9;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .form-section h3 {
            margin-top: 0;
            color: #1976d2;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h1>Modifier le profil - Secrétariat</h1>
        
        <div class="info-box">
            <strong>Utilisateur:</strong>
            <p id="userType">
                <strong><%= utilisateur.getPrenomUtilisateur() %> <%= utilisateur.getNomUtilisateur() %></strong>
                <br/>
                Type: <strong><%= utilisateur.getTypeU() %></strong>
            </p>
        </div>
        
        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>
        
        <form method="post" action="<%= contextPath %>/secretaire/profil/edit">
            <input type="hidden" name="id" value="<%= utilisateur.getIdUtilisateur() %>">
            
            <div class="form-section">
                <h3>Informations personnelles</h3>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nom">Nom:</label>
                        <input type="text" id="nom" name="nom" value="<%= utilisateur.getNomUtilisateur() != null ? utilisateur.getNomUtilisateur() : "" %>">
                    </div>
                    <div class="form-group">
                        <label for="prenom">Prénom:</label>
                        <input type="text" id="prenom" name="prenom" value="<%= utilisateur.getPrenomUtilisateur() != null ? utilisateur.getPrenomUtilisateur() : "" %>">
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" value="<%= utilisateur.getEmailUtilisateur() != null ? utilisateur.getEmailUtilisateur() : "" %>">
                    </div>
                    <div class="form-group">
                        <label for="dateNaissance">Date de naissance:</label>
                        <input type="date" id="dateNaissance" name="dateNaissance" value="<%= dateNaissanceStr %>">
                    </div>
                </div>
            </div>
            
            <div class="form-section">
                <h3>Profil / Description</h3>
                
                <div class="form-group">
                    <label for="description">Profil:</label>
                    <textarea id="description" name="description" placeholder="Description ou notes..."><%= utilisateur.getDescription() != null ? utilisateur.getDescription() : "" %></textarea>
                </div>
            </div>
            
            <div class="button-group">
                <button type="submit" class="btn btn-primary">Enregistrer les modifications</button>
                <a href="<%= contextPath %>/secretaire/profil" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</body>
</html>
