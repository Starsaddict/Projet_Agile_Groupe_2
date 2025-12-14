package service;

import model.*;
import repo.utilisateurRepo;
import repository.UtilisateurRepositoryImpl;
import util.emailUtil;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class UtilisateurService {

    private final UtilisateurRepositoryImpl repo;
    private static final String DEFAULT_PROFILE_PIC_ROUTE = "/img/joueur_avatar/default.png";
    private static final int NUMERO_JOUEUR_ATTEMPTS = 20;

    public UtilisateurService(UtilisateurRepositoryImpl repo) {
        this.repo = repo;
    }

    public UtilisateurService() {
        this.repo = new UtilisateurRepositoryImpl();
    }

    /**
     * Génère un NumeroJoueur unique pour l'année courante avec la règle :
     * J<yyyy>-<00000>. Vérifie en base pour éviter les collisions.
     */
    public String generateNumeroJoueur() {
        String yearPart = String.valueOf(Year.now().getValue());

        for (int attempt = 0; attempt < NUMERO_JOUEUR_ATTEMPTS; attempt++) {
            String candidate = "J" + yearPart + "-" + String.format("%05d",
                    ThreadLocalRandom.current().nextInt(0, 100_000));
            if (!utilisateurRepo.numeroJoueurExists(candidate)) {
                return candidate;
            }
        }

        throw new IllegalStateException("Impossible de generer un NumeroJoueur unique apres plusieurs essais");
    }

    private void assignNumeroJoueurIfMissing(Joueur joueur) {
        if (joueur != null && (joueur.getNumeroJoueur() == null || joueur.getNumeroJoueur().isEmpty())) {
            joueur.setNumeroJoueur(generateNumeroJoueur());
        }
    }

    /**
     * Authentifie un utilisateur par email, mot de passe et rôle.
     * - Cherche l'utilisateur via le repository.
     * - Si le hash stocké ressemble à un hash BCrypt, utilise BCrypt.checkpw.
     * - Sinon, tente une comparaison avec le hash SHA-256 via util.mdpUtil
     * (fallback).
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
            String hashedInput = password;
            if (hashedInput != null && hashedInput.equals(storedHash)) {
                return Optional.of(u);
            }
        } catch (Exception e) {
            // A gérer
        }

        return Optional.empty();
    }

    public static utilisateurRepo utilisateurRepo = new utilisateurRepo();

    public Joueur loadJoueur(Long id) {
        Utilisateur u = utilisateurRepo.loadUtilisateur(id);
        if (u instanceof Joueur) {
            return (Joueur) u;
        }
        return null;
    }

    public Parent loadParent(Long id) {
        Utilisateur u = utilisateurRepo.loadUtilisateur(id);
        if (u instanceof Parent) {
            return (Parent) u;
        }
        return null;
    }

    /**
     * 加载 Parent 并初始化其所有集合，避免 LazyInitializationException
     * 用于在 detached 对象上访问集合时使用
     */
    public Parent loadParentWithCollections(Long id) {
        Parent parent = loadParent(id);
        if (parent != null && parent.getJoueurs() != null) {
            parent.getJoueurs().size(); // Force initialization
        }
        return parent;
    }

    /**
     * 获取所有用户
     */
    public List<Utilisateur> loadAllUtilisateurs() {
        try {
            return utilisateurRepo.loadAllUtilisateurs();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void modifierUtilisateurNom(Utilisateur u, String nom) {
        u.setNomUtilisateur(nom);
        utilisateurRepo.updateUtilisateur(u);
    }

    public void modifierUtilisateurPrenom(Utilisateur u, String prenom) {
        u.setPrenomUtilisateur(prenom);
        utilisateurRepo.updateUtilisateur(u);
    }

    public void modifierUtilisateurDateNaissance(Utilisateur u, String dateNaissance) {
        u.setDateNaissanceUtilisateur(java.time.LocalDate.parse(dateNaissance));
        utilisateurRepo.updateUtilisateur(u);
    }

    public void modifierUtilisateurMdp(Utilisateur u, String mdp) {
        u.setMdpUtilisateur(mdp);
        utilisateurRepo.updateUtilisateur(u);
    }

    public Utilisateur creerCompteUtilisateur(String email, String type) {
        if (!type.equals("Coach") && !type.equals("Joueur") && !type.equals("Parent") && !type.equals("Secretaire")) {
            return null;
        }

        boolean emailValide = emailUtil.isValidEmail(email);
        if (!emailValide && !type.equals("Joueur")) {
            return null;
        }

        String mdp = "";
        if (emailValide) {
            if (email.length() >= 6) {
                mdp = email.substring(0, 6);
            } else {
                mdp = email;
            }
        }

        switch (type) {
            case "Coach":
                return emailValide ? creerCompteCoach(email, mdp) : null;
            case "Joueur":
                return creerCompteJoueur(emailValide ? email : null, mdp);
            case "Parent":
                return emailValide ? creerCompteParent(email, mdp) : null;
            case "Secretaire":
                return emailValide ? creerCompteSecretaire(email, mdp) : null;
        }

        return null;

    }

    public Secretaire creerCompteSecretaire(String email, String mdp) {
        Secretaire secretaire = new Secretaire();
        if (emailUtil.isValidEmail(email)) {
            secretaire.setEmailUtilisateur(email);
            secretaire.setMdpUtilisateur(mdp);

            secretaire = (Secretaire) utilisateurRepo.saveUtilisateur(secretaire);
            return secretaire;
        }

        return null;
    }

    public Coach creerCompteCoach(String email, String mdp) {
        Coach coach = new Coach();
        if (emailUtil.isValidEmail(email)) {
            coach.setEmailUtilisateur(email);
            coach.setMdpUtilisateur(mdp);

            coach = (Coach) utilisateurRepo.saveUtilisateur(coach);
            return coach;
        }
        return null;
    }

    public Parent creerCompteParent(String email, String mdp) {
        Parent parent = new Parent();
        if (emailUtil.isValidEmail(email)) {
            parent.setEmailUtilisateur(email);

            parent.setMdpUtilisateur(mdp);

            parent = (Parent) utilisateurRepo.saveUtilisateur(parent);
            return parent;
        }
        return null;
    }

    public Joueur creerCompteJoueur(String email, String mdp) {
        Joueur joueur = new Joueur();
        if (emailUtil.isValidEmail(email)) {
            joueur.setEmailUtilisateur(email);
            joueur.setMdpUtilisateur(mdp);
        } else {
            joueur.setEmailUtilisateur(null);
            joueur.setMdpUtilisateur(mdp == null ? "" : mdp);
        }
        joueur.setProfilePicRoute(DEFAULT_PROFILE_PIC_ROUTE);
        assignNumeroJoueurIfMissing(joueur);
        // 前端调用：从用户详情接口获取 profilePicRoute 字段，直接作为 <img src={profilePicRoute}> 即可显示默认头像
        joueur = (Joueur) utilisateurRepo.saveUtilisateur(joueur);
        return joueur;
    }

    public void setFamily(List<Parent> parents, List<Joueur> joueurs) {
        if (parents == null || parents.isEmpty() || joueurs == null || joueurs.isEmpty()) {
            return;
        }

        List<Parent> managedParents = new ArrayList<>();
        for (Parent parent : parents) {
            Parent managedParent = resolveParent(parent);
            if (managedParent.getJoueurs() == null) {
                managedParent.setJoueurs(new ArrayList<>());
            }
            if (containsParent(managedParents, managedParent)) {
                managedParents.add(managedParent);
            }
        }

        List<Joueur> managedJoueurs = new ArrayList<>();
        for (Joueur joueur : joueurs) {
            Joueur managedJoueur = resolveJoueur(joueur);
            if (managedJoueur.getParents() == null) {
                managedJoueur.setParents(new ArrayList<>());
            }
            if (containsJoueur(managedJoueurs, managedJoueur)) {
                managedJoueurs.add(managedJoueur);
            }
        }

        for (Parent parent : managedParents) {
            List<Joueur> parentJoueurs = parent.getJoueurs();
            for (Joueur joueur : managedJoueurs) {
                if (containsJoueur(parentJoueurs, joueur)) {
                    parentJoueurs.add(joueur);
                }

                List<Parent> joueurParents = joueur.getParents();
                if (containsParent(joueurParents, parent)) {
                    joueurParents.add(parent);
                }
            }
        }

        for (Parent parent : managedParents) {
            utilisateurRepo.updateUtilisateur(parent);
        }
        for (Joueur joueur : managedJoueurs) {
            utilisateurRepo.updateUtilisateur(joueur);
        }
    }

    private Parent resolveParent(Parent parent) {
        if (parent == null) {
            return null;
        }

        if (parent.getIdUtilisateur() != null) {
            Parent loadedParent = utilisateurRepo.loadParent(parent.getIdUtilisateur());
            if (loadedParent != null) {
                return loadedParent;
            }
        } else {
            parent = (Parent) utilisateurRepo.saveUtilisateur(parent);
        }
        return parent;
    }

    private Joueur resolveJoueur(Joueur joueur) {
        if (joueur == null) {
            return null;
        }

        if (joueur.getIdUtilisateur() != null) {
            Joueur loadedJoueur = loadJoueur(joueur.getIdUtilisateur());
            if (loadedJoueur != null) {
                return loadedJoueur;
            }
        } else {
            assignNumeroJoueurIfMissing(joueur);
            joueur = (Joueur) utilisateurRepo.saveUtilisateur(joueur);
        }
        return joueur;
    }

    private boolean containsParent(List<Parent> parents, Parent parent) {
        if (parents == null || parent == null) {
            return true;
        }
        Long id = parent.getIdUtilisateur();
        for (Parent p : parents) {
            if (p == parent) {
                return false;
            }
            if (id != null && id.equals(p.getIdUtilisateur())) {
                return false;
            }
        }
        return true;
    }

    private boolean containsJoueur(List<Joueur> joueurs, Joueur joueur) {
        if (joueurs == null || joueur == null) {
            return true;
        }
        Long id = joueur.getIdUtilisateur();
        for (Joueur j : joueurs) {
            if (j == joueur) {
                return false;
            }
            if (id != null && id.equals(j.getIdUtilisateur())) {
                return false;
            }
        }
        return true;
    }

    public Utilisateur loadUtilisateur(Long id) {
        return utilisateurRepo.loadUtilisateur(id);
    }

    public boolean resetPassword(long id) {
        Utilisateur u = utilisateurRepo.loadUtilisateur(id);
        if (u == null) {
            return false;
        }
        String mdp = u.getEmailUtilisateur().substring(0,5);
        u.setMdpUtilisateur(mdp);
        return utilisateurRepo.updateUtilisateur(u);
    }
}
