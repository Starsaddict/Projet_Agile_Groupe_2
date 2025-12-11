<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Utilisateur, java.util.List" %>
<%
    Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
    String error = (String) request.getAttribute("error");
    String contextPath = request.getContextPath();
    
    if (utilisateur == null) {
        response.sendRedirect(contextPath + "/secretaire/profil?error=Utilisateur non trouvé");
        return;
    }
%>
<html>
<head>
    <title>Modifier le compte</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-container { max-width: 600px; margin: 0 auto; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"],
        input[type="email"],
        input[type="date"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
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
        .role-info {
            padding: 10px;
            background: #e9ecef;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        .role-label {
            font-weight: bold;
            color: #1976d2;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h1>Modifier le compte</h1>
        
        <%
            List<Utilisateur> utilisateursByEmail = (List<Utilisateur>) request.getAttribute("utilisateursByEmail");
            if (utilisateursByEmail != null && !utilisateursByEmail.isEmpty()) {
                StringBuilder roles = new StringBuilder();
                for (int i = 0; i < utilisateursByEmail.size(); i++) {
                    if (i > 0) roles.append(", ");
                    roles.append(utilisateursByEmail.get(i).getRoleLabel());
                }
        %>
        <div class="role-info">
            Rôles: <span class="role-label"><%= roles.toString() %></span>
        </div>
        <%
            }
        %>
        
        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>
        
        <form method="post" action="<%= contextPath %>/secretaire/profil/pageModifier">
            <input type="hidden" name="id" value="<%= utilisateur.getIdUtilisateur() %>">
            
            <div class="form-group">
                <label for="nom">Nom:</label>
                <input type="text" id="nom" name="nom" 
                       value="<%= utilisateur.getNomUtilisateur() != null ? utilisateur.getNomUtilisateur() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label for="prenom">Prénom:</label>
                <input type="text" id="prenom" name="prenom" 
                       value="<%= utilisateur.getPrenomUtilisateur() != null ? utilisateur.getPrenomUtilisateur() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" 
                       value="<%= utilisateur.getEmailUtilisateur() != null ? utilisateur.getEmailUtilisateur() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label for="dateNaissance">Date de naissance:</label>
                <input type="date" id="dateNaissance" name="dateNaissance" 
                       value="<%= utilisateur.getDateNaissanceUtilisateur() != null ? utilisateur.getDateNaissanceUtilisateur() : "" %>">
            </div>
   
            
            
            
            <div class="button-group">
                <button type="submit" class="btn btn-primary">Enregistrer</button>
                <a href="<%= contextPath %>/secretaire/profil/modifier" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</body>
</html>