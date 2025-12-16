// java
package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "EtreAbsent")
public class EtreAbsent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtreAbsent;

    private LocalDateTime absenceDebut;

    private Boolean absenceTerminee;

    @ManyToOne
    @JoinColumn(name = "IdJoueur")
    private Joueur joueur;

    @Column(name = "CertificatName")
    private String certificatName;

    @Column(name = "CertificatContentType")
    private String certificatContentType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "Certificat")
    private byte[] certificatData;

    // getters / setters

    public Long getIdEtreAbsent() {
        return idEtreAbsent;
    }

    public void setIdEtreAbsent(Long idEtreAbsent) {
        this.idEtreAbsent = idEtreAbsent;
    }

    public LocalDateTime getAbsenceDebut() {
        return absenceDebut;
    }

    public void setAbsenceDebut(LocalDateTime absenceDebut) {
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

    public String getCertificatName() {
        return certificatName;
    }

    public void setCertificatName(String certificatName) {
        this.certificatName = certificatName;
    }

    public String getCertificatContentType() {
        return certificatContentType;
    }

    public void setCertificatContentType(String certificatContentType) {
        this.certificatContentType = certificatContentType;
    }

    public byte[] getCertificatData() {
        return certificatData;
    }

    public void setCertificatData(byte[] certificatData) {
        this.certificatData = certificatData;
    }
}