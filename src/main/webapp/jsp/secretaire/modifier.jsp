<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Utilisateur, java.util.LinkedHashMap, java.util.Map" %>
<%
    List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
    String contextPath = request.getContextPath();
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
    
    // Grouper par email - garder seulement le premier utilisateur pour chaque email
    // mais conserver tous les comptes sans email (joueur peut en être dépourvu)
    Map<String, Utilisateur> emailGroupMap = new LinkedHashMap<>();
    if (utilisateurs != null && !utilisateurs.isEmpty()) {
        for (Utilisateur u : utilisateurs) {
            String email = u.getEmailUtilisateur() != null ? u.getEmailUtilisateur().trim() : "";
            String key = (!email.isEmpty()) ? email : "NO_EMAIL_" + u.getIdUtilisateur();
            if (!emailGroupMap.containsKey(key)) {
                emailGroupMap.put(key, u);
            }
        }
    }
%>
<html>
<head>
    <title>Modifier un compte</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <div class="action-buttons" style="justify-content: space-between; align-items: center;">
            <h1 style="margin:0;">Gestion des utilisateurs</h1>
            <a href="<%= contextPath %>/secretaire/profil" class="btn secondary">← Retour au menu</a>
        </div>

        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>

        <% if ("1".equals(success)) { %>
            <div class="alert success">Compte modifié avec succès.</div>
        <% } %>

        <% if (utilisateurs != null && !utilisateurs.isEmpty() && !emailGroupMap.isEmpty()) { %>
            <table class="table">
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
                            <td data-label="Nom"><%= nom %></td>
                            <td data-label="Prénom"><%= prenom %></td>
                            <td data-label="Email"><%= email %></td>
                            <td data-label="Type"><%= typeU %></td>

                            <td class="actions" data-label="Action">
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
</div>
</body>
</html>
