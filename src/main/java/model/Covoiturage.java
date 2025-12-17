package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Covoiturage")
public class Covoiturage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCovoiturage")
    private Long idCovoiturage;

    @Column(name = "DateCovoiturage")
    private LocalDateTime dateCovoiturage;

    @Column(name = "NbPlacesMax", nullable = false)
    private int nbPlacesMaxCovoiturage;

    @Column(name = "LieuDepart", nullable = false)
    private String lieuDepartCovoiturage;

    @ManyToOne
    @JoinColumn(name = "IdConducteur", nullable = false)
    private Utilisateur conducteur;

    @ManyToOne
    @JoinColumn(name = "IdEvenement", nullable = false)
    private Evenement evenement;

    @OneToMany(
        mappedBy = "covoiturage",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Reservation> reservations = new ArrayList<>();

    /* ================= GETTERS / SETTERS ================= */

    public Long getIdCovoiturage() {
        return idCovoiturage;
    }

    public void setIdCovoiturage(Long idCovoiturage) {
        this.idCovoiturage = idCovoiturage;
    }

    public LocalDateTime getDateCovoiturage() {
        return dateCovoiturage;
    }

    public void setDateCovoiturage(LocalDateTime dateCovoiturage) {
        this.dateCovoiturage = dateCovoiturage;
    }

    public int getNbPlacesMaxCovoiturage() {
        return nbPlacesMaxCovoiturage;
    }

    public void setNbPlacesMaxCovoiturage(int nbPlacesMaxCovoiturage) {
        this.nbPlacesMaxCovoiturage = nbPlacesMaxCovoiturage;
    }

    public String getLieuDepartCovoiturage() {
        return lieuDepartCovoiturage;
    }

    public void setLieuDepartCovoiturage(String lieuDepartCovoiturage) {
        this.lieuDepartCovoiturage = lieuDepartCovoiturage;
    }

    public Utilisateur getConducteur() {
        return conducteur;
    }

    public void setConducteur(Utilisateur conducteur) {
        this.conducteur = conducteur;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    /* ================= LOGIQUE MÉTIER ================= */

    public int getPlacesReservees() {
        return reservations.stream()
                .mapToInt(Reservation::getNbPlaces)
                .sum();
    }

    public int getPlacesRestantes() {
        return nbPlacesMaxCovoiturage - getPlacesReservees();
    }

    public void reserver(Utilisateur utilisateur, int nbPlaces) {

        if (utilisateur == null)
            throw new IllegalArgumentException("Utilisateur invalide");

        if (utilisateur.equals(conducteur))
            throw new IllegalStateException("Le conducteur ne peut pas réserver");

        if (nbPlaces <= 0)
            throw new IllegalArgumentException("Nombre de places invalide");

        Reservation r = reservations.stream()
                .filter(res -> res.getUtilisateur().equals(utilisateur))
                .findFirst()
                .orElse(null);

        int dejaReserve = (r != null) ? r.getNbPlaces() : 0;
        int delta = nbPlaces - dejaReserve;

        if (delta > getPlacesRestantes())
            throw new IllegalStateException("Pas assez de places disponibles");

        if (r == null) {
            r = new Reservation();
            r.setUtilisateur(utilisateur);
            r.setCovoiturage(this);
            r.setNbPlaces(nbPlaces);
            reservations.add(r);
        } else {
            r.setNbPlaces(nbPlaces); // modification
        }
    }

    public void quitter(Utilisateur utilisateur) {
        reservations.removeIf(r -> r.getUtilisateur().equals(utilisateur));
    }
    
    public Reservation getReservationParUtilisateur(Utilisateur utilisateur) {
        return reservations.stream()
                .filter(r -> r.getUtilisateur().equals(utilisateur))
                .findFirst()
                .orElse(null);
    }

}
