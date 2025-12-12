<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Batch Create - Upload Excel</title>
</head>
<body>
<h1>Batch Create - Upload Excel</h1>
<% String uploadError = (String) request.getAttribute("uploadError"); %>
<% if (uploadError != null) { %>
<p style="color:red;"><%= uploadError %></p>
<% } %>
<form action="<%=request.getContextPath()%>/secretaire/profil/batchCreate" method="post" enctype="multipart/form-data">
    <label for="excelFile">Choisir un fichier Excel :</label>
    <input type="file" id="excelFile" name="excelFile" accept=".xls,.xlsx" required />
    <button type="submit">Upload</button>
</form>

<p><a href="<%=request.getContextPath()%>/secretaire/profil">Retour au profil</a></p>
</body>
</html>
