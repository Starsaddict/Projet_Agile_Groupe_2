package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Evenement")
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEvenement")
    private Long idEvenement;

    @Column(name = "NomEvenement")
    private String nomEvenement;

    @Column(name = "LieuEvenement")
    private String lieuEvenement;

    @Column(name = "DateEvenement")
    private LocalDateTime dateEvenement;

    @Column(name = "TypeEvenement")
    private String typeEvenement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdGroupe", nullable = true)
    private Groupe groupe;

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Covoiturage> covoiturages = new ArrayList<>();

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EtrePresent> etresPresents = new ArrayList<>();

    public Evenement() {}

    public Evenement(String nom, String lieu, LocalDateTime date, String type, Groupe groupe) {
        this.nomEvenement = nom;
        this.lieuEvenement = lieu;
        this.dateEvenement = date;
        this.typeEvenement = type;
        this.groupe = groupe;
    }

    public Evenement(String nom, String lieu, LocalDateTime date, String type) {
        this.nomEvenement = nom;
        this.lieuEvenement = lieu;
        this.dateEvenement = date;
        this.typeEvenement = type;
    }

    public Long getIdEvenement() { return idEvenement; }
    public void setIdEvenement(Long idEvenement) { this.idEvenement = idEvenement; }

    public String getNomEvenement() { return nomEvenement; }
    public void setNomEvenement(String nomEvenement) { this.nomEvenement = nomEvenement; }

    public String getLieuEvenement() { return lieuEvenement; }
    public void setLieuEvenement(String lieuEvenement) { this.lieuEvenement = lieuEvenement; }

    public LocalDateTime getDateEvenement() { return dateEvenement; }
    public void setDateEvenement(LocalDateTime dateEvenement) { this.dateEvenement = dateEvenement; }

    public String getTypeEvenement() { return typeEvenement; }
    public void setTypeEvenement(String typeEvenement) { this.typeEvenement = typeEvenement; }

    public Groupe getGroupe() { return groupe; }
    public void setGroupe(Groupe groupe) { this.groupe = groupe; }

    public List<Covoiturage> getCovoiturages() { return covoiturages; }
    public void setCovoiturages(List<Covoiturage> covoiturages) { this.covoiturages = covoiturages; }

    public List<EtrePresent> getEtresPresents() { return etresPresents; }
    public void setEtresPresents(List<EtrePresent> etresPresents) { this.etresPresents = etresPresents; }

    public void addCovoiturage(Covoiturage c) {
        if (c != null && !covoiturages.contains(c)) {
            covoiturages.add(c);
            c.setEvenement(this);
        }
    }

    public void removeCovoiturage(Covoiturage c) {
        if (c != null && covoiturages.remove(c)) {
            c.setEvenement(null);
        }
    }

    public void addEtrePresent(EtrePresent p) {
        if (p != null && !etresPresents.contains(p)) {
            etresPresents.add(p);
            p.setEvenement(this);
        }
    }

    public void removeEtrePresent(EtrePresent p) {
        if (p != null && etresPresents.remove(p)) {
            p.setEvenement(null);
        }
    }
    
 // Méthode utile pour formatter la date dans le format attendu pour <input type="datetime-local">
    public String getDateForInput() {
        return dateEvenement.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        );
    }
}