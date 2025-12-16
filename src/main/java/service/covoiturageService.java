package service;

import model.Covoiturage;
import model.Utilisateur;
import repo.covoiturageRepo;

import java.util.List;

public class covoiturageService {

    private final covoiturageRepo repo = new covoiturageRepo();

    // -------------------- CREATE --------------------
    public void creerCovoiturage(Covoiturage c) {
        repo.save(c);
    }

    // -------------------- READ --------------------
    // Trouver par événement
    public List<Covoiturage> findByEvenement(Long idEvenement) {
        return repo.findByEvenement(idEvenement);
    }

    // Récupérer tous les covoiturages avec les collections fetchées pour JSP
    public List<Covoiturage> findAllCovoiturages() {
        return repo.findAllWithReservations();
    }

    // -------------------- UPDATE --------------------
    // Réserver une place dans un covoiturage
    public void reserver(Long idCovoiturage, Utilisateur utilisateur) {
        // On utilise la méthode fetch pour éviter LazyInitializationException
        Covoiturage c = repo.findByIdWithReservations(idCovoiturage);
        if (c == null) {
            throw new IllegalArgumentException("Covoiturage introuvable");
        }

        c.addReservation(utilisateur);
        repo.update(c);
    }

    // -------------------- DELETE --------------------
    // Supprimer un covoiturage si l'utilisateur est le conducteur
    public void supprimer(Long idCovoiturage, Utilisateur user) {
        Covoiturage c = repo.findByIdWithReservations(idCovoiturage);
        if (c == null) {
            throw new IllegalArgumentException("Covoiturage introuvable");
        }

        if (!c.getConducteur().equals(user)) {
            throw new SecurityException("Suppression non autorisée");
        }

        repo.delete(c);
    }
    
    public void quitter(Long idCovoiturage, Utilisateur utilisateur) {
        Covoiturage c = repo.findByIdWithReservations(idCovoiturage);
        if (c == null) {
            throw new IllegalArgumentException("Covoiturage introuvable");
        }

        c.removeReservation(utilisateur);
        repo.update(c);
    }
    
}
