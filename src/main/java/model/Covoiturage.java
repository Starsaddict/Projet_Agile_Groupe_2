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
    private String nbPlacesMax;

    @Column(name = "LieuDepart")
    private String lieuDepart;

    @ManyToOne
    @JoinColumn(name = "IdConducteur", nullable = false)
    private Utilisateur conducteur;

    @ManyToMany
    @JoinTable(
        name = "Reserver",
        joinColumns = @JoinColumn(name = "IdCovoiturage"),
        inverseJoinColumns = @JoinColumn(name = "IdUtilisateur")
    )
    private List<Utilisateur> reservers;

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

    public String getNbPlacesMax() {
        return nbPlacesMax;
    }

    public void setNbPlacesMax(String nbPlacesMax) {
        this.nbPlacesMax = nbPlacesMax;
    }

    public String getLieuDepart() {
        return lieuDepart;
    }

    public void setLieuDepart(String lieuDepart) {
        this.lieuDepart = lieuDepart;
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

    public List<Utilisateur> getReservers() {
        return reservers;
    }

    public void setReservers(List<Utilisateur> utilisateurs) {
        this.reservers = utilisateurs;
    }

    public void addUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return;
        }
        if (reservers == null) {
            reservers = new ArrayList<>();
        }
        if (!reservers.contains(utilisateur)) {
            reservers.add(utilisateur);
        }
        List<Covoiturage> covoituragesUtilisateur = utilisateur.getCovoiturages();
        if (covoituragesUtilisateur == null) {
            covoituragesUtilisateur = new ArrayList<>();
            utilisateur.setCovoiturages(covoituragesUtilisateur);
        }
        if (!covoituragesUtilisateur.contains(this)) {
            covoituragesUtilisateur.add(this);
        }
    }

    public void removeUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null || reservers == null) {
            return;
        }
        reservers.remove(utilisateur);
        List<Covoiturage> covoituragesUtilisateur = utilisateur.getCovoiturages();
        if (covoituragesUtilisateur != null) {
            covoituragesUtilisateur.remove(this);
        }
    }

}
