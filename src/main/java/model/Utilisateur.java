package model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {

    //TODO: 缺一个reserve onetomany

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUtilisateur")
    private Long idUtilisateur;

    @Column(name = "EmailUtilisateur")
    private String emailUtilisateur;

    @Column(name = "MdpUtilisateur")
    private String mdpUtilisateur;

    @Column(name = "NomUtilisateur")
    private String nomUtilisateur;

    @Column(name = "PrenomUtilisateur")
    private String prenomUtilisateur;

    @Column(name = "DateNaissanceUtilisateur")
    private LocalDate dateNaissanceUtilisateur;

    @ManyToMany(mappedBy = "reservers")
    private List<Covoiturage> covoiturages;

    @OneToMany(mappedBy = "conducteur")
    private List<Covoiturage> conduits;

    public Utilisateur() {
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public String getMdpUtilisateur() {
        return mdpUtilisateur;
    }

    public void setMdpUtilisateur(String mdpUtilisateur) {
        this.mdpUtilisateur = mdpUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public LocalDate getDateNaissanceUtilisateur() {
        return dateNaissanceUtilisateur;
    }

    public void setDateNaissanceUtilisateur(LocalDate dateNaissanceUtilisateur) {
        this.dateNaissanceUtilisateur = dateNaissanceUtilisateur;
    }

    public List<Covoiturage> getCovoiturages() {
        return covoiturages;
    }

    public void setCovoiturages(List<Covoiturage> covoiturages) {
        this.covoiturages = covoiturages;
    }

    public List<Covoiturage> getConduits() {
        return conduits;
    }

    public void setConduits(List<Covoiturage> conduits) {
        this.conduits = conduits;
    }
}
