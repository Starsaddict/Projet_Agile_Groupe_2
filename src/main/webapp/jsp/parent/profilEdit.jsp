[%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Utilisateur" %>
<%
    Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
    String error = (String) request.getAttribute("error");
    String contextPath = request.getContextPath();
    
    if (utilisateur == null) {
        response.sendRedirect(contextPath + "/parent/profil?error=Utilisateur non trouvé");
        return;
    }
%>
<html>
<head>
    <title>Créer/Modifier mon profil - Parent</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-container { max-width: 700px; margin: 0 auto; }
        h1 { color: #1976d2; border-bottom: 2px solid #1976d2; padding-bottom: 10px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"],
        input[type="email"],
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
            min-height: 300px;
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
    </style>
</head>
<body>
    <div class="form-container">
        <h1>Créer/Modifier mon profil</h1>
        
        <div class="info-box">
            <strong>Informations personnelles:</strong>
            <p>
                <strong><%= utilisateur.getPrenomUtilisateur() %> <%= utilisateur.getNomUtilisateur() %></strong>
                <br/>
                Email: <%= utilisateur.getEmailUtilisateur() %>
            </p>
        </div>
        
        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>
        
        <form method="post" action="<%= contextPath %>/parent/profil/edit">
            <input type="hidden" name="id" value="<%= utilisateur.getIdUtilisateur() %>">
            
            <div class="form-group">
                <label for="description">Mon profil:</label>
                <textarea id="description" name="description" required placeholder="Décrivez-vous, votre expérience, vos attentes..."><%= utilisateur.getDescription() != null ? utilisateur.getDescription() : "" %></textarea>
                <small style="color: #666;">Cet espace est pour créer votre profil en tant que parent. Décrivez votre situation, vos attentes et toute information pertinente.</small>
            </div>
            
            <div class="button-group">
                <button type="submit" class="btn btn-primary">Enregistrer mon profil</button>
                <a href="<%= contextPath %>/parent/profil" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</body>
</html>
