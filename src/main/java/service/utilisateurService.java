package service;

import model.*;
import repo.utilisateurRepo;
import util.emailUtil;
import util.mdpUtil;

import java.util.List;

public class utilisateurService {

    public Utilisateur creerCompteUtilisateur(String email, String type) {
        if(!type.equals("Coach") && !type.equals("Joueur") && !type.equals("Parent") && !type.equals("Secretaire")) {
            return null;
        }

        if(!emailUtil.isValidEmail(email)) {
            return null;
        }

        String mdp;
        if (email.length() >= 6) {
            mdp = email.substring(0, 6);
        } else {
            mdp = email;
        }

        switch (type) {
            case "Coach":
                return creerCompteCoach(email,mdp);
            case "Jouer":
                return creerCompteJoueur(email,mdp);
            case "Parent":
                return creerCompteParent(email,mdp);
            case "Secretaire":
                return creerCompteSecretaire(email,mdp);
        }

        return null;

    }

    public Secretaire creerCompteSecretaire(String email, String mdp) {
        Secretaire secretaire = new Secretaire();
        if (emailUtil.isValidEmail(email)) {
            secretaire.setEmailUtilisateur(email);
            String password = mdpUtil.mdpString(mdp);
            secretaire.setMdpUtilisateur(password);

            utilisateurRepo utilisateurRepo = new utilisateurRepo();
            secretaire = (Secretaire) utilisateurRepo.saveUtilisateur(secretaire);
            return secretaire;
        }

        return null;
    }

    public Coach creerCompteCoach(String email, String mdp) {
        Coach coach = new Coach();
        if (emailUtil.isValidEmail(email)) {
            coach.setEmailUtilisateur(email);

            String password = mdpUtil.mdpString(mdp);
            coach.setMdpUtilisateur(password);

            utilisateurRepo utilisateurRepo = new utilisateurRepo();
            coach = (Coach) utilisateurRepo.saveUtilisateur(coach);
            return coach;
        }
        return null;
    }

    public Parent creerCompteParent(String email, String mdp) {
        Parent parent = new Parent();
        if (emailUtil.isValidEmail(email)) {
            parent.setEmailUtilisateur(email);

            String password = mdpUtil.mdpString(mdp);
            parent.setMdpUtilisateur(password);

            utilisateurRepo utilisateurRepo = new utilisateurRepo();
            parent = (Parent) utilisateurRepo.saveUtilisateur(parent);
            return parent;
        }
        return null;
    }

    public Joueur creerCompteJoueur(String email, String mdp) {
        Joueur joueur = new Joueur();
        if (emailUtil.isValidEmail(email)) {
            joueur.setEmailUtilisateur(email);
            String password = mdpUtil.mdpString(mdp);
            joueur.setMdpUtilisateur(password);

            utilisateurRepo utilisateurRepo = new utilisateurRepo();
            joueur = (Joueur) utilisateurRepo.saveUtilisateur(joueur);
            return joueur;
        }
        return null;
    }

}
