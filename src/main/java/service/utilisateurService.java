package service;

import model.*;
import repo.utilisateurRepo;
import util.emailUtil;
import util.mdpUtil;

public class utilisateurService {



    public Secretaire creerCompteSecretaire(String email, String mdp) {
        Secretaire secretaire = new Secretaire();
        emailUtil emailUtil = new emailUtil();
        if (emailUtil.isValidEmail(email)) {
            secretaire.setEmailUtilisateur(email);
            mdpUtil mdpUtil = new mdpUtil();
            String password = mdpUtil.mdpString(mdp);
            secretaire.setMdpUtilisateur(password);

            utilisateurRepo utilisateurRepo = new utilisateurRepo();
            secretaire = (Secretaire) utilisateurRepo.saveUtilisateur(secretaire);
            return secretaire;
        }

        return null;
    }

    public Coach crerCompteCoach(String email, String mdp) {
        Coach coach = new Coach();
        emailUtil emailUtil = new emailUtil();
        if (emailUtil.isValidEmail(email)) {
            coach.setEmailUtilisateur(email);
            mdpUtil mdpUtil = new mdpUtil();
            String password = mdpUtil.mdpString(mdp);
            coach.setMdpUtilisateur(password);

            utilisateurRepo utilisateurRepo = new utilisateurRepo();
            coach = (Coach) utilisateurRepo.saveUtilisateur(coach);
            return coach;
        }
        return null;
    }

    public Parent crerCompteParent(String email, String mdp) {
        Parent parent = new Parent();
        emailUtil emailUtil = new emailUtil();
        if (emailUtil.isValidEmail(email)) {
            parent.setEmailUtilisateur(email);
            mdpUtil mdpUtil = new mdpUtil();
            String password = mdpUtil.mdpString(mdp);
            parent.setMdpUtilisateur(password);

            utilisateurRepo utilisateurRepo = new utilisateurRepo();
            parent = (Parent) utilisateurRepo.saveUtilisateur(parent);
            return parent;
        }
        return null;
    }

    public Joueur crerCompteJoueur(String email, String mdp) {
        Joueur joueur = new Joueur();
        emailUtil emailUtil = new emailUtil();
        if (emailUtil.isValidEmail(email)) {
            joueur.setEmailUtilisateur(email);
            mdpUtil mdpUtil = new mdpUtil();
            String password = mdpUtil.mdpString(mdp);
            joueur.setMdpUtilisateur(password);

            utilisateurRepo utilisateurRepo = new utilisateurRepo();
            joueur = (Joueur) utilisateurRepo.saveUtilisateur(joueur);
            return joueur;
        }
        return null;
    }


}
