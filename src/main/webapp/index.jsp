<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>Accueil</title>

<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f3f4f6;
	margin: 0;
	padding: 0;
}

.container {
	max-width: 600px;
	margin: 80px auto;
	background-color: #ffffff;
	padding: 35px 45px;
	border-radius: 12px;
	text-align: center;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
}

h1 {
	font-size: 26px;
	color: #111827;
	margin-bottom: 10px;
}

p {
	font-size: 16px;
	color: #374151;
}

.role-buttons {
	margin-top: 25px;
	display: flex;
	flex-direction: column;
	gap: 12px;
}

.btn-role {
	display: block;
	padding: 12px;
	background-color: #2563eb;
	color: white;
	text-decoration: none;
	border-radius: 8px;
	font-size: 16px;
	transition: background-color 0.15s ease, transform 0.05s ease;
}

.btn-role:hover {
	background-color: #1d4ed8;
	transform: translateY(-2px);
}

.message {
	margin-top: 20px;
	color: #dc2626;
	font-weight: bold;
	font-size: 14px;
}
</style>
</head>
<body>

	<div class="container">
		<h1>Bienvenue !</h1>

		<p>Veuillez choisir votre profil :</p>

		<div class="role-buttons">
			<a class="btn-role" href="CtrlFrontLogin?role=Joueur">Joueur</a> <a
				class="btn-role" href="CtrlFrontLogin?role=Coach">Coach</a> <a
				class="btn-role" href="CtrlFrontLogin?role=Parent">Parent</a> <a
				class="btn-role" href="CtrlFrontLogin?role=Secretaire">Secrétaire</a>
		</div>

		<div class="message">${message != null ? message : ""}</div>
	</div>
	</div>
</body>
</html>