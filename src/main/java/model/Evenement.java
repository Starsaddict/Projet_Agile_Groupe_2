package model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "IdGroupe", nullable = false)
    private Groupe groupe;

    @Column(name = "IdGroupe", nullable = false, insertable = false, updatable = false)
    private Long idGroupe;

    @OneToMany(mappedBy = "evenement", fetch = FetchType.LAZY)
    private List<Covoiturage> covoiturages;
    
    public Evenement() {
    }

    public Long getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(Long idEvenement) {
        this.idEvenement = idEvenement;
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

    public Long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
        this.idGroupe = groupe != null ? groupe.getIdGroupe() : null;
    }

    public String getTypeEvenement() { return this.typeEvenement; }

    public void setTypeEvenement(String typeEvenement) { this.typeEvenement=typeEvenement; }

    public List<Covoiturage> getCovoiturages() {
        return covoiturages;
    }
    public void setCovoiturages(List<Covoiturage> covoiturages) {
        this.covoiturages = covoiturages;
    }
}
