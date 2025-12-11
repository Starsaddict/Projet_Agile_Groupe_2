package ctrl;

import model.Evenement;
import repo.EvenementRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/evenementSecre")
public class EvenementController extends HttpServlet {

    private final EvenementRepo repo = new EvenementRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("evenements", repo.findAll());
        request.getRequestDispatcher("/jsp/evenementSecre.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "create":
                create(request);
                break;

            case "update":
                update(request);
                break;

            case "delete":
                delete(request);
                break;
        }

        response.sendRedirect("evenementSecre");
    }


    // -------------------------
    //     CRUD METHODS
    // -------------------------

    private void create(HttpServletRequest r) {
        Evenement e = map(r, new Evenement());
        repo.save(e);
    }

    private void update(HttpServletRequest r) {
        int id = Integer.parseInt(r.getParameter("id"));
        repo.findById(id).ifPresent(e -> {
            map(r, e);
            repo.save(e);
        });
    }

    private void delete(HttpServletRequest r) {
        int id = Integer.parseInt(r.getParameter("id"));
        repo.findById(id).ifPresent(repo::delete);
    }


    // -------------------------
    //  Mapping form → entity
    // -------------------------

    private Evenement map(HttpServletRequest r, Evenement e) {

        e.setNomEvenement(r.getParameter("nom"));
        e.setLieuEvenement(r.getParameter("lieu"));
        e.setTypeEvenement(r.getParameter("type"));

        // 🟦 CORRECTION : Gestion propre de la date (évite l'erreur 500)
        String dateStr = r.getParameter("date");

        if (dateStr == null || dateStr.isBlank()) {
            throw new IllegalArgumentException("La date est obligatoire.");
        }

        // ⚠ Le champ datetime-local doit donner un format comme : 2025-12-11T15:30
        LocalDateTime date = LocalDateTime.parse(dateStr);
        e.setDateEvenement(date);

        return e;
    }
}
