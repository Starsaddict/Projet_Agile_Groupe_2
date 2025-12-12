<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Créer Compte Famille</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
  <div class="container">
    <div class="card">
      <h1>Création du compte famille</h1>

      <% String error = request.getParameter("error"); %>
      <% if (error != null && !error.isEmpty()) { %>
          <div class="alert error"><%= error %></div>
      <% } %>

      <form method="post" action="<%=request.getContextPath()%>/secretaire/profil/creerCompteFamile">
          <table class="form-table">
              <tr>
                  <th>Joueur(s)</th>
                  <th>Parent(s)</th>
              </tr>
              <tr>
                  <td id="joueur-col">
                      <div class="form-row joueur-row">
                          <label>Nom :</label>
                          <input type="text" name="joueur_nom[]" required /><br/>
                          <label>Prénom :</label>
                          <input type="text" name="joueur_prenom[]" required /><br/>
                          <label>Email :</label>
                          <input type="email" name="joueur_email[]" required />
                      </div>
                  </td>
                  <td id="parent-col">
                      <div class="form-row parent-row">
                          <label>Nom :</label>
                          <input type="text" name="parent_nom[]" required /><br/>
                          <label>Prénom :</label>
                          <input type="text" name="parent_prenom[]" required /><br/>
                          <label>Email :</label>
                          <input type="email" name="parent_email[]" required />
                      </div>
                  </td>
              </tr>
          </table>

          <div class="action-buttons">
              <button type="button" onclick="addJoueur()" class="btn">+ Ajouter un joueur</button>
              <button type="button" onclick="addParent()" class="btn secondary">+ Ajouter un parent</button>
          </div>

          <div class="action-buttons" style="margin-top: 2rem;">
              <button type="submit" class="btn">Créer la famille</button>
              <button type="button" class="btn secondary" onclick="window.history.back();">Annuler</button>
          </div>
      </form>
    </div>
  </div>

  <script>
      function addJoueur() {
          var col = document.getElementById("joueur-col");
          if (col.querySelectorAll('.joueur-row').length) {
              var separator = document.createElement("hr");
              separator.className = "row-separator";
              col.appendChild(separator);
          }
          var div = document.createElement("div");
          div.className = "form-row joueur-row";
          div.innerHTML =
              '<label>Nom :</label><input type="text" name="joueur_nom[]" required /><br/>' +
              '<label>Prénom :</label><input type="text" name="joueur_prenom[]" required /><br/>' +
              '<label>Email :</label><input type="email" name="joueur_email[]" required />';
          col.appendChild(div);
      }

      function addParent() {
          var col = document.getElementById("parent-col");
          if (col.querySelectorAll('.parent-row').length) {
              var separator = document.createElement("hr");
              separator.className = "row-separator";
              col.appendChild(separator);
          }
          var div = document.createElement("div");
          div.className = "form-row parent-row";
          div.innerHTML =
              '<label>Nom :</label><input type="text" name="parent_nom[]" required /><br/>' +
              '<label>Prénom :</label><input type="text" name="parent_prenom[]" required /><br/>' +
              '<label>Email :</label><input type="email" name="parent_email[]" required />';
          col.appendChild(div);
      }
  </script>
</body>
</html>