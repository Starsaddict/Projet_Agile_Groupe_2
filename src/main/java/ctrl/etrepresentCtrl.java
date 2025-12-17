package ctrl;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import service.convocationEvenementService;
import model.ConvocationEvenementId;

@WebServlet("/secretaire/enregistrement/confirmPresent")

public class etrepresentCtrl extends HttpServlet {

    private final static convocationEvenementService convocationService = new convocationEvenementService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String eventId = req.getParameter("id");
        String joueurId = req.getParameter("joueurId");

        Long evenementId = null;
        Long joueurIdLong = null;
        if (eventId != null && !eventId.isEmpty()) {
            try {
                evenementId = Long.parseLong(eventId);
            } catch (NumberFormatException e) {
                evenementId = null;
            }
        }

        if (joueurId != null && !joueurId.isEmpty()) {
            try {
                joueurIdLong = Long.parseLong(joueurId);
            } catch (NumberFormatException e) {
                joueurIdLong = null;
            }
        }

        if (evenementId == null || joueurIdLong == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Identifiants convocation invalides");
            return;
        }

        boolean etrePresent = "present".equalsIgnoreCase(action);
        // "absent" or any other value defaults to false

        ConvocationEvenementId convocationId = new ConvocationEvenementId(evenementId, joueurIdLong);
        convocationService.updatePresenceReelle(convocationId, etrePresent);

        resp.sendRedirect(req.getContextPath() + "/secretaire/enregistrement?id=" + req.getParameter("id"));
    }
}
