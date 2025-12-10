package model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Evenement")
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEvenement")
    private Long idEvenement;

    @Column(name = "NomEvenement", nullable = false)
    private String nomEvenement;

    @Column(name = "LieuEvenement", nullable = false)
    private String lieuEvenement;

    @Column(name = "DateEvenement", nullable = false)
    private LocalDateTime dateEvenement;

    @Column(name = "TypeEvenement", nullable = false)
    private String typeEvenement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdGroupe", nullable = false)
    private Groupe groupe;

    @OneToMany(mappedBy = "evenement", fetch = FetchType.LAZY)
    private List<Covoiturage> covoiturages;

    // Constructeur vide obligatoire
    public Evenement() {}

    // Constructeur utile
    public Evenement(String nom, String lieu, LocalDateTime date, String type, Groupe groupe) {
        this.nomEvenement = nom;
        this.lieuEvenement = lieu;
        this.dateEvenement = date;
        this.typeEvenement = type;
        this.groupe = groupe;
    }

    // ===================== GETTERS / SETTERS =====================

    public Long getIdEvenement() {
        return idEvenement;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public String getLieuEvenement() {
        return lieuEvenement;
    }

    public void setLieuEvenement(String lieuEvenement) {
        this.lieuEvenement = lieuEvenement;
    }

    public LocalDateTime getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(LocalDateTime dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public String getTypeEvenement() {
        return typeEvenement;
    }

    public void setTypeEvenement(String typeEvenement) {
        this.typeEvenement = typeEvenement;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public List<Covoiturage> getCovoiturages() {
        return covoiturages;
    }

    public void setCovoiturages(List<Covoiturage> covoiturages) {
        this.covoiturages = covoiturages;
    }
}
