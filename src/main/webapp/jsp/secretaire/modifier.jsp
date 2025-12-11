<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Utilisateur, java.util.LinkedHashMap, java.util.Map" %>
<%
    List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
    String contextPath = request.getContextPath();
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
    
    // Grouper par email - garder l'ordre et éviter les doublons
    Map<String, Utilisateur> emailGroupMap = new LinkedHashMap<>();
    if (utilisateurs != null && !utilisateurs.isEmpty()) {
        for (Utilisateur u : utilisateurs) {
            String email = u.getEmailUtilisateur() != null ? u.getEmailUtilisateur() : "";
            // Garder seulement le premier utilisateur pour chaque email
            if (!emailGroupMap.containsKey(email)) {
                emailGroupMap.put(email, u);
            }
        }
    }
%>
<html>
<head>
    <title>Modifier un compte</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 100%; margin-top: 15px; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }
        th { background-color: #f3f3f3; }
        .actions a { display: inline-block; padding: 6px 10px; background: #1976d2; color: #fff; text-decoration: none; border-radius: 4px; }
        .actions a:hover { background: #125aa0; }
        .alert { padding: 10px 12px; border-radius: 4px; margin: 10px 0; }
        .alert.success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .empty { text-align: center; color: #666; }
    </style>
</head>
<body>
<h1>Liste des comptes</h1>

<% if (error != null) { %>
    <div class="alert" style="background:#f8d7da;color:#721c24;border:1px solid #f5c6cb;"><%= error %></div>
<% } %>

<% if ("1".equals(success)) { %>
    <div class="alert success">Compte modifié avec succès.</div>
<% } %>

<table>
    <thead>
    <tr>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Email</th>
        <th>Role</th>
        <th>Action</th>
        
    </tr>
    </thead>
    <tbody>
    <% if (emailGroupMap != null && !emailGroupMap.isEmpty()) {
           for (Map.Entry<String, Utilisateur> entry : emailGroupMap.entrySet()) {
               Utilisateur utilisateur = entry.getValue();
               String nom = utilisateur.getNomUtilisateur() != null ? utilisateur.getNomUtilisateur() : "";
               String prenom = utilisateur.getPrenomUtilisateur() != null ? utilisateur.getPrenomUtilisateur() : "";
               String email = utilisateur.getEmailUtilisateur() != null ? utilisateur.getEmailUtilisateur() : "";
               String role = utilisateur.getRoleLabel() != null ? utilisateur.getRoleLabel() : "";
    %>
        <tr>
            <td><%= nom %></td>
            <td><%= prenom %></td>
            <td><%= email %></td>
            <td><%= role %></td>
            <td class="actions">
                <a href="<%= contextPath %>/secretaire/profil/pageModifier?id=<%= utilisateur.getIdUtilisateur() %>">Modifier</a>
            </td>
        </tr>
    <%   }
       } else { %>
        <tr>
            <td colspan="5" class="empty">Aucun compte à afficher.</td>
        </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
