<%--
  Created by IntelliJ IDEA.
  User: kaiyangzhang
  Date: 2025/12/10
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creer Compte</title>
</head>
<body>

    <h1>Création du compte famille</h1>

    <form method="post" action="/secretaire/creerCompteFamile">

        <table border="1" cellpadding="10" cellspacing="0">
            <tr>
                <th>Joueur</th>
                <th>Parent</th>
            </tr>

            <tr>
                <td id="joueur-col">
                    <div class="joueur-row">
                        Nom: <input type="text" name="joueur_nom[]" /><br/>
                        Prénom: <input type="text" name="joueur_prenom[]" /><br/>
                        Email: <input type="email" name="joueur_email[]" /><br/>
                    </div>
                </td>

                <td id="parent-col">
                    <div class="parent-row">
                        Nom: <input type="text" name="parent_nom[]" /><br/>
                        Prénom: <input type="text" name="parent_prenom[]" /><br/>
                        Email: <input type="email" name="parent_email[]" /><br/>
                    </div>
                </td>
            </tr>
        </table>

        <br/>

        <button type="button" onclick="addJoueur()">Ajouter un joueur</button>
        <button type="button" onclick="addParent()">Ajouter un parent</button>

        <br/><br/>

        <input type="submit" value="Submit" />
        <input type="button" value="Cancel" onclick="window.history.back();" />

    </form>

    <script>
        function addJoueur() {
            var col = document.getElementById("joueur-col");
            var div = document.createElement("div");
            div.className = "joueur-row";
            div.innerHTML =
                'Nom: <input type="text" name="joueur_nom[]" /><br/>' +
                'Prénom: <input type="text" name="joueur_prenom[]" /><br/>' +
                'Email: <input type="email" name="joueur_email[]" /><br/>';
            col.appendChild(div);
        }

        function addParent() {
            var col = document.getElementById("parent-col");
            var div = document.createElement("div");
            div.className = "parent-row";
            div.innerHTML =
                'Nom: <input type="text" name="parent_nom[]" /><br/>' +
                'Prénom: <input type="text" name="parent_prenom[]" /><br/>' +
                'Email: <input type="email" name="parent_email[]" /><br/>';
            col.appendChild(div);
        }
    </script>

</body>
</html>
