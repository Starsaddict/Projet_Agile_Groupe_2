package ctrl;

import model.Joueur;
import model.Parent;
import repo.codeRepo;
import repo.utilisateurRepo;
import service.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class inscriptionProcessCtrl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/inscription?error=Code+manquant");
            return;
        }

        codeRepo codeRepo = new codeRepo();
        if (codeRepo.findByCode(code) == null) {
            response.sendRedirect(request.getContextPath() + "/inscription?error=Code+invalide");
            return;
        }

        String parentNom = request.getParameter("parent_nom");
        String parentPrenom = request.getParameter("parent_prenom");
        String parentEmail = request.getParameter("parent_email");

        String[] joueurNom = request.getParameterValues("joueur_nom[]");
        String[] joueurPrenom = request.getParameterValues("joueur_prenom[]");
        String[] joueurEmail = request.getParameterValues("joueur_email[]");

        if (parentNom == null || parentPrenom == null || parentEmail == null) {
            response.sendRedirect(
                    request.getContextPath() + "/inscription?code=" + code + "&error=Parametres+manquants");
            return;
        }

        utilisateurRepo utilisateurRepo = new utilisateurRepo();
        if (utilisateurRepo.emailExists(parentEmail)) {
            response.sendRedirect(request.getContextPath() + "/inscription?code=" + code + "&error=Email+deja+utilise");
            return;
        }

        UtilisateurService utilisateurService = new UtilisateurService();

        Parent parent = (Parent) utilisateurService.creerCompteUtilisateur(parentEmail, "Parent");
        if (parent == null) {
            response.sendRedirect(
                    request.getContextPath() + "/inscription?code=" + code + "&error=Erreur+creation+parent");
            return;
        }
        parent.setNomUtilisateur(parentNom);
        parent.setPrenomUtilisateur(parentPrenom);
        new repo.utilisateurRepo().updateUtilisateur(parent);

        List<Joueur> joueurs = new ArrayList<>();
        if (joueurNom != null && joueurPrenom != null) {
            for (int i = 0; i < joueurNom.length; i++) {
                String jNom = joueurNom[i];
                String jPrenom = joueurPrenom[i];
                String jEmail = (joueurEmail != null && i < joueurEmail.length) ? joueurEmail[i] : null;
                Joueur j = (Joueur) utilisateurService.creerCompteUtilisateur(jEmail, "Joueur");
                if (j != null) {
                    j.setNomUtilisateur(jNom);
                    j.setPrenomUtilisateur(jPrenom);
                    new repo.utilisateurRepo().updateUtilisateur(j);
                    joueurs.add(j);
                }
            }
        }

        // link family
        utilisateurService.setFamily(List.of(parent), joueurs);

        // consume code
        codeRepo.delete(code);

        response.sendRedirect(request.getContextPath() + "/Login?success=inscription");
    }
}
