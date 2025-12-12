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
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 1000px; margin: 0 auto; }
        h1 { color: #1976d2; border-bottom: 2px solid #1976d2; padding-bottom: 10px; }
        .alert { padding: 10px 12px; border-radius: 4px; margin: 10px 0; }
        .alert.success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert.error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .back-link { margin-bottom: 20px; }
        .back-link a { color: #1976d2; text-decoration: none; }
        .back-link a:hover { text-decoration: underline; }
        .profile-table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .profile-table th {
            background: #1976d2;
            color: white;
            padding: 12px;
            text-align: left;
            font-weight: bold;
        }
        .profile-table td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }
        .profile-table tr:hover {
            background: #f9f9f9;
        }
        .actions {
            text-align: center;
        }
        .btn {
            display: inline-block;
            padding: 6px 12px;
            margin: 2px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            font-size: 13px;
            text-decoration: none;
            color: white;
            background: #1976d2;
        }
        .btn:hover {
            background: #125aa0;
        }
        .empty {
            text-align: center;
            padding: 30px;
            color: #666;
        }
    </style>
</head>
<body>
<div class="container">
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
