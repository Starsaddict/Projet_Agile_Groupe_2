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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

@WebServlet("/CtrlCoach")
public class CtrlCoach extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "";

        switch (action) {

            case "PageCoach":
            	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			        Date now = new Date();
			        List<Evenement> evenements = session
			                .createQuery(
			                        "from Evenement e where e.dateEvenement >= :now order by e.dateEvenement",
			                        Evenement.class)
			                .setParameter("now", now)
			                .list();

			        List<Groupe> groupes = session.createQuery("from Groupe", Groupe.class).list();
			        for (Groupe g : groupes) {
			            g.getJoueurs().size();
			        }
			        
			        request.setAttribute("evenements", evenements);
			        request.setAttribute("groupes", groupes);
			    }
                request.getRequestDispatcher("/jsp/coach/PageCoach.jsp").forward(request, response);
                break;

            case "ConvocationGroupe":
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    Date now = new Date();
                    List<Evenement> evenements = session
                            .createQuery("from Evenement e where e.dateEvenement >= :now order by e.dateEvenement",
                                    Evenement.class)
                            .setParameter("now", now)
                            .list();
                    
			        request.setAttribute("evenements", evenements);
                }
                request.getRequestDispatcher("/jsp/coach/PageConvoquer.jsp").forward(request, response);
                break;

            case "GestionGroupe":
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {

                    // Charger tous les joueurs pour le formulaire
                    List<Joueur> joueurs = session.createQuery("from Joueur", Joueur.class).list();

                    // Charger les groupes avec leurs joueurs
                    List<Groupe> groupes = session.createQuery(
                        "SELECT DISTINCT g FROM Groupe g LEFT JOIN FETCH g.joueurs", Groupe.class
                    ).list();

                    // Supprimer les doublons éventuels causés par le fetch join
                    List<Groupe> groupesSansDoublons = new ArrayList<>(new LinkedHashSet<>(groupes));

                    request.setAttribute("joueurs", joueurs);
                    request.setAttribute("groupes", groupesSansDoublons);
                }
                request.getRequestDispatcher("/jsp/coach/PageGestionGroupe.jsp").forward(request, response);
                break;

            default:
                response.sendRedirect("CtrlCoach?action=GestionGroupe");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "EnregistrerGroupe":
                creerGroupe(request, response);
                break;
            case "SupprimerGroupe":
                supprimerGroupe(request, response);
                break;
            default:
                response.sendRedirect("CtrlCoach?action=GestionGroupe");
        }
    }

    private void creerGroupe(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

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

    private void supprimerGroupe(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long idGroupe = Long.parseLong(request.getParameter("idGroupe"));

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Groupe groupe = session.get(Groupe.class, idGroupe);
            if (groupe != null) {
                session.delete(groupe);
            }

            tx.commit();
        }

        response.sendRedirect("CtrlCoach?action=GestionGroupe");
    }

    
}
