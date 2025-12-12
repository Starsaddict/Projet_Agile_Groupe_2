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

        if ("create".equals(action)) {
            create(request);
        } else if ("update".equals(action)) {
            update(request);
        } else if ("delete".equals(action)) {
            delete(request);
        }

        response.sendRedirect(request.getContextPath() + "/evenementSecre");
    }

    private void create(HttpServletRequest r) {
        Evenement e = new Evenement();
        e.setNomEvenement(r.getParameter("nom"));
        e.setLieuEvenement(r.getParameter("lieu"));
        e.setTypeEvenement(r.getParameter("type"));
        e.setDateEvenement(LocalDateTime.parse(r.getParameter("date")));
        e.setGroupe(null); // ⭐ Groupe optionnel
        repo.create(e);
    }

    private void update(HttpServletRequest r) {
        Long id = Long.parseLong(r.getParameter("id"));
        Evenement e = repo.findById(id);
        e.setNomEvenement(r.getParameter("nom"));
        e.setLieuEvenement(r.getParameter("lieu"));
        e.setTypeEvenement(r.getParameter("type"));
        e.setDateEvenement(LocalDateTime.parse(r.getParameter("date")));
        e.setGroupe(null); // ⭐ Toujours optionnel
        repo.update(e);
    }

    private void delete(HttpServletRequest r) {
    	Long id = Long.parseLong(r.getParameter("id"));
    	Evenement e = repo.findById(id);
        repo.delete(e);
    }
}
