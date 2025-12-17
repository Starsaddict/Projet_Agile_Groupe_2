package ctrl;

import model.Covoiturage;
import model.Evenement;
import model.Utilisateur;
import service.covoiturageService;
import service.evenementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/parent/covoiturage")
public class CtrlCovoiturage extends HttpServlet {

    private final covoiturageService service = new covoiturageService();
    private final evenementService eService = new evenementService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Utilisateur user = (Utilisateur) req.getSession().getAttribute("user");
        if (user == null || !(user instanceof model.Parent)) {
            resp.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        // Tous les covoiturages avec collections fetchées
        List<Covoiturage> covoiturages = service.findAllCovoiturages();
        req.setAttribute("covoiturages", covoiturages);

        // Événements futurs
        List<Evenement> evenements = eService.findEvenementsFuturs();
        req.setAttribute("evenements", evenements);

        req.getRequestDispatcher("/jsp/parent/covoiturage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Utilisateur user = (Utilisateur) req.getSession().getAttribute("user");
        if (user == null || !(user instanceof model.Parent)) {
            resp.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        String action = req.getParameter("action");

        try {
            switch (action) {
                case "creer": {
                    Long idEvenement = Long.valueOf(req.getParameter("idEvenement"));
                    Covoiturage c = new Covoiturage();
                    c.setConducteur(user);
                    c.setLieuDepartCovoiturage(req.getParameter("lieuDepart"));
                    c.setNbPlacesMaxCovoiturage(Integer.parseInt(req.getParameter("nbPlaces")));
                    c.setDateCovoiturage(LocalDateTime.parse(req.getParameter("date")));

                    Evenement e = new Evenement();
                    e.setIdEvenement(idEvenement);
                    c.setEvenement(e);

                    service.creerCovoiturage(c);
                    break;
                }

                case "supprimer": {
                    Long id = Long.valueOf(req.getParameter("idCovoiturage"));
                    service.supprimer(id, user);
                    break;
                }

                case "rejoindre": {
                    Long id = Long.valueOf(req.getParameter("idCovoiturage"));
                    int nbPlaces = Integer.parseInt(req.getParameter("nbPlaces")); // multi-réservation
                    service.reserver(id, user, nbPlaces);
                    break;
                }

                case "quitter": {
                    Long id = Long.valueOf(req.getParameter("idCovoiturage"));
                    service.quitter(id, user);
                    break;
                }

                default:
                    break;
            }
        } catch (IllegalArgumentException | IllegalStateException ex) {
            // Optionnel : logger l'erreur ou stocker un message pour JSP
            ex.printStackTrace();
        }

        // Redirection unique après traitement
        resp.sendRedirect(req.getContextPath() + "/parent/covoiturage");
    }
}
