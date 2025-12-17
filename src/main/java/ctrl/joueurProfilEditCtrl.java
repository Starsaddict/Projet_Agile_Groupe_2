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

public class joueurProfilEditCtrl extends HttpServlet {

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

        request.setAttribute("utilisateur", joueur);
        request.getRequestDispatcher("/jsp/joueur/profilEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");

        joueur.setAdresseUtilisateur(adresse);
        joueur.setTelephoneUtilisateur(telephone);

        boolean ok = utilisateurRepo.updateUtilisateur(joueur);
        if (!ok) {
            response.sendRedirect(request.getContextPath() + "/joueur/profil?error=Erreur+mise+a+jour");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/joueur/profil?success=1");
    }
}
