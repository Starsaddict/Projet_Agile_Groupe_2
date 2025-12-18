<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.Joueur" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
    String error = (String) request.getAttribute("error");
    String contextPath = request.getContextPath();
    boolean isJoueur = utilisateur instanceof Joueur;
    String profilePicRoute = isJoueur ? ((Joueur) utilisateur).getProfilePicRoute() : null;
    String resolvedProfilePic = profilePicRoute;
    if ((resolvedProfilePic == null || resolvedProfilePic.isEmpty()) && isJoueur) {
        resolvedProfilePic = "/img/joueur_avatar/default.png";
    }
    if (resolvedProfilePic != null && !resolvedProfilePic.isEmpty()) {
        if (resolvedProfilePic.startsWith("src/main/resources/pic_joueur/")) {
            resolvedProfilePic = "/img/joueur_avatar/" + new java.io.File(resolvedProfilePic).getName();
        } else if (resolvedProfilePic.startsWith("/pic_joueur/")) {
            // legacy value in DB
            resolvedProfilePic = resolvedProfilePic.replaceFirst("/pic_joueur/", "/img/joueur_avatar/");
        }
        if (resolvedProfilePic.startsWith("/")) {
            resolvedProfilePic = contextPath + resolvedProfilePic;
        } else if (!resolvedProfilePic.startsWith("http")) {
            resolvedProfilePic = contextPath + "/" + resolvedProfilePic;
        }
    }
    String numeroJoueur = isJoueur ? ((Joueur) utilisateur).getNumeroJoueur() : null;
    
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
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
    <%@ include file="/jsp/header.jspf" %>
    <div class="container">
        <div class="card">
            <h1>Modifier le profil - Secrétariat</h1>
            
            <div class="alert" style="background:#e3f2fd; border-left:4px solid var(--primary); color: var(--text);">
                <strong>Utilisateur:</strong><br>
                <strong><%= utilisateur.getPrenomUtilisateur() %> <%= utilisateur.getNomUtilisateur() %></strong><br>
                Type: <strong><%= utilisateur.getTypeU() %></strong>
            </div>
            
            <% if (error != null) { %>
                <div class="alert error"><%= error %></div>
            <% } %>
            
            <form method="post" action="<%= contextPath %>/secretaire/profil/edit" enctype="multipart/form-data">
                <input type="hidden" name="id" value="<%= utilisateur.getIdUtilisateur() %>">
                
                <% if (isJoueur) { %>
                <div class="form-section" style="background:#f9f9f9; padding:15px; border-radius:8px; margin-bottom:20px;">
                    <h2 style="margin-top:0;">Avatar du joueur</h2>
                    <% if (resolvedProfilePic != null && !resolvedProfilePic.isEmpty()) {
                           String altText = "Avatar joueur " + (numeroJoueur != null ? numeroJoueur : "");
                    %>
                        <div style="margin-bottom:10px;">
                            <img src="<%= resolvedProfilePic %>" alt="<%= altText %>" style="max-width:160px; border-radius:8px; border:1px solid #ddd;">
                        </div>
                    <% } %>
                    <div class="field">
                        <label for="profilePic">Mettre à jour l'avatar</label>
                        <input type="file" id="profilePic" name="profilePic" accept="image/*">
                    </div>
                </div>
                <% } %>
                
                <div class="form-section" style="background:#f9f9f9; padding:15px; border-radius:8px; margin-bottom:20px;">
                    <h2 style="margin-top:0;">Informations personnelles</h2>
                    
                    <div class="form-row">
                        <div class="field" style="margin-right:0;">
                            <label for="nom">Nom</label>
                            <input type="text" id="nom" name="nom" value="<%= utilisateur.getNomUtilisateur() != null ? utilisateur.getNomUtilisateur() : "" %>">
                        </div>
                        <div class="field">
                            <label for="prenom">Prénom</label>
                            <input type="text" id="prenom" name="prenom" value="<%= utilisateur.getPrenomUtilisateur() != null ? utilisateur.getPrenomUtilisateur() : "" %>">
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="field" style="margin-right:0;">
                            <label>Email (non modifiable)</label>
                            <input type="email" value="<%= utilisateur.getEmailUtilisateur() != null ? utilisateur.getEmailUtilisateur() : "" %>" disabled>
                        </div>
                        <div class="field">
                            <label for="dateNaissance">Date de naissance</label>
                            <input type="date" id="dateNaissance" name="dateNaissance" value="<%= dateNaissanceStr %>">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="field" style="margin-right:0;">
                            <label for="adresse">Adresse</label>
                            <input type="text" id="adresse" name="adresse" value="<%= utilisateur.getAdresseUtilisateur() != null ? utilisateur.getAdresseUtilisateur() : "" %>">
                        </div>
                        <div class="field">
                            <label for="telephone">Téléphone</label>
                            <input type="tel" id="telephone" name="telephone" value="<%= utilisateur.getTelephoneUtilisateur() != null ? utilisateur.getTelephoneUtilisateur() : "" %>">
                        </div>
                    </div>
                </div>
                
                <div class="action-buttons">
                    <button type="submit" class="btn">Enregistrer les modifications</button>
                    <a href="<%= contextPath %>/secretaire/profil" class="btn secondary">Annuler</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
