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

        // Récupérer tous les covoiturages avec fetch pour éviter LazyInitializationException
        List<Covoiturage> covoiturages = service.findAllCovoiturages();
        req.setAttribute("covoiturages", covoiturages);

        // Récupérer tous les événements
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
                try {
                    service.reserver(id, user);
                } catch (IllegalArgumentException ignored) {
                    // tu peux logger ou afficher un message d'erreur si tu veux
                }
                break;
            }
            
            case "quitter":
                Long idCovoiturage = Long.valueOf(req.getParameter("idCovoiturage"));
                try {
                    service.quitter(idCovoiturage, user);
                } catch (IllegalArgumentException | IllegalStateException e) {

                }
                break;


            default:
                break;
        }

        // redirection générale après action
        resp.sendRedirect(req.getContextPath() + "/parent/covoiturage");
    }

}
