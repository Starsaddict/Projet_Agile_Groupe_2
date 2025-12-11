package ctrl;

import model.Joueur;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Parent;
import repo.utilisateurRepo;
import repository.UtilisateurRepositoryImpl;
import service.UtilisateurService;

public class secretaireRequestCreerCompteFamile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/jsp/secretaire/creerCompte.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] joueurNom = request.getParameterValues("joueur_nom[]");
        String[] joueurPrenom = request.getParameterValues("joueur_prenom[]");
        String[] joueurEmail = request.getParameterValues("joueur_email[]");

        String[] parentNom = request.getParameterValues("parent_nom[]");
        String[] parentPrenom = request.getParameterValues("parent_prenom[]");
        String[] parentEmail = request.getParameterValues("parent_email[]");

        if (joueurNom == null || joueurPrenom == null || joueurEmail == null
                || parentNom == null || parentPrenom == null || parentEmail == null) {
            redirectWithError(request, response, "Parametres manquants");
            return;
        }

        if (joueurNom.length != joueurPrenom.length || joueurNom.length != joueurEmail.length
                || parentNom.length != parentPrenom.length || parentNom.length != parentEmail.length) {
            redirectWithError(request, response, "Parametres invalides");
            return;
        }

        if (joueurNom.length == 0 || parentNom.length == 0) {
            redirectWithError(request, response, "Au moins un parent et un joueur sont requis");
            return;
        }

        utilisateurRepo utilisateurRepo = new utilisateurRepo();

        Set<String> emails = new HashSet<>();
        for (String email : joueurEmail) {
            if (email == null || email.isEmpty() || !emails.add(email)) {
                redirectWithError(request, response, "Email joueur invalide ou duplique");
                return;
            }
            if (utilisateurRepo.emailExists(email)) {
                redirectWithError(request, response, "Email deja utilise: " + email);
                return;
            }
        }
        for (String email : parentEmail) {
            if (email == null || email.isEmpty() || !emails.add(email)) {
                redirectWithError(request, response, "Email parent invalide ou duplique");
                return;
            }
            if (utilisateurRepo.emailExists(email)) {
                redirectWithError(request, response, "Email deja utilise: " + email);
                return;
            }
        }

        UtilisateurService utilisateurService = new UtilisateurService();

        List<Joueur> joueurs = new ArrayList<>();
        for (int i = 0; i < joueurNom.length; i++) {
            Joueur joueur = (Joueur) utilisateurService.creerCompteUtilisateur(joueurEmail[i], "Joueur");
            if (joueur != null) {
                joueur.setNomUtilisateur(joueurNom[i]);
                joueur.setPrenomUtilisateur(joueurPrenom[i]);
                joueurs.add(joueur);
            }
        }

        List<Parent> parents = new ArrayList<>();
        for (int i = 0; i < parentNom.length; i++) {
            Parent parent = (Parent) utilisateurService.creerCompteUtilisateur(parentEmail[i], "Parent");
            if (parent != null) {
                parent.setNomUtilisateur(parentNom[i]);
                parent.setPrenomUtilisateur(parentPrenom[i]);
                parents.add(parent);
            }
        }

        if (joueurs.size() != joueurNom.length || parents.size() != parentNom.length) {
            redirectWithError(request, response, "Creation de compte echouee pour certains membres");
            return;
        }

        utilisateurService.setFamily(parents, joueurs);
        response.sendRedirect(request.getContextPath() + "/secretaire/profil?success=1");
    }

    private void redirectWithError(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        String encodedMessage = URLEncoder.encode(message, "UTF-8");
        response.sendRedirect(request.getContextPath() + "/secretaire/profil/creerCompte?error=" + encodedMessage);
    }
}
