<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, model.Utilisateur" %>
<%
    List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
    String contextPath = request.getContextPath();
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Modifier un compte</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
  <div class="container">
    <div class="card">
      <h1>Gestion des comptes</h1>

      <% if (error != null) { %>
          <div class="alert error"><%= error %></div>
      <% } %>

      <% if (success != null) { %>
          <div class="alert success"><%= success %></div>
      <% } %>

      <table class="table">
          <thead>
          <tr>
              <th>Nom</th>
              <th>Prénom</th>
              <th>Email</th>
              <th>Actions</th>
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
                  <td data-label="Nom"><%= nom %></td>
                  <td data-label="Prénom"><%= prenom %></td>
                  <td data-label="Email"><%= email %></td>
                  <td data-label="Actions" class="actions">
                      <form method="post" action="<%= contextPath %>/secretaire/profil/resetMdp">
                          <input type="hidden" name="idUtilisateur" value="<%= utilisateur.getIdUtilisateur() %>"/>
                          <button type="submit" class="btn secondary">Réinitialiser MDP</button>
                      </form>
                      <a class="btn" href="<%= contextPath %>/secretaire/profil/modifier?id=<%= utilisateur.getIdUtilisateur() %>">Modifier</a>
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
    </div>
  </div>
</body>
</html>