package ctrl;

import model.ConvocationEvenement;
import repo.ConvocationEvenementRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/confirmation/match")
public class ConfirmationMatchCtrl extends HttpServlet {

    private final ConvocationEvenementRepo convocationRepo = new ConvocationEvenementRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        if (token == null || token.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Lien invalide");
            return;
        }

        ConvocationEvenement convocation = convocationRepo.findByToken(token);

        if (convocation == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Convocation introuvable");
            return;
        }

        request.setAttribute("convocation", convocation);
        request.getRequestDispatcher("/jsp/convocation/confirmationMatch.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        String choix = request.getParameter("peutJouer");

        if (token == null || choix == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ConvocationEvenement convocation = convocationRepo.findByToken(token);

        if (convocation == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Mise à jour de la réponse (écrase l'ancienne si elle existe)
        convocation.setConfirmePresence("oui".equalsIgnoreCase(choix));
        convocation.setLastUpdate(LocalDateTime.now());

        convocationRepo.update(convocation);

        // Redirection pour éviter le double POST et afficher l'état actuel
        response.sendRedirect(
                request.getContextPath()
                + "/confirmation/match?token=" + token
        );
    }
}
