package ctrl;

import model.Utilisateur;
import repo.utilisateurRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class modifierPageProfiCtrl extends HttpServlet {

    private utilisateurRepo utilisateurRepo = new utilisateurRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        try {
            Long id = Long.parseLong(idStr);
            Utilisateur utilisateur = utilisateurRepo.loadUtilisateur(id);

            if (utilisateur == null) {
                response.sendRedirect(
                        request.getContextPath() + "/secretaire/profil/liste?error=Utilisateur non trouvé");
                return;
            }

            // Récupérer tous les utilisateurs avec le même email pour afficher leurs rôles
            String email = utilisateur.getEmailUtilisateur();
            List<Utilisateur> utilisateursByEmail = utilisateurRepo.findByEmail(email);

            request.setAttribute("utilisateur", utilisateur);
            request.setAttribute("utilisateursByEmail", utilisateursByEmail);
            request.getRequestDispatcher("/jsp/secretaire/pageModifier.jsp").forward(request, response);

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/secretaire/profil/liste?error=Erreur");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Récupérer les paramètres
            Long id = Long.parseLong(request.getParameter("id"));
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");

            // Charger l'utilisateur principal
            Utilisateur utilisateur = utilisateurRepo.loadUtilisateur(id);

            if (utilisateur == null) {
                response.sendRedirect(
                        request.getContextPath() + "/secretaire/profil/modifier?error=Utilisateur non trouvé");
                return;
            }

            // Récupérer l'ancien email pour trouver tous les utilisateurs avec ce même
            // email
            String oldEmail = utilisateur.getEmailUtilisateur();

            // Vérifier l'email unique (sauf si c'est le même email)
            if (!email.equals(oldEmail)) {
                if (utilisateurRepo.emailExists(email)) {
                    request.setAttribute("error", "Cet email est déjà utilisé");
                    request.setAttribute("utilisateur", utilisateur);
                    request.getRequestDispatcher("/jsp/secretaire/pageModifier.jsp").forward(request, response);
                    return;
                }
            }

            // Récupérer tous les utilisateurs avec le même ancien email
            List<Utilisateur> utilisateursWithSameEmail = utilisateurRepo.findByEmail(oldEmail);

            // Mettre à jour tous les utilisateurs avec le même email
            for (Utilisateur user : utilisateursWithSameEmail) {
                user.setNomUtilisateur(nom);
                user.setPrenomUtilisateur(prenom);
                user.setEmailUtilisateur(email);
                utilisateurRepo.updateUtilisateur(user);
            }

            // Rediriger vers la page modifier avec succès
            response.sendRedirect(request.getContextPath() + "/secretaire/profil/modifier?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/secretaire/profil/modifier?error=Erreur");
        }
    }
}