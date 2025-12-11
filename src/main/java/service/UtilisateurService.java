package service;

import model.*;
import repo.utilisateurRepo;
import repository.UtilisateurRepositoryImpl;
import util.emailUtil;
import util.mdpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilisateurService {

    private final UtilisateurRepositoryImpl repo;

    public UtilisateurService(UtilisateurRepositoryImpl repo) {
        this.repo = repo;
    }

    public UtilisateurService() {
        this.repo = new UtilisateurRepositoryImpl();
    }

    /**
     * Authentifie un utilisateur par email, mot de passe et rôle.
     * - Cherche l'utilisateur via le repository.
     * - Si le hash stocké ressemble à un hash BCrypt, utilise BCrypt.checkpw.
     * - Sinon, tente une comparaison avec le hash SHA-256 via util.mdpUtil (fallback).
     */
    public Optional<Utilisateur> authenticate(String email, String password, String role) {
        if (email == null || password == null) {
            return Optional.empty();
        }

        Optional<Utilisateur> opt = repo.findByEmailUtilisateur(email, role);
        if (!opt.isPresent()) {
            return Optional.empty();
        }

        Utilisateur u = opt.get();
        String storedHash = u.getMdpUtilisateur();
        if (storedHash == null) {
            return Optional.empty();
        }

        try {
            //String hashedInput = mdpUtil.mdpString(password);
            String hashedInput = password;
            if (hashedInput != null && hashedInput.equals(storedHash)) {
                return Optional.of(u);
            }
        } catch (Exception e) {
            // A gérer
        }

        return Optional.empty();
    }

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
            case "Joueur":
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

    public void setFamily(List<Parent> parents, List<Joueur> joueurs) {
        if (parents == null || joueurs == null) {
            return;
        }

        for (Parent parent : parents) {
            List<Joueur> parentJoueurs = parent.getJoueurs();
            if (parentJoueurs == null) {
                parentJoueurs = new ArrayList<>();
                parent.setJoueurs(parentJoueurs);
            }
            for (Joueur joueur : joueurs) {
                if (!parentJoueurs.contains(joueur)) {
                    parentJoueurs.add(joueur);
                }
            }
        }

        for (Joueur joueur : joueurs) {
            List<Parent> joueurParents = joueur.getParents();
            if (joueurParents == null) {
                joueurParents = new ArrayList<>();
                joueur.setParents(joueurParents);
            }
            for (Parent parent : parents) {
                if (!joueurParents.contains(parent)) {
                    joueurParents.add(parent);
                }
            }
        }

        utilisateurRepo utilisateurRepo = new utilisateurRepo();
        for (Parent parent : parents) {
            utilisateurRepo.updateUtilisateur(parent);
        }
        for (Joueur joueur : joueurs) {
            utilisateurRepo.updateUtilisateur(joueur);
        }
    }

}
