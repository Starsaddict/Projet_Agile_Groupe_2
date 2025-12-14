package service;

import model.*;
import repo.utilisateurRepo;
import repository.UtilisateurRepositoryImpl;
import util.emailUtil;
import util.mdpUtil;

import java.util.ArrayList;
import java.util.Collections;
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

    public static class AuthenticatedUser {
        private final Utilisateur user;
        private final List<String> roles;

        public AuthenticatedUser(Utilisateur user, List<String> roles) {
            this.user = user;
            this.roles = roles;
        }

        public Utilisateur getUser() {
            return user;
        }

        public List<String> getRoles() {
            return roles;
        }
    }

    /**
     * Authentifie un utilisateur par email / mot de passe, et renvoie ses rôles disponibles.
     */
    public Optional<AuthenticatedUser> authenticate(String email, String password) {
        if (email == null || password == null) {
            return Optional.empty();
        }

        Optional<Utilisateur> opt = repo.findFirstByEmail(email);
        if (!opt.isPresent()) {
            return Optional.empty();
        }

        Utilisateur u = opt.get();
        String storedHash = u.getMdpUtilisateur();
        if (storedHash == null) {
            return Optional.empty();
        }

        try {
            // String hashedInput = mdpUtil.mdpString(password);
            String hashedInput = password;
            if (hashedInput != null && hashedInput.equals(storedHash)) {
                List<String> roles = repo.findRolesByEmail(email);
                if (roles == null || roles.isEmpty()) {
                    roles = u.getTypeU() != null
                            ? Collections.singletonList(u.getTypeU())
                            : Collections.emptyList();
                }
                return Optional.of(new AuthenticatedUser(u, roles));
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
     * Charge un parent par email (utile si l'utilisateur courant est connecté
     * sous un autre rôle mais possède aussi le rôle Parent).
     */
    public Parent loadParentByEmail(String email) {
        if (email == null) {
            return null;
        }
        Optional<Utilisateur> opt = repo.findByEmailUtilisateur(email, "Parent");
        if (opt.isPresent() && opt.get() instanceof Parent) {
            Parent parent = (Parent) opt.get();
            // Recharge via utilisateurRepo pour initialiser les collections
            if (parent.getIdUtilisateur() != null) {
                return utilisateurRepo.loadParent(parent.getIdUtilisateur());
            }
            return parent;
        }
        return null;
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
        String password = mdpUtil.mdpString(mdp);
        u.setMdpUtilisateur(mdp);
        utilisateurRepo.updateUtilisateur(u);
    }

    public Utilisateur creerCompteUtilisateur(String email, String type) {
        if (!type.equals("Coach") && !type.equals("Joueur") && !type.equals("Parent") && !type.equals("Secretaire")) {
            return null;
        }

        if (!emailUtil.isValidEmail(email)) {
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
                return creerCompteCoach(email, mdp);
            case "Joueur":
                return creerCompteJoueur(email, mdp);
            case "Parent":
                return creerCompteParent(email, mdp);
            case "Secretaire":
                return creerCompteSecretaire(email, mdp);
        }

        return null;

    }

    public Secretaire creerCompteSecretaire(String email, String mdp) {
        Secretaire secretaire = new Secretaire();
        if (emailUtil.isValidEmail(email)) {
            secretaire.setEmailUtilisateur(email);
            String password = mdpUtil.mdpString(mdp);
            secretaire.setMdpUtilisateur(password);

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

            joueur = (Joueur) utilisateurRepo.saveUtilisateur(joueur);
            return joueur;
        }
        return null;
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
