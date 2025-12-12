package ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Secretaire;
import model.Utilisateur;

public class secretaireProfilCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur loginUser = (Utilisateur) session.getAttribute("user");

        if (loginUser == null || !(loginUser instanceof Secretaire)) {
            request.setAttribute("error", "Accès réservé aux secrétaires");
        }

        request.getRequestDispatcher("/jsp/secretaire/profil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
