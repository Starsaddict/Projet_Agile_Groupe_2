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
    private String nbPlacesMaxCovoiturage;

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

    public String getNbPlacesMaxCovoiturage() {
        return nbPlacesMaxCovoiturage;
    }

    public void setNbPlacesMaxCovoiturage(String nbPlacesMax) {
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

    public void addReservation(Utilisateur utilisateur) {
        if (utilisateur == null) return;
        if (!reservations.contains(utilisateur)) {
            reservations.add(utilisateur);
            // Mettre à jour la collection inverse si présente
            if (utilisateur.getCovoiturages() != null && !utilisateur.getCovoiturages().contains(this)) {
                utilisateur.getCovoiturages().add(this);
            }
        }
    }

    public void removeReservation(Utilisateur utilisateur) {
        if (utilisateur == null) return;
        if (reservations.remove(utilisateur)) {
            // mettre à jour la collection inverse si présente
            if (utilisateur.getCovoiturages() != null) {
                utilisateur.getCovoiturages().remove(this);
            }
        }
    }
}