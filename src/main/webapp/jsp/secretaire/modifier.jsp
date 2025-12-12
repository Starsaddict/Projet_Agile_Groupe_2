<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Utilisateur, java.util.LinkedHashMap, java.util.Map" %>
<%
    List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
    String contextPath = request.getContextPath();
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
    
    // Grouper par email - garder seulement le premier utilisateur pour chaque email
    Map<String, Utilisateur> emailGroupMap = new LinkedHashMap<>();
    if (utilisateurs != null && !utilisateurs.isEmpty()) {
        for (Utilisateur u : utilisateurs) {
            String email = u.getEmailUtilisateur() != null ? u.getEmailUtilisateur() : "";
            if (!emailGroupMap.containsKey(email)) {
                emailGroupMap.put(email, u);
            }
        }
    }
%>
<html>
<head>
    <title>Modifier un compte</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/secretaire-profil.css">
</head>
<body>
<div class="container container-wide">
    <h1>Gestion des utilisateurs</h1>
    
    <div class="back-link">
        <a href="<%= contextPath %>/secretaire/profil">← Retour au menu</a>
    </div>

    <% if (error != null) { %>
        <div class="alert alert-error"><%= error %></div>
    <% } %>

    <% if ("1".equals(success)) { %>
        <div class="alert alert-success">Compte modifié avec succès.</div>
    <% } %>

    <% if (utilisateurs != null && !utilisateurs.isEmpty() && !emailGroupMap.isEmpty()) { %>
        <table class="profile-table">
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Email</th>
                    <th>Type</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% for (Map.Entry<String, Utilisateur> entry : emailGroupMap.entrySet()) {
                    Utilisateur utilisateur = entry.getValue();
                    String nom = utilisateur.getNomUtilisateur() != null ? utilisateur.getNomUtilisateur() : "-";
                    String prenom = utilisateur.getPrenomUtilisateur() != null ? utilisateur.getPrenomUtilisateur() : "-";
                    String email = utilisateur.getEmailUtilisateur() != null ? utilisateur.getEmailUtilisateur() : "-";
                    String typeU = utilisateur.getTypeU() != null ? utilisateur.getTypeU() : "-";
                    Long userId = utilisateur.getIdUtilisateur();
                    System.out.println("DEBUG modifier.jsp: userId=" + userId + ", nom=" + nom + ", email=" + email);
                %>
                    <tr>
                        <td><%= nom %></td>
                        <td><%= prenom %></td>
                        <td><%= email %></td>
                        <td><%= typeU %></td>

                        <td class="actions">
                            <a href="<%= contextPath %>/secretaire/profil/sendResetPasswordRequest?id=<%= userId %>" class="btn">Send Modification Request</a>
                            <a href="<%= contextPath %>/secretaire/profil/edit?id=<%= userId %>" class="btn">Modifier</a>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } else { %>
        <div class="empty">Aucun utilisateur à afficher.</div>
    <% } %>
</div>
</body>
</html>
