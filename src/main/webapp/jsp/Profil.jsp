<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Joueur, model.Parent" %>
<%
    // 获取数据
    Joueur joueur = (Joueur) request.getAttribute("joueur");
    Parent parent1 = (Parent) request.getAttribute("parent1");
    Parent parent2 = (Parent) request.getAttribute("parent2");
    Boolean isSecretaire = (Boolean) request.getAttribute("isSecretaire");
    Long currentUserId = (Long) request.getAttribute("currentUserId");
    
    // 获取请求参数
    String success = request.getParameter("success");
    String error = request.getParameter("error");
    
    // 上下文路径
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>Profil</title>
    <style>
        .form-container { margin: 20px; padding: 20px; border: 1px solid #ddd; }
        .parent-section { margin-top: 30px; padding: 15px; background: #f5f5f5; }
        .form-group { margin-bottom: 15px; }
        label { display: inline-block; width: 200px; }
        input[type="text"], input[type="email"], input[type="date"] { width: 300px; }
        .readonly { background-color: #f0f0f0; }
        .success { color: green; padding: 10px; background: #d4edda; }
        .error { color: red; padding: 10px; background: #f8d7da; }
    </style>
</head>
<body>
    <h2>
        <%= (isSecretaire != null && isSecretaire) ? "Modifier le profil" : "Mon profil" %>
    </h2>
    
    <%-- 显示成功消息 --%>
    <% if ("true".equals(success)) { %>
        <div class="success">Modifications enregistrées avec succès!</div>
    <% } %>
    
    <%-- 显示错误消息 --%>
    <% if (error != null) { 
        String errorMsg = "";
        if ("invalidinput".equals(error)) {
            errorMsg = "Données invalides";
        } else if ("updatefailed".equals(error)) {
            errorMsg = "Échec de la mise à jour";
        } else {
            errorMsg = "Erreur inconnue";
        }
    %>
        <div class="error">Erreur: <%= errorMsg %></div>
    <% } %>
    
    <% if (joueur != null) { %>
    <form action="<%= contextPath %>/Profil/<%= joueur.getIdUtilisateur() %>" method="post">
        <!-- Joueur Section -->
        <div class="form-container">
            <h3>Informations du Joueur</h3>
            <input type="hidden" name="idJoueur" value="<%= joueur.getIdUtilisateur() %>">
            
            <div class="form-group">
                <label>Nom:</label>
                <input type="text" name="nomJoueur" value="<%= joueur.getNomUtilisateur() != null ? joueur.getNomUtilisateur() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label>Prénom:</label>
                <input type="text" name="prenomJoueur" value="<%= joueur.getPrenomUtilisateur() != null ? joueur.getPrenomUtilisateur() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="emailJoueur" value="<%= joueur.getEmailUtilisateur() != null ? joueur.getEmailUtilisateur() : "" %>"
                       <% if (isSecretaire == null || !isSecretaire) { %>
                           readonly class="readonly"
                       <% } %>>
            </div>
            
            <div class="form-group">
                <label>Date de naissance:</label>
                <input type="date" name="dateNaissance" 
                       value="<%= joueur.getDateNaissanceUtilisateur() != null ? joueur.getDateNaissanceUtilisateur().toString() : "" %>">
            </div>
        </div>
        
        <!-- Parent 1 Section -->
        <% if (parent1 != null) { 
            boolean canEdit1 = (isSecretaire != null && isSecretaire) || 
                              (currentUserId != null && currentUserId.equals(parent1.getIdUtilisateur()));
        %>
            <div class="form-container parent-section">
                <h3>Parent 1</h3>
                <input type="hidden" name="idParent1" value="<%= parent1.getIdUtilisateur() %>">
                
                <div class="form-group">
                    <label>Nom:</label>
                    <input type="text" name="nomParent1" value="<%= parent1.getNomUtilisateur() != null ? parent1.getNomUtilisateur() : "" %>"
                           <%= canEdit1 ? "" : "readonly class='readonly'" %>>
                </div>
                
                <div class="form-group">
                    <label>Prénom:</label>
                    <input type="text" name="prenomParent1" value="<%= parent1.getPrenomUtilisateur() != null ? parent1.getPrenomUtilisateur() : "" %>"
                           <%= canEdit1 ? "" : "readonly class='readonly'" %>>
                </div>
                
                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="emailParent1" value="<%= parent1.getEmailUtilisateur() != null ? parent1.getEmailUtilisateur() : "" %>"
                           <%= (isSecretaire != null && isSecretaire) ? "" : "readonly class='readonly'" %>>
                </div>
            </div>
        <% } %>
        
        <!-- Parent 2 Section -->
        <% if (parent2 != null) { 
            boolean canEdit2 = (isSecretaire != null && isSecretaire) || 
                              (currentUserId != null && currentUserId.equals(parent2.getIdUtilisateur()));
        %>
            <div class="form-container parent-section">
                <h3>Parent 2</h3>
                <input type="hidden" name="idParent2" value="<%= parent2.getIdUtilisateur() %>">
                
                <div class="form-group">
                    <label>Nom:</label>
                    <input type="text" name="nomParent2" value="<%= parent2.getNomUtilisateur() != null ? parent2.getNomUtilisateur() : "" %>"
                           <%= canEdit2 ? "" : "readonly class='readonly'" %>>
                </div>
                
                <div class="form-group">
                    <label>Prénom:</label>
                    <input type="text" name="prenomParent2" value="<%= parent2.getPrenomUtilisateur() != null ? parent2.getPrenomUtilisateur() : "" %>"
                           <%= canEdit2 ? "" : "readonly class='readonly'" %>>
                </div>
                
                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="emailParent2" value="<%= parent2.getEmailUtilisateur() != null ? parent2.getEmailUtilisateur() : "" %>"
                           <%= (isSecretaire != null && isSecretaire) ? "" : "readonly class='readonly'" %>>
                </div>
            </div>
        <% } %>
        
        <div style="margin-top: 20px;">
            <input type="submit" value="Enregistrer les modifications">
            <button type="button" onclick="window.history.back()">Annuler</button>
        </div>
    </form>
    <% } else { %>
        <p>Joueur non trouvé.</p>
    <% } %>
</body>
</html>