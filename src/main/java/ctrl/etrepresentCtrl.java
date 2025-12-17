package ctrl;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.EtrePresentId;
import service.etrePresentService;

@WebServlet("/secretaire/enregistrement/confirmPresent")

public class etrepresentCtrl extends HttpServlet {

    private final static etrePresentService etrePresentService = new etrePresentService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("etrepresentId");

        EtrePresentId epId = null;
        if (id != null && !id.isEmpty()) {
            String[] parts = id.split("-");
            if (parts.length == 3) {
                try {
                    epId = new EtrePresentId(
                            Long.parseLong(parts[0]),
                            Long.parseLong(parts[1]),
                            Long.parseLong(parts[2])
                    );
                } catch (NumberFormatException e) {
                    epId = null;
                }
            }
        }

        if (epId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Identifiant etrepresentId invalide");
            return;
        }

        boolean etrePresent = "present".equalsIgnoreCase(action);
        // "absent" or any other value defaults to false

        etrePresentService.updatePresenceReelle(epId, etrePresent);

        resp.sendRedirect(req.getContextPath() + "/secretaire/enregistrement?id=" + epId.getIdEvenement());
    }
}
