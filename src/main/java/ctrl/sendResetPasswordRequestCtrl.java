package ctrl;

import jakarta.mail.MessagingException;
import model.Utilisateur;
import service.UtilisateurService;
import util.SendEmailSSL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/secretaire/profil/sendResetPasswordRequest")
public class sendResetPasswordRequestCtrl extends HttpServlet {

    private static final UtilisateurService utilisateurService = new UtilisateurService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String error = null;
        String success = null;
        Long userId = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                userId = Long.parseLong(idParam.trim());
                if (userId <= 0) {
                    error = "Identifiant invalide.";
                }
            } catch (NumberFormatException e) {
                error = "Identifiant invalide.";
            }
        }

        if (userId == null && error == null) {
            Object sessionId = request.getSession(false) != null
                    ? request.getSession(false).getAttribute("id")
                    : null;
            if (sessionId instanceof Number) {
                userId = ((Number) sessionId).longValue();
            }
        }

        if (userId == null && error == null) {
            error = "Identifiant manquant.";
        }

        if (error == null) {
            Utilisateur utilisateur = utilisateurService.loadUtilisateur(userId);
            if (utilisateur == null) {
                error = "Utilisateur introuvable.";
            } else {
                SendEmailSSL mailer = new SendEmailSSL();
                try {
                    mailer.sendResetRequest(request, utilisateur);
                    success = "Demande de réinitialisation envoyée.";
                } catch (MessagingException e) {
                    error = "Impossible d'envoyer l'email de réinitialisation.";
                    e.printStackTrace();
                } catch (Exception e) {
                    error = "Une erreur inattendue est survenue.";
                    e.printStackTrace();
                }
            }
        }

        if (success != null) {
            request.setAttribute("success", success);
        }
        if (error != null) {
            request.setAttribute("error", error);
        }

        request.getRequestDispatcher("/secretaire/profil/modifier").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
