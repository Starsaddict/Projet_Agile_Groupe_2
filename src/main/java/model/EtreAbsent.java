package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EtreAbsent")
public class EtreAbsent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEtreAbsent")
    private Long idEtreAbsent;

    @Column(name = "Certificat")
    private String certificat;

    @Column(name = "AbsenceDebut")
    private String absenceDebut;

    @Column(name = "AbsenceTerminee")
    private Boolean absenceTerminee;

    @ManyToOne
    @JoinColumn(name = "IdJoueur", referencedColumnName = "IdUtilisateur")
    private Joueur joueur;

    public EtreAbsent() {
    }

    public String getCertificat() {
        return certificat;
    }

    public void setCertificat(String certificat) {
        this.certificat = certificat;
    }

    public String getAbsenceDebut() {
        return absenceDebut;
    }

    public void setAbsenceDebut(String absenceDebut) {
        this.absenceDebut = absenceDebut;
    }

    public Boolean getAbsenceTerminee() {
        return absenceTerminee;
    }

    public void setAbsenceTerminee(Boolean absenceTerminee) {
        this.absenceTerminee = absenceTerminee;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
}
