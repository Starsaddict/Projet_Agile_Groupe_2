package ctrl;

import repo.ConvocationEvenementRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/CtrlStatistique")
public class CtrlStatistique extends HttpServlet {

    private final ConvocationEvenementRepo convocationRepo =
            new ConvocationEvenementRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Sécurité : utilisateur connecté
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login");
            return;
        }

        String action = request.getParameter("action");

        if ("presenceEntrainement".equals(action)) {
            afficherTauxPresenceEntrainement(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue");
        }
    }

    private void afficherTauxPresenceEntrainement(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long total = convocationRepo.countPresencesRenseigneesEntrainement();
        long presents = convocationRepo.countPresencesEffectivesEntrainement();

        double tauxPresence = 0;
        if (total > 0) {
            tauxPresence = (presents * 100.0) / total;
        }

        // Données envoyées à la JSP
        request.setAttribute("totalPresences", total);
        request.setAttribute("presencesEffectives", presents);
        request.setAttribute("tauxPresence", tauxPresence);
        
        List<Object[]> statsParJoueur =
                convocationRepo.statsPresenceParJoueurEntrainement();

        request.setAttribute("statsParJoueur", statsParJoueur);
        

        // Redirection vers la vue
        request.getRequestDispatcher(
            "/jsp/statistiques/presenceEntrainement.jsp"
        		
        ).forward(request, response);
    }
}
