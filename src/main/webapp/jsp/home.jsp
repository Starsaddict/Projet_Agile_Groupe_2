<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="model.Groupe, model.Joueur, model.Evenement"%>
<%@ page import="java.util.List"%>
<%
if (session.getAttribute("user") == null) {
	response.sendRedirect("Login");
	return;
}
%>
<c:set var="roles" value="${sessionScope.roles}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Accueil</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" />
<style>
.pill {
	display: inline-block;
	padding: 4px 10px;
	border-radius: 999px;
	font-size: 12px;
	color: #fff;
}

.pill.blue {
	background: var(--primary);
}

.pill.green {
	background: var(--success);
}

.pill.pink {
	background: #f472b6;
}

.section-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
	gap: 1rem;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 0.75rem;
	font-size: 0.9rem;
}

th, td {
	padding: 0.5rem;
	border-bottom: 1px solid var(--border);
	text-align: left;
}

.empty {
	color: var(--text-muted);
	font-style: italic;
}

.top-bar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	gap: 1rem;
	margin-bottom: 1rem;
}

.top-bar p {
	margin: 0;
	color: var(--text-muted);
}

.links {
	display: flex;
	gap: 0.5rem;
	flex-wrap: wrap;
	margin-top: 0.75rem;
}

.badge {
	display: inline-block;
	padding: 3px 8px;
	border-radius: 9999px;
	font-size: 11px;
	background-color: #e5f2ff;
	color: #1d4ed8;
	margin-left: 6px;
}
/* =========================
   Coach 首页样式增强（只补充/覆盖）
   ========================= */

/* 1) “Coach” 蓝色小气泡与欢迎词同一行显示 */
.section-grid > .card > h2:first-of-type {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 6px;
}

/* 让 div.pill 不再占一整行（从 block -> inline） */
.section-grid > .card > .pill {
  display: inline-flex;   /* 覆盖你原来的 inline-block 也没问题 */
  align-items: center;
  vertical-align: middle;
  margin: 0;              /* 防止它自己把行撑开 */
}

/* 把 pill “视觉上”挪到欢迎词旁边：紧贴上一个 h2 */
.section-grid > .card > h2:first-of-type + .pill {
  margin-left: 10px;
  transform: translateY(-2px);
}

/* 2) évènement / groupe 数量小气泡显示在标题旁边（视觉上放到同一行右侧） */
.section-grid > .card > h2 {
  position: relative; /* 作为定位参考 */
}

/* 你的 badge 现在在 p 后面：用负 margin + 右浮动把它“拉回”标题行 */
.section-grid > .card > p + .badge {
  float: right;
  margin-top: -44px;  /* 往上拉到标题同一行（可微调 -40~-48） */
}

/* 清掉 float 对后面 table 的影响 */
.section-grid > .card > p + .badge::after {
  content: "";
  display: block;
  clear: both;
}

/* 3) “Gestion des groupes” 按钮居中显示 */
.section-grid > .card > table + .links {
  justify-content: center;
}

/* 4) 给“Évènements...” 和 “Groupes...” 两个标题加小装饰（不影响第一个欢迎标题） */
.section-grid > .card > h2:not(:first-of-type) {
  padding-left: 14px;
  margin-top: 22px;
}

/* 左侧小竖条 */
.section-grid > .card > h2:not(:first-of-type)::before {
  content: "";
  position: absolute;
  left: 0;
  top: 50%;
  width: 4px;
  height: 70%;
  transform: translateY(-50%);
  border-radius: 999px;
  background: var(--primary);
}

/* 额外小圆点（更“有样式”一点） */
.section-grid > .card > h2:not(:first-of-type)::after {
  content: "";
  position: absolute;
  left: -2px;
  top: 50%;
  width: 8px;
  height: 8px;
  transform: translateY(-50%);
  border-radius: 999px;
  background: #c7d2fe;
}

/* 5) 小优化：表格标题和内容更对齐一些（不会改变你的结构） */
.section-grid > .card > h2 + p {
  margin-top: 6px;
}

</style>
</head>
<body class="page">
	<div class="container">
		<div class="card">
			<div class="top-bar">
				<div>
					<h1>Tableau de bord</h1>
					<p>Contenus affichés selon vos rôles</p>
				</div>
				<div>
					<c:if test="${not empty sessionScope.user}">
						<span class="pill blue">${sessionScope.user.emailUtilisateur}</span>
					</c:if>
					<form action="${pageContext.request.contextPath}/CtrlLogout"
						method="post" style="display: inline;">
						<button class="btn secondary" type="submit">Déconnexion</button>
					</form>
				</div>
			</div>

			<div class="section-grid">
				<c:if test="${not empty roles and fn:contains(roles, 'Coach')}">
					<%
					List<Evenement> evenements = (List<Evenement>) request.getAttribute("evenements");
					List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupes");
					%>
					<div class="card">
						<h2>Bienvenue, Coach ${sessionScope.user.prenomUtilisateur}
							${sessionScope.user.nomUtilisateur}!</h2>
						<div class="pill blue">Coach</div>

						<!-- Section Evenements -->
						<h2>Évènements officiels à venir</h2>
						<p>Vous pouvez choisir ou modifier les convocations.</p>
						<%
						if (evenements != null) {
						%>
						<span class="badge"><%=evenements.size()%> évènement(s)</span>
						<%
						}
						%>
						<table>
							<thead>
								<tr>
									<th>Titre</th>
									<th>Date</th>
									<th>Type</th>
									<th>Groupe</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<%
								if (evenements != null && !evenements.isEmpty()) {
									for (Evenement e : evenements) {
								%>
								<tr>
									<td><%=e.getNomEvenement()%></td>
									<td><%=e.getDateEvenement()%></td>
									<td><%=e.getTypeEvenement()%></td>
									<td>
										<%
										if (e.getGroupe() != null) {
										%> <%=e.getGroupe().getNomGroupe()%> <%
 } else {
 %> <span class="empty-msg">Aucun groupe convoqué.</span> <%
 }
 %>
									</td>
									<td>
										<form action="CtrlConvoquer" method="get" class="links">
											<input type="hidden" name="action" value="selectionEvenement">
											<input type="hidden" name="idEvenement"
												value="<%=e.getIdEvenement()%>">
											<button type="submit" class="btn">À convoquer</button>
										</form>
									</td>
								</tr>
								<%
								}
								} else {
								%>
								<tr>
									<td colspan="4" class="empty-msg">Aucun évènement officiel
										à venir.</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>

						<!-- Section Groupes -->
						<h2>Groupes existants</h2>
						<p>Vue simple des groupes existants.</p>
						<%
						if (groupes != null) {
						%>
						<span class="badge"><%=groupes.size()%> groupe(s)</span>
						<%
						}
						%>

						<table>
							<thead>
								<tr>
									<th>Nom du groupe</th>
									<th>Joueurs</th>
								</tr>
							</thead>
							<tbody>
								<%
								if (groupes != null && !groupes.isEmpty()) {
									for (Groupe g : groupes) {
								%>
								<tr>
									<td><%=g.getNomGroupe()%></td>
									<td>
										<%
										List<Joueur> joueurs = g.getJoueurs();
										if (joueurs != null && !joueurs.isEmpty()) {
											for (int i = 0; i < joueurs.size(); i++) {
												Joueur j = joueurs.get(i);
										%> <%=j.getPrenomUtilisateur()%> <%=j.getNomUtilisateur()%><%=(i < joueurs.size() - 1) ? ", " : ""%>
										<%
										}
										} else {
										%> <span class="empty-msg">Aucun joueur dans ce groupe.</span>
										<%
										}
										%>
									</td>
								</tr>
								<%
								}
								} else {
								%>
								<tr>
									<td colspan="2" class="empty-msg">Aucun groupe trouvé.</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
						<div class="links">
							<a class="btn" href="CtrlCoach?action=GestionGroupe">Gestion
								des groupes</a>
						</div>
					</div>
				</c:if>

				<c:if test="${not empty roles and fn:contains(roles, 'Parent')}">
					<div class="card">
						<div class="pill green">Parent</div>
						<h2>Services Parent</h2>
						<p>Accès rapide aux covoiturages, convocations et profil.</p>
						<div class="links">
							<a class="btn"
								href="${pageContext.request.contextPath}/jsp/covoiturage.jsp">Covoiturage</a>
							<a class="btn"
								href="${pageContext.request.contextPath}/jsp/convocation.jsp">Convocations</a>
							<a class="btn"
								href="${pageContext.request.contextPath}/parent/profil">Profil
								parent</a>
						</div>
					</div>
				</c:if>

				<c:if test="${not empty roles and fn:contains(roles, 'Secretaire')}">
					<div class="card">
						<div class="pill pink">Secrétaire</div>
						<h2>Administration</h2>
						<p>Créer/éditer des comptes et gérer les événements.</p>
						<div class="links">
							<a class="btn"
								href="${pageContext.request.contextPath}/evenementSecre">Gestion
								des événements</a> <a class="btn"
								href="${pageContext.request.contextPath}/secretaire/profil">Gestion
								des profils</a> <a class="btn"
								href="${pageContext.request.contextPath}/secretaire/convoquer?type=match">Convoquer
								les Matches</a>
						</div>
						<div style="margin-top: 8px;">
							<form method="post"
								action="${pageContext.request.contextPath}/secretaire/profil/sendInvitation"
								style="display: flex; gap: 8px; align-items: center;">
								<input type="email" name="email"
									placeholder="Envoyer code à email parent" required />
								<button type="submit" class="btn">Envoyer</button>
							</form>
						</div>
					</div>
				</c:if>

				<c:if test="${not empty roles and fn:contains(roles, 'Joueur')}">
					<div class="card">
						<div class="pill blue">Joueur</div>
						<h2>Vue Joueur</h2>
						<p>Consultez vos convocations et informations d'équipe.</p>
						<div class="links">
							<a class="btn"
								href="${pageContext.request.contextPath}/jsp/convocation.jsp">Mes
								convocations</a> <a class="btn"
								href="${pageContext.request.contextPath}/joueur/profil">Ma
								profil</a>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>

</body>
</html>
