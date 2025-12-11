package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bd.HibernateUtil;
import model.Evenement;
import model.Groupe;

@WebServlet("/CtrlConvoquer")
public class CtrlConvoquer extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("selectionEvenement".equals(action)) {
			afficherSelection(request, response);
		} else {
			//
		}
	}

	private void afficherSelection(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idEvtStr = request.getParameter("idEvenement");
		if (idEvtStr == null) {
			request.setAttribute("messageErreur", "Mauvaise id de l'évènement.");
			//To do: à modifier.
			request.getRequestDispatcher("/jsp/PageConvoquer.jsp").forward(request, response);
			return;
		}

		 Long idEvenement = Long.parseLong(idEvtStr);

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Evenement evt = session.get(Evenement.class, idEvenement);
			List<Groupe> groupes = session.createQuery("from Groupe", Groupe.class).list();

			request.setAttribute("evenementSelectionne", evt);
			request.setAttribute("groupesCoach", groupes);
		}

		request.getRequestDispatcher("/jsp/PageSelection.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("validerConvocation".equals(action)) {
			validerConvocation(request, response);
		} else {
			//
		}
	}

	private void validerConvocation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idEvtStr = request.getParameter("idEvenement");
		String idGroupeStr = request.getParameter("idGroupe");

		if (idEvtStr == null || idGroupeStr == null) {
			request.setAttribute("messageErreur", "Veuillez sélectionner un groupe.");
			afficherSelection(request, response);
			return;
		}

		 Long idEvenement = Long.parseLong(idEvtStr);
		Long idGroupe = Long.parseLong(idGroupeStr);

		Evenement evt = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();

			evt = session.get(Evenement.class, idEvenement);
			Groupe g = session.get(Groupe.class, idGroupe);

			evt.setGroupe(g);
			session.update(evt);
			tx.commit();

			List<Groupe> tousGroupes = session.createQuery("from Groupe", Groupe.class).list();
			request.setAttribute("groupesCoach", tousGroupes);
		}

		request.setAttribute("evenementSelectionne", evt);
		request.setAttribute("messageSucces", "Le groupe est marqué pour convocation");
		request.getRequestDispatcher("/jsp/PageSelection.jsp").forward(request, response);
	}

}