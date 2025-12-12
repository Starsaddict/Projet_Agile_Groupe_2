package ctrl;

import model.Utilisateur;
import repo.utilisateurRepo;
import service.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class secretaireResetMdpCtrl extends HttpServlet {

    private static utilisateurRepo utilisateurRepo = new utilisateurRepo();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("[secretaireResetMdpCtrl] Début de la réinitialisation");
        String idParam = request.getParameter("idUtilisateur");
        String successMessage = null;
        String errorMessage = null;

        if (idParam == null || idParam.trim().isEmpty()) {
            System.out.println("[secretaireResetMdpCtrl] idUtilisateur manquant");
            errorMessage = "Identifiant manquant.";
        } else {
            String trimmedId = idParam.trim();
            try {
                long id = Long.parseLong(trimmedId);
                System.out.println("[secretaireResetMdpCtrl] idUtilisateur=" + id);
                if (id <= 0) {
                    System.out.println("[secretaireResetMdpCtrl] idUtilisateur invalide (<=0)");
                    errorMessage = "Identifiant invalide.";
                } else {
                    UtilisateurService utilisateurService = new UtilisateurService();
                    boolean resetOk = utilisateurService.resetPassword(id);
                    System.out.println("[secretaireResetMdpCtrl] resetPasswordWithEmailPrefix=" + resetOk);
                    if (resetOk) {
                        successMessage = "Mot de passe réinitialisé.";
                    } else {
                        errorMessage = "Impossible de réinitialiser le mot de passe.";
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("[secretaireResetMdpCtrl] NumberFormatException: " + idParam);
                errorMessage = "Identifiant invalide.";
            } catch (Exception e) {
                System.out.println("[secretaireResetMdpCtrl] Erreur inattendue: " + e.getMessage());
                e.printStackTrace();
                errorMessage = "Une erreur est survenue lors de la réinitialisation.";
            }
        }

        List<Utilisateur> utilisateurs;
        try {
            utilisateurs = utilisateurRepo.loadAllUtilisateurs();
            System.out.println("[secretaireResetMdpCtrl] Nombre d'utilisateurs chargés=" + utilisateurs.size());
        } catch (Exception e) {
            System.out.println("[secretaireResetMdpCtrl] Impossible de charger les comptes: " + e.getMessage());
            e.printStackTrace();
            utilisateurs = Collections.emptyList();
            if (errorMessage == null) {
                errorMessage = "Impossible de charger les comptes.";
            }
        }

        if (successMessage != null) {
            request.setAttribute("success", successMessage);
        }
        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
        }
        request.setAttribute("utilisateurs", utilisateurs);
        request.getRequestDispatcher("/jsp/secretaire/modifier.jsp").forward(request, response);
    }
}
