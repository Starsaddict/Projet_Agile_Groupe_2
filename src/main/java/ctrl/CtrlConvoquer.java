package ctrl;

import java.io.IOException;
import java.time.LocalDateTime;
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
			request.getRequestDispatcher("/jsp/coach/PageConvoquer.jsp").forward(request, response);
			return;
		}

		 Long idEvenement = Long.parseLong(idEvtStr);

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Evenement evt = session.get(Evenement.class, idEvenement);
			List<Groupe> groupes = session.createQuery("from Groupe", Groupe.class).list();

			request.setAttribute("evenementSelectionne", evt);
			request.setAttribute("groupesCoach", groupes);
		}

		request.getRequestDispatcher("/jsp/coach/PageSelection.jsp").forward(request, response);
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

	    if (idEvtStr == null || idGroupeStr == null || idEvtStr.isBlank() || idGroupeStr.isBlank()) {
	        request.setAttribute("messageErreur", "Veuillez sélectionner un groupe.");
	        afficherSelection(request, response);
	        return;
	    }

	    Long idEvenement;
	    Long idGroupe;

	    try {
	        idEvenement = Long.parseLong(idEvtStr);
	        idGroupe = Long.parseLong(idGroupeStr);
	    } catch (NumberFormatException ex) {
	        request.setAttribute("messageErreur", "Id invalide.");
	        afficherSelection(request, response);
	        return;
	    }

	    Evenement evt;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Transaction tx = session.beginTransaction();

	        // 1) Charger l'évènement et le groupe
	        evt = session.get(Evenement.class, idEvenement);
	        Groupe g = session.get(Groupe.class, idGroupe);

	        if (evt == null || g == null) {
	            tx.rollback();
	            request.setAttribute("messageErreur", "Évènement ou groupe introuvable.");
	            afficherSelection(request, response);
	            return;
	        }

	        if (evt.getDateEvenement() == null) {
	            tx.rollback();
	            request.setAttribute("messageErreur", "La date de l'évènement est vide.");
	            afficherSelection(request, response);
	            return;
	        }

	        // 2) Définir la journée : [00:00, lendemain 00:00[
	        LocalDateTime start = evt.getDateEvenement().toLocalDate().atStartOfDay();
	        LocalDateTime end = start.plusDays(1);

	        // 3) 🔒 Vérifier conflit: même groupe + même jour + match officiel
	        Long nb = session.createQuery(
	                "select count(e) from Evenement e " +
	                "where e.groupe.idGroupe = :gid " +
	                "and e.typeEvenement = 'Match-officiel' " +
	                "and e.dateEvenement >= :start " +
	                "and e.dateEvenement < :end " +
	                "and e.idEvenement <> :eid",
	                Long.class)
	            .setParameter("gid", idGroupe)
	            .setParameter("start", start)
	            .setParameter("end", end)
	            .setParameter("eid", idEvenement)
	            .uniqueResult();

	        if (nb != null && nb > 0) {
	            tx.rollback();
	            request.setAttribute("messageErreur",
	                    "Impossible : ce groupe est déjà convoqué pour un match officiel ce jour-là.");
	            afficherSelection(request, response);
	            return;
	        }

	        // 4) Sauvegarde
	        evt.setGroupe(g);
	        session.update(evt);
	        tx.commit();

	    }

	    request.setAttribute("evenementSelectionne", evt);
	    request.setAttribute("messageSucces", "Le groupe est marqué pour convocation");
	    request.getRequestDispatcher("/jsp/coach/PageSelection.jsp").forward(request, response);
	}

}