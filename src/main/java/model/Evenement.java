package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

 // Pour Tester us 13 il faut laisser gourpe lié vide.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdGroupe", nullable = true)
    private Groupe groupe;

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Covoiturage> covoiturages;
    
    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EtrePresent> etresPresents = new ArrayList<>();


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
    // Pour Tester us 13 il faut laisser gourpe lié vide.
    public Evenement(String nom, String lieu, LocalDateTime date, String type) {
        this.nomEvenement = nom;
        this.lieuEvenement = lieu;
        this.dateEvenement = date;
        this.typeEvenement = type;
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
