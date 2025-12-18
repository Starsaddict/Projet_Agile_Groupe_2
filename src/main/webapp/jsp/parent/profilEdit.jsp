<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
    <%@ include file="/jsp/header.jspf" %>
    <div class="container">
        <div class="card">
            <h1>Créer/Modifier mon profil</h1>
            
            <div class="alert" style="background:#e3f2fd; border-left:4px solid var(--primary); color: var(--text);">
                <strong>Informations personnelles:</strong><br>
                <strong><%= utilisateur.getPrenomUtilisateur() %> <%= utilisateur.getNomUtilisateur() %></strong><br>
                Email: <%= utilisateur.getEmailUtilisateur() %>
            </div>
            
            <% if (error != null) { %>
                <div class="alert error"><%= error %></div>
            <% } %>
            
            <form method="post" action="<%= contextPath %>/parent/profil/edit">
                <input type="hidden" name="id" value="<%= utilisateur.getIdUtilisateur() %>">

                <div class="field">
                    <label for="adresse">Adresse</label>
                    <input type="text" id="adresse" name="adresse" value="<%= utilisateur.getAdresseUtilisateur() != null ? utilisateur.getAdresseUtilisateur() : "" %>">
                </div>

                <div class="field">
                    <label for="telephone">Téléphone</label>
                    <input type="text" id="telephone" name="telephone" value="<%= utilisateur.getTelephoneUtilisateur() != null ? utilisateur.getTelephoneUtilisateur() : "" %>">
                </div>
                
                <div class="action-buttons">
                    <button type="submit" class="btn">Enregistrer mon profil</button>
                    <a href="<%= contextPath %>/parent/profil" class="btn secondary">Annuler</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
