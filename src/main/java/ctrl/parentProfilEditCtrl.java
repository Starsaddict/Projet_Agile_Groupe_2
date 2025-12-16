package ctrl;

import model.Parent;
import model.Utilisateur;
import repo.utilisateurRepo;
import service.UtilisateurService;
import repository.UtilisateurRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class parentProfilEditCtrl extends HttpServlet {

    private UtilisateurService utilisateurService = new UtilisateurService(new UtilisateurRepositoryImpl());
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

            // Recharger le parent avec ses collections initialisées
            Parent parent = utilisateurService.loadParentWithCollections(loginUser.getIdUtilisateur());
            if (parent == null) {
                request.setAttribute("error", "Parent non trouvé");
                request.getRequestDispatcher("/jsp/parent/profil.jsp").forward(request, response);
                return;
            }

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

            Long id = Long.parseLong(request.getParameter("id"));
            String adresse = request.getParameter("adresse");
            String telephone = request.getParameter("telephone");

            Utilisateur utilisateur = utilisateurRepo.loadUtilisateur(id);

            if (utilisateur == null) {
                response.sendRedirect(request.getContextPath() + "/parent/profil?error=Utilisateur non trouvé");
                return;
            }

            // Recharger le parent avec ses collections initialisées pour la vérification
            // d'accès
            Parent parent = utilisateurService.loadParentWithCollections(loginUser.getIdUtilisateur());
            if (parent == null) {
                response.sendRedirect(request.getContextPath() + "/parent/profil?error=Parent non trouvé");
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

            // Mettre à jour le profil de base
            utilisateur.setAdresseUtilisateur(adresse);
            utilisateur.setTelephoneUtilisateur(telephone);
            utilisateur.setProfil(true);
            Boolean updateSuccess = utilisateurRepo.updateUtilisateur(utilisateur);

            if (!updateSuccess) {
                response.sendRedirect(
                        request.getContextPath() + "/parent/profil?error=Erreur lors de la mise à jour du profil");
                return;
            }

            // Rediriger vers la page de profil avec succès
            response.sendRedirect(request.getContextPath() + "/parent/profil?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/parent/profil?error=Erreur");
        }
    }
}
