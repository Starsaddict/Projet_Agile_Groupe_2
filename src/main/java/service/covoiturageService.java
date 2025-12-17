package service;

import model.Covoiturage;
import model.Reservation;
import model.Utilisateur;
import repo.covoiturageRepo;

import java.util.List;

public class covoiturageService {

    private final covoiturageRepo repo = new covoiturageRepo();

    /* ================= CREATE ================= */

    public void creerCovoiturage(Covoiturage c) {
        repo.save(c);
    }

    /* ================= READ ================= */

    public List<Covoiturage> findByEvenement(Long idEvenement) {
        return repo.findByEvenement(idEvenement);
    }

    public List<Covoiturage> findAllCovoiturages() {
        return repo.findAllWithReservations();
    }

    /* ================= UPDATE ================= */

    public void reserver(Long idCovoiturage, Utilisateur utilisateur, int nbPlaces) {
        Covoiturage c = repo.findByIdWithReservations(idCovoiturage);
        if (c == null) {
            throw new IllegalArgumentException("Covoiturage introuvable");
        }

        Reservation r = c.getReservationParUtilisateur(utilisateur);

        if (r == null) {
            // Nouvelle réservation
            r = new Reservation();
            r.setUtilisateur(utilisateur);
            r.setCovoiturage(c);
            r.setNbPlaces(nbPlaces);
            c.getReservations().add(r); // ajoute à la collection
        } else {
            // Modification de la réservation existante
            r.setNbPlaces(nbPlaces);
        }

        // Met à jour le covoiturage (Hibernate va persister la réservation grâce au cascade)
        repo.update(c);
    }


    /**
     * Quitter un covoiturage
     */
    public void quitter(Long idCovoiturage, Utilisateur utilisateur) {

        Covoiturage c = repo.findByIdWithReservations(idCovoiturage);
        if (c == null) {
            throw new IllegalArgumentException("Covoiturage introuvable");
        }

        c.quitter(utilisateur);
        repo.update(c);
    }

    /* ================= DELETE ================= */

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
}
