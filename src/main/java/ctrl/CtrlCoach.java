package ctrl;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bd.HibernateUtil;
import model.Evenement;
import model.Groupe;
import model.Joueur;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@WebServlet("/CtrlCoach")
public class CtrlCoach extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null)
			action = "";

		switch (action) {
		case "EditerGroupe":
			editerGroupe(request, response);
			break;
		case "Home":
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				LocalDateTime now = LocalDateTime.now();
				List<Evenement> evenements = session.createQuery(
						"from Evenement e where e.dateEvenement >= :now and e.typeEvenement = :typeEvt order by e.dateEvenement",
						Evenement.class).setParameter("now", now).setParameter("typeEvt", "MATCH_OFFICIEL").list();

				List<Groupe> groupes = session.createQuery("from Groupe", Groupe.class).list();
				for (Groupe g : groupes) {
					g.getJoueurs().size();
				}

				request.setAttribute("evenements", evenements);
				request.setAttribute("groupes", groupes);
			}
			request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
			break;
		case "GestionGroupe":
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {

				// Charger tous les joueurs pour le formulaire
				List<Joueur> joueurs = session.createQuery("from Joueur", Joueur.class).list();

				// Charger les groupes avec leurs joueurs
				List<Groupe> groupes = session
						.createQuery("SELECT DISTINCT g FROM Groupe g LEFT JOIN FETCH g.joueurs", Groupe.class).list();

				// Supprimer les doublons éventuels causés par le fetch join
				List<Groupe> groupesSansDoublons = new ArrayList<>(new LinkedHashSet<>(groupes));

				request.setAttribute("joueurs", joueurs);
				request.setAttribute("groupes", groupesSansDoublons);
			}
			request.getRequestDispatcher("/jsp/coach/PageGestionGroupe.jsp").forward(request, response);
			break;

		default:
			response.sendRedirect("CtrlCoach?action=Home");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null)
			action = "";

		switch (action) {
		case "EnregistrerGroupe":
			creerGroupe(request, response);
			break;
		case "SupprimerGroupe":
			supprimerGroupe(request, response);
			break;
		case "ModifierGroupe":
			modifierGroupe(request, response);
			break;
		default:
			response.sendRedirect("CtrlCoach?action=GestionGroupe");
		}
	}

	private void creerGroupe(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String nom = request.getParameter("nomGroupe");
		String[] joueursIds = request.getParameterValues("joueursIds");

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();

			Groupe groupe = new Groupe();
			groupe.setNomGroupe(nom);

			if (joueursIds != null) {
				for (String idStr : joueursIds) {
					Joueur j = session.get(Joueur.class, Long.parseLong(idStr));
					groupe.getJoueurs().add(j);

					if (j.getGroupes() == null) {
						j.setGroupes(new ArrayList<>());
					}
					j.getGroupes().add(groupe);
				}
			}

			session.save(groupe);
			tx.commit();
		}

		response.sendRedirect("CtrlCoach?action=GestionGroupe");
	}

	private void supprimerGroupe(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Long idGroupe = Long.parseLong(request.getParameter("idGroupe"));

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();

			session.createQuery("update Evenement e set e.groupe = null where e.groupe.idGroupe = :gid")
					.setParameter("gid", idGroupe).executeUpdate();

			session.createNativeQuery("delete from Groupe_Joueur where IdGroupe = :gid").setParameter("gid", idGroupe)
					.executeUpdate();

			Groupe groupe = session.get(Groupe.class, idGroupe);
			if (groupe != null) {
				session.delete(groupe);
			}

			tx.commit();
		}

		response.sendRedirect("CtrlCoach?action=GestionGroupe");
	}

	private void editerGroupe(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long idGroupe = Long.parseLong(request.getParameter("idGroupe"));

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			List<Joueur> joueurs = session.createQuery("from Joueur", Joueur.class).list();

			List<Groupe> groupes = session
					.createQuery("select distinct g from Groupe g left join fetch g.joueurs", Groupe.class).list();

			Groupe groupeAEditer = session
					.createQuery("select g from Groupe g left join fetch g.joueurs where g.idGroupe = :id",
							Groupe.class)
					.setParameter("id", idGroupe).uniqueResult();

			request.setAttribute("joueurs", joueurs);
			request.setAttribute("groupes", groupes);
			request.setAttribute("groupeAEditer", groupeAEditer);
		}

		request.getRequestDispatcher("/jsp/coach/PageGestionGroupe.jsp").forward(request, response);
	}

	private void modifierGroupe(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Long idGroupe = Long.parseLong(request.getParameter("idGroupe"));
		String nomGroupe = request.getParameter("nomGroupe");
		String[] joueursIds = request.getParameterValues("joueursIds");

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();

			Groupe g = session.get(Groupe.class, idGroupe);
			if (g != null) {
				g.setNomGroupe(nomGroupe);

				g.getJoueurs().clear();

				if (joueursIds != null) {
					for (String idStr : joueursIds) {
						Long idJ = Long.parseLong(idStr);
						Joueur j = session.get(Joueur.class, idJ);
						if (j != null)
							g.getJoueurs().add(j);
					}
				}

				session.update(g);
			}

			tx.commit();
		}

		response.sendRedirect("CtrlCoach?action=GestionGroupe");
	}

}
