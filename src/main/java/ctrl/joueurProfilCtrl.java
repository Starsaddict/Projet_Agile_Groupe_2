package ctrl;

import model.Joueur;
import model.Utilisateur;
import repo.utilisateurRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class joueurProfilCtrl extends HttpServlet {

    private final utilisateurRepo utilisateurRepo = new utilisateurRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur loginUser = (Utilisateur) session.getAttribute("user");

        if (!(loginUser instanceof Joueur)) {
            response.sendRedirect(request.getContextPath() + "/Login?error=Acces+refuse");
            return;
        }

        Joueur joueur = (Joueur) utilisateurRepo.loadUtilisateur(loginUser.getIdUtilisateur());
        if (joueur == null) {
            response.sendRedirect(request.getContextPath() + "/Login?error=Joueur+introuvable");
            return;
        }

        request.setAttribute("joueur", joueur);
        request.getRequestDispatcher("/jsp/joueur/profil.jsp").forward(request, response);
    }
}
