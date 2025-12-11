package ctrl;

import model.Parent;
import model.Utilisateur;
import repo.utilisateurRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class parentProfilEditCtrl extends HttpServlet {

    private utilisateurRepo utilisateurRepo = new utilisateurRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        try {
            HttpSession session = request.getSession();
            Utilisateur loginUser = (Utilisateur) session.getAttribute("user");

            if (loginUser == null) {
                request.setAttribute("error", "Vous devez être connecté");
                request.getRequestDispatcher("/jsp/parent/profilEdit.jsp").forward(request, response);
                return;
            }

            if (!(loginUser instanceof Parent)) {
                request.setAttribute("error", "Accès réservé aux parents");
                request.getRequestDispatcher("/jsp/parent/profilEdit.jsp").forward(request, response);
                return;
            }

            Parent parent = (Parent) loginUser;

            Long id = Long.parseLong(idStr);
            Utilisateur utilisateur = utilisateurRepo.loadUtilisateur(id);

            if (utilisateur == null) {
                request.setAttribute("error", "Utilisateur non trouvé");
                request.getRequestDispatcher("/jsp/parent/profil.jsp").forward(request, response);
                return;
            }

            // Vérifier que l'utilisateur à éditer est soit le parent lui-même, soit un
            // enfant du parent
            if (!parent.getIdUtilisateur().equals(utilisateur.getIdUtilisateur())) {
                boolean isChild = parent.getJoueurs() != null && parent.getJoueurs().stream()
                        .anyMatch(j -> j.getIdUtilisateur().equals(utilisateur.getIdUtilisateur()));
                if (!isChild) {
                    request.setAttribute("error", "Accès refusé");
                    request.getRequestDispatcher("/jsp/parent/profil.jsp").forward(request, response);
                    return;
                }
            }

            request.setAttribute("utilisateur", utilisateur);
            request.getRequestDispatcher("/jsp/parent/profilEdit.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur: " + e.getMessage());
            request.getRequestDispatcher("/jsp/parent/profil.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            Utilisateur loginUser = (Utilisateur) session.getAttribute("user");

            if (loginUser == null) {
                response.sendRedirect(request.getContextPath() + "/parent/profil?error=Vous devez être connecté");
                return;
            }

            if (!(loginUser instanceof Parent)) {
                response.sendRedirect(request.getContextPath() + "/parent/profil?error=Accès refusé");
                return;
            }

            Parent parent = (Parent) loginUser;

            Long id = Long.parseLong(request.getParameter("id"));
            String description = request.getParameter("description");

            Utilisateur utilisateur = utilisateurRepo.loadUtilisateur(id);

            if (utilisateur == null) {
                response.sendRedirect(request.getContextPath() + "/parent/profil?error=Utilisateur non trouvé");
                return;
            }

            // Vérifier que l'utilisateur à éditer est soit le parent lui-même, soit un
            // enfant du parent
            if (!parent.getIdUtilisateur().equals(utilisateur.getIdUtilisateur())) {
                boolean isChild = parent.getJoueurs() != null && parent.getJoueurs().stream()
                        .anyMatch(j -> j.getIdUtilisateur().equals(utilisateur.getIdUtilisateur()));
                if (!isChild) {
                    response.sendRedirect(request.getContextPath() + "/parent/profil?error=Accès refusé");
                    return;
                }
            }

            // Mettre à jour la description
            utilisateur.setDescription(description);
            utilisateurRepo.updateUtilisateur(utilisateur);

            // Rediriger vers la page de profil avec succès
            response.sendRedirect(request.getContextPath() + "/parent/profil?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/parent/profil?error=Erreur");
        }
    }
}
