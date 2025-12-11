package model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Utilisateur")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TypeU",
        discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("Utilisateur")
public class Utilisateur {

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

    @Column(name = "TypeU", insertable = false, updatable = false)
    private String typeU;

    @ManyToMany(mappedBy = "reservers", cascade = CascadeType.ALL)
    private List<Covoiturage> covoiturages;

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.REMOVE, orphanRemoval = true)
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

    public String getTypeU() {
        return typeU;
    }

    public void setTypeU(String typeU) {
        this.typeU = typeU;
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

    @Override
    public String toString() {
        return "Utilisateur [idUtilisateur=" + idUtilisateur + ", emailUtilisateur=" + emailUtilisateur
                + ", nomUtilisateur=" + nomUtilisateur + ", prenomUtilisateur=" + prenomUtilisateur + ", typeU="
                + typeU + "]";
    }
}
