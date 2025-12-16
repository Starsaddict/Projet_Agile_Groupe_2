package ctrl;

import model.Utilisateur;
import repo.utilisateurRepo;
import service.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/resetPassword")
public class ResetPasswordCtrl extends HttpServlet {

    private final UtilisateurService utilisateurService = new UtilisateurService();
    private final utilisateurRepo utilisateurRepo = new utilisateurRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uidParam = request.getParameter("uid");
        Utilisateur utilisateur = null;
        String error = null;

        if (uidParam == null || uidParam.trim().isEmpty()) {
            error = "Identifiant manquant.";
        } else {
            try {
                Long id = Long.parseLong(uidParam.trim());
                utilisateur = utilisateurService.loadUtilisateur(id);
                if (utilisateur == null) {
                    error = "Utilisateur introuvable.";
                }
            } catch (NumberFormatException e) {
                error = "Identifiant invalide.";
            }
        }

        request.setAttribute("uid", uidParam);
        request.setAttribute("utilisateur", utilisateur);
        request.setAttribute("error", error);
        request.getRequestDispatcher("/jsp/resetPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uidParam = request.getParameter("uid");
        String newPassword = request.getParameter("newPassword");
        String error = null;
        Utilisateur utilisateur = null;

        if (uidParam == null || uidParam.trim().isEmpty()) {
            error = "Identifiant manquant.";
        } else {
            try {
                Long id = Long.parseLong(uidParam.trim());
                utilisateur = utilisateurService.loadUtilisateur(id);
                if (utilisateur == null) {
                    error = "Utilisateur introuvable.";
                }
            } catch (NumberFormatException e) {
                error = "Identifiant invalide.";
            }
        }

        if (error == null) {
            if (newPassword == null || newPassword.trim().isEmpty()) {
                error = "Nouveau mot de passe requis.";
            } else {
                Boolean updated = utilisateurService.modifierUtilisateurMdp(utilisateur, newPassword);
                System.out.println(updated);
                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/jsp/requestPasswordSuccess.jsp");
                    return;
                } else {
                    error = "Impossible de mettre à jour le mot de passe.";
                }
            }
        }

        request.setAttribute("uid", uidParam);
        request.setAttribute("utilisateur", utilisateur);
        request.setAttribute("error", error);
        request.getRequestDispatcher("/jsp/resetPassword.jsp").forward(request, response);
    }
}
