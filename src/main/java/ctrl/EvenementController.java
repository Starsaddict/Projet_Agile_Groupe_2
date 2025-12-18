package ctrl;

import model.Evenement;
import repo.EvenementRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/evenementSecre")
public class EvenementController extends HttpServlet {

    private final EvenementRepo repo = new EvenementRepo();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("evenements", repo.findAll());
        req.getRequestDispatcher("/jsp/evenementSecre.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if ("create".equals(action)) create(req);
        else if ("update".equals(action)) update(req);
        else if ("delete".equals(action)) delete(req);

        resp.sendRedirect(req.getContextPath() + "/evenementSecre");
    }


    /* ================= CREATE ================= */
    private void create(HttpServletRequest r) {
        Evenement e = new Evenement();
        e.setNomEvenement(r.getParameter("nom"));
        e.setLieuEvenement(r.getParameter("lieu"));
        e.setTypeEvenement(r.getParameter("type"));
        e.setDateEvenement(LocalDateTime.parse(r.getParameter("date")));
        repo.create(e);
    }

    /* ================= UPDATE ================= */
    private void update(HttpServletRequest r) {
        Long id = Long.parseLong(r.getParameter("id"));
        Evenement e = repo.findById(id);
        if (e != null) {
            e.setNomEvenement(r.getParameter("nom"));
            e.setLieuEvenement(r.getParameter("lieu"));
            e.setTypeEvenement(r.getParameter("type"));
            e.setDateEvenement(LocalDateTime.parse(r.getParameter("date")));
            repo.update(e);
        }
    }

    /* ================= DELETE (SECURISÉE) ================= */
    private void delete(HttpServletRequest r) {
        try {
            String idParam = r.getParameter("id");

            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                Evenement e = repo.findById(id);

                // Suppression seulement si l'événement existe
                if (e != null) {
                    repo.delete(e);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
