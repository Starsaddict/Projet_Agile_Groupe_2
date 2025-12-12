<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Utilisateur" %>
<%
    List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
    String contextPath = request.getContextPath();
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
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
        .actions form { display: inline-block; margin-right: 6px; }
        .actions button { padding: 6px 10px; background: #d32f2f; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        .actions button:hover { background: #9a2323; }
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

<% if (success != null) { %>
    <div class="alert success"><%= success %></div>
<% } %>

<table>
    <thead>
    <tr>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Email</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <% if (utilisateurs != null && !utilisateurs.isEmpty()) {
           for (Utilisateur utilisateur : utilisateurs) {
               String nom = utilisateur.getNomUtilisateur() != null ? utilisateur.getNomUtilisateur() : "";
               String prenom = utilisateur.getPrenomUtilisateur() != null ? utilisateur.getPrenomUtilisateur() : "";
               String email = utilisateur.getEmailUtilisateur() != null ? utilisateur.getEmailUtilisateur() : "";
    %>
        <tr>
            <td><%= nom %></td>
            <td><%= prenom %></td>
            <td><%= email %></td>
            <td class="actions">
                <form method="get" action="<%= contextPath %>/secretaire/profil/resetMdp">
                    <input type="hidden" name="idUtilisateur" value="<%= utilisateur.getIdUtilisateur() %>"/>
                    <button type="submit">reset password</button>
                </form>
                <a href="<%= contextPath %>/secretaire/profil/sendResetPasswordRequest?id=<%= utilisateur.getIdUtilisateur() %>">send Reset Request</a>
                <a href="<%= contextPath %>/secretaire/profil/modifier?id=<%= utilisateur.getIdUtilisateur() %>">Modifier</a>
            </td>
        </tr>
    <%   }
       } else { %>
        <tr>
            <td colspan="4" class="empty">Aucun compte à afficher.</td>
        </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
