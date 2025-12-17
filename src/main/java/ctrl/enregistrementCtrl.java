package ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.EtrePresent;
import model.Evenement;
import service.eventService;

@WebServlet("/secretaire/enregistrement")
public class enregistrementCtrl extends HttpServlet {
    private static final eventService eventService = new eventService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventId = req.getParameter("id");
        if (eventId != null && !eventId.isEmpty()) {
            try {
                long id = Long.parseLong(eventId);
                forwardDetail(req, resp, id);
                return;
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id évènement invalide");
                return;
            }
        }

        req.setAttribute("events", eventService.getAllEntrainements());
        req.getRequestDispatcher("/jsp/secretaire/enregistrement.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventId = req.getParameter("id");

        if (eventId == null || eventId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id évènement manquant");
            return;
        }

        try {
            forwardDetail(req, resp, Long.parseLong(eventId));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id évènement invalide");
        }
    }

    private void forwardDetail(HttpServletRequest req, HttpServletResponse resp, long eventId) throws ServletException, IOException {
        Evenement evenement = eventService.findById(eventId);
        if (evenement == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Évènement introuvable");
            return;
        }

        List<EtrePresent> joueursPresent = new ArrayList<>();
        evenement.getEtresPresents().forEach(e -> {
            if (e.getJoueur() != null && "OUI".equalsIgnoreCase(e.getConfirmerPresenceJoueur())) {
                joueursPresent.add(e);
            }
        });

        req.setAttribute("evenement", evenement);
        req.setAttribute("etrePresents", joueursPresent);

        req.getRequestDispatcher("/jsp/secretaire/enregistreDetail.jsp").forward(req, resp);
    }
}
