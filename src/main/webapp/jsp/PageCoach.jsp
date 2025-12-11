<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PageCoach</title>
</head>
<body>
	<h1>Bienvenu! Coach</h1>
	<!-- Gérer les groupes des joueurs -->
	<form action="CtrlCoach" method="get">
		<input type="hidden" name="action" value="GestionGroupe">
		<button type="submit">Créer</button>
	</form>
	<br>
	<form action="CtrlCoach" method="get">
		<input type="hidden" name="action" value="ConvocationGroupe">
		<button type="submit">Convoquer</button>
	</form>
</body>
</html>