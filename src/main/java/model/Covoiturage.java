package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
@Table(name = "Covoiturage")
public class Covoiturage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCovoiturage")
    private Long idCovoiturage;

    @Column(name = "DateCovoiturage")
    private LocalDateTime dateCovoiturage;

    @Column(name = "NbPlacesMax")
    private int nbPlacesMaxCovoiturage;

    @Column(name = "LieuDepart")
    private String lieuDepartCovoiturage;

    @ManyToOne
    @JoinColumn(name = "IdConducteur", nullable = false)
    private Utilisateur conducteur;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Reserver",
            joinColumns = @JoinColumn(name = "IdCovoiturage"),
            inverseJoinColumns = @JoinColumn(name = "IdUtilisateur")
    )
    private List<Utilisateur> reservations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "IdEvenement", nullable = false)
    private Evenement evenement;


    public Covoiturage() {
    }

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

    public void setNbPlacesMaxCovoiturage(int nbPlacesMax) {
        this.nbPlacesMaxCovoiturage = nbPlacesMax;
    }

    public String getLieuDepartCovoiturage() {
        return lieuDepartCovoiturage;
    }

    public void setLieuDepartCovoiturage(String lieuDepart) {
        this.lieuDepartCovoiturage = lieuDepart;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Utilisateur getConducteur() {
        return conducteur;
    }

    public void setConducteur(Utilisateur conducteur) {
        this.conducteur = conducteur;
    }

    public List<Utilisateur> getReservations() {
        return reservations;
    }

    public void setReservations(List<Utilisateur> utilisateurs) {
        this.reservations = utilisateurs;
    }

    public boolean hasPlacesDisponibles() {
        return reservations.size() < nbPlacesMaxCovoiturage;
    }

    public void addReservation(Utilisateur utilisateur) {
        if (utilisateur == null) return;

        if (utilisateur.equals(conducteur)) {
            throw new IllegalStateException("Le conducteur ne peut pas réserver son propre covoiturage");
        }

        if (!hasPlacesDisponibles()) {
            throw new IllegalStateException("Plus de places disponibles");
        }

        if (!reservations.contains(utilisateur)) {
            reservations.add(utilisateur);
        }
    }



    public void removeReservation(Utilisateur utilisateur) {
        if (utilisateur == null) return;
        if (reservations.remove(utilisateur)) {
            }
        }
    }
