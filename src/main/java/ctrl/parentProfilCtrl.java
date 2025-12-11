package ctrl;

import model.Joueur;
import model.Parent;
import model.Utilisateur;
import repo.utilisateurRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class parentProfilCtrl extends HttpServlet {

    private utilisateurRepo utilisateurRepo = new utilisateurRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

            if (utilisateur == null) {
                request.setAttribute("error", "Vous devez être connecté");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                return;
            }

            if (!(utilisateur instanceof Parent)) {
                request.setAttribute("error", "Accès réservé aux parents");
                request.getRequestDispatcher("/jsp/parent/profil.jsp").forward(request, response);
                return;
            }

            Parent parent = (Parent) utilisateur;

            // Créer une liste contenant le parent et ses enfants
            List<Utilisateur> profiles = new ArrayList<>();
            profiles.add(parent);

            // Ajouter les joueurs (enfants) du parent
            if (parent.getJoueurs() != null && !parent.getJoueurs().isEmpty()) {
                profiles.addAll(parent.getJoueurs());
            }

            request.setAttribute("parent", parent);
            request.setAttribute("profiles", profiles);
            request.getRequestDispatcher("/jsp/parent/profil.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement des profils: " + e.getMessage());
            try {
                request.getRequestDispatcher("/jsp/parent/profil.jsp").forward(request, response);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // POST 处理由专用的创建/修改控制器处理
        doGet(request, response);
    }
}
