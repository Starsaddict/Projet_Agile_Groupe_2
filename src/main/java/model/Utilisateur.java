package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "Utilisateur")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TypeU", discriminatorType = DiscriminatorType.STRING)
public abstract class Utilisateur {

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

    @Column(name = "AdresseUtilisateur")
    private String adresseUtilisateur;

    @Column(name = "TelephoneUtilisateur")
    private String telephoneUtilisateur;

    @Column(name = "TypeU", insertable = false, updatable = false)
    private String typeU;

    @ManyToMany(mappedBy = "reservations", fetch = FetchType.LAZY)
    private List<Covoiturage> covoiturages = new ArrayList<>();

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

    public String getAdresseUtilisateur() {
        return adresseUtilisateur;
    }

    public void setAdresseUtilisateur(String adresseUtilisateur) {
        this.adresseUtilisateur = adresseUtilisateur;
    }

    public String getTelephoneUtilisateur() {
        return telephoneUtilisateur;
    }

    public void setTelephoneUtilisateur(String telephoneUtilisateur) {
        this.telephoneUtilisateur = telephoneUtilisateur;
    }

    public String getRoleLabel() {
        if (this instanceof Joueur) {
            return "Joueur";
        } else if (this instanceof Parent) {
            return "Parent";
        } else if (this instanceof Secretaire) {
            return "Secrétaire";
        } else if (this.getClass().getSimpleName().equals("Admin")) {
            return "Administrateur";
        }
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Utilisateur))
            return false;
        Utilisateur that = (Utilisateur) o;
        return idUtilisateur != null && idUtilisateur.equals(that.idUtilisateur);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idUtilisateur);
    }

    @Override
    public String toString() {
        return "Utilisateur [idUtilisateur=" + idUtilisateur + ", emailUtilisateur=" + emailUtilisateur
                + ", nomUtilisateur=" + nomUtilisateur + ", prenomUtilisateur=" + prenomUtilisateur + ", typeU="
                + typeU + "]";
    }
}
