package ctrl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bd.HibernateUtil;
import model.Evenement;
import model.Groupe;
import model.Joueur;

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
			// To do: à modifier.
			request.getRequestDispatcher("/jsp/coach/PageConvoquer.jsp").forward(request, response);
			return;
		}

		Long idEvenement = Long.parseLong(idEvtStr);

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

		    Evenement evt = session.get(Evenement.class, idEvenement);

		    List<Groupe> groupes = session.createQuery(
		            "select distinct g from Groupe g left join fetch g.joueurs",
		            Groupe.class
		    ).list();

		    // ====== 1) 得到比赛当天的区间 [startDay, endDay) ======
		    LocalDateTime startDay;
		    Object d = evt.getDateEvenement(); // 一般是 Timestamp
		    if (d instanceof java.sql.Timestamp) {
		        startDay = ((java.sql.Timestamp) d).toLocalDateTime().toLocalDate().atStartOfDay();
		    } else if (d instanceof LocalDateTime) {
		        startDay = ((LocalDateTime) d).toLocalDate().atStartOfDay();
		    } else {
		        startDay = LocalDateTime.now().toLocalDate().atStartOfDay();
		    }
		    LocalDateTime endDay = startDay.plusDays(1);

		    // ====== 2) 找到当天“有缺席重叠”的球员 id ======
		    List<Long> absentIds = session.createQuery(
		            "select distinct ea.joueur.idUtilisateur " +
		            "from EtreAbsent ea " +
		            "where ea.absenceDebut < :endDay " +
		            "and (ea.absenceFin is null or ea.absenceFin >= :startDay)",
		            Long.class
		    ).setParameter("startDay", startDay)
		     .setParameter("endDay", endDay)
		     .list();

		    Set<Long> absentJoueurIds = new HashSet<>(absentIds);


		    // ====== 3) 推导缺席组（标红 + radio disabled）以及提示文本 ======
		    Set<Long> groupesIndisponibles = new HashSet<>();
		    Map<Long, String> detailAbsencesParGroupe = new HashMap<>();

		    for (Groupe g : groupes) {
		        List<String> absentsDansGroupe = new ArrayList<>();

		        for (Joueur j : g.getJoueurs()) {
		            Long jid = j.getIdUtilisateur();
		            if (jid != null && absentJoueurIds.contains(jid)) {
		                absentsDansGroupe.add(j.getNomUtilisateur());
		            }
		        }

		        if (!absentsDansGroupe.isEmpty()) {
		            groupesIndisponibles.add(g.getIdGroupe());
		            detailAbsencesParGroupe.put(
		                    g.getIdGroupe(),
		                    "Joueur(s) absent(s) : " + String.join(", ", absentsDansGroupe)
		            );
		        }
		    }

		    request.setAttribute("evenementSelectionne", evt);
		    request.setAttribute("groupesCoach", groupes);

		    request.setAttribute("groupesIndisponibles", groupesIndisponibles);
		    request.setAttribute("detailAbsencesParGroupe", detailAbsencesParGroupe);
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

	        evt = session.get(Evenement.class, idEvenement);
	        Groupe g = session.get(Groupe.class, idGroupe);

	        if (evt == null || g == null) {
	            echec(tx, request, response, "Évènement ou groupe introuvable.");
	            return;
	        }

	        if (evt.getDateEvenement() == null) {
	            echec(tx, request, response, "La date de l'évènement est vide.");
	            return;
	        }
	        
	        

	        LocalDateTime start = evt.getDateEvenement().toLocalDate().atStartOfDay();
	        LocalDateTime end = start.plusDays(1);	       

	        Long nbAbsents = session.createQuery(
	                "select count(ea) from EtreAbsent ea " +
	                "where ea.joueur in (select j from Groupe gg join gg.joueurs j where gg.idGroupe = :gid) " +
	                "and ea.absenceDebut < :endDay " +
	                "and (ea.absenceFin is null or ea.absenceFin >= :startDay)",
	                Long.class
	        ).setParameter("gid", idGroupe)
	         .setParameter("startDay", start)
	         .setParameter("endDay", end)
	         .uniqueResult();

	        if (nbAbsents != null && nbAbsents > 0) {
	            echec(tx, request, response, "Impossible : ce groupe contient un joueur absent ce jour-là.");
	            return;
	        }


	        List<ConflitJoueur> conflitsJ = conflitsJoueursMemeJour(session, idGroupe, start, end, idEvenement);
	        if (conflitsJ != null && !conflitsJ.isEmpty()) {

	            String detail = conflitsJ.stream()
	                    .map(c -> c.getJoueurNom() + " (déjà dans " + c.getGroupeNom() + " : " + c.getEvenementNom() + ")")
	                    .distinct()
	                    .reduce((a, b) -> a + " ; " + b)
	                    .orElse("Conflit joueurs");

	            echec(tx, request, response,
	                    "Impossible : certains joueurs sont déjà convoqués ce jour-là. " + detail);
	            return;
	        }

	        List<String> conflitsEvtNoms = conflitsMemeGroupeMemeJourNoms(session, idGroupe, start, end, idEvenement);
	        if (conflitsEvtNoms != null && !conflitsEvtNoms.isEmpty()) {
	            String noms = String.join(", ", conflitsEvtNoms);
	            echec(tx, request, response,
	                    "Impossible : ce groupe est déjà convoqué ce jour-là pour : " + noms);
	            return;
	        }

	        List<Groupe> groupes = session.createQuery(
	        	    "select distinct g from Groupe g left join fetch g.joueurs",
	        	    Groupe.class
	        	).list();
	        request.setAttribute("groupesCoach", groupes);
	        evt.setGroupe(g);
	        session.update(evt);
	        tx.commit();
	    }
	    
	    request.setAttribute("evenementSelectionne", evt);
	    request.setAttribute("messageSucces", "Le groupe est marqué pour convocation");
	    request.getRequestDispatcher("/jsp/coach/PageSelection.jsp").forward(request, response);
	}

	private static class ConflitJoueur {
		private final String joueurNom;
		private final String groupeNom;
		private final String evenementNom;

		public ConflitJoueur(String joueurNom, String groupeNom, String evenementNom) {
			this.joueurNom = joueurNom;
			this.groupeNom = groupeNom;
			this.evenementNom = evenementNom;
		}

		public String getJoueurNom() {
			return joueurNom;
		}

		public String getGroupeNom() {
			return groupeNom;
		}

		public String getEvenementNom() {
			return evenementNom;
		}
	}

	private List<ConflitJoueur> conflitsJoueursMemeJour(Session session, Long idGroupeChoisi, LocalDateTime start,
			LocalDateTime end, Long idEvenementCourant) {

		List<Object[]> rows = session
				.createQuery("select distinct j.nomUtilisateur, g.nomGroupe, e.nomEvenement " + "from Evenement e "
						+ "join e.groupe g " + "join g.joueurs j " + "where e.typeEvenement = 'MATCH_OFFICIEL' "
						+ "and e.dateEvenement >= :start and e.dateEvenement < :end " + "and e.idEvenement <> :eid "
						+ "and g.idGroupe <> :gidChoisi " + "and j in ("
						+ "   select j2 from Groupe g2 join g2.joueurs j2 " + "   where g2.idGroupe = :gidChoisi" + ")",
						Object[].class)
				.setParameter("start", start).setParameter("end", end).setParameter("eid", idEvenementCourant)
				.setParameter("gidChoisi", idGroupeChoisi).list();

		return rows.stream().map(r -> new ConflitJoueur((String) r[0], (String) r[1], (String) r[2])).toList();
	}

	private List<String> conflitsMemeGroupeMemeJourNoms(Session session, Long idGroupe, LocalDateTime start,
			LocalDateTime end, Long idEvenementCourant) {

		return session
				.createQuery("select e.nomEvenement " + "from Evenement e " + "where e.groupe.idGroupe = :gid "
						+ "and e.typeEvenement = 'MATCH_OFFICIEL' "
						+ "and e.dateEvenement >= :start and e.dateEvenement < :end " + "and e.idEvenement <> :eid",
						String.class)
				.setParameter("gid", idGroupe).setParameter("start", start).setParameter("end", end)
				.setParameter("eid", idEvenementCourant).list();
	}

	private void echec(Transaction tx, HttpServletRequest request, HttpServletResponse response, String msg)
			throws ServletException, IOException {
		if (tx != null)
			tx.rollback();
		request.setAttribute("messageErreur", msg);
		afficherSelection(request, response);
	}

}