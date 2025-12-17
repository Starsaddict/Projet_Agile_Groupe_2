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

    private LocalDateTime absenceFin;

    @Enumerated(EnumType.STRING)
    private TypeAbsence typeAbsence;

    @ManyToOne
    @JoinColumn(name = "IdJoueur")
    private Joueur joueur;

    @Column(name = "CertificatName")
    private String certificatName;

    @Column(name = "CertificatContentType")
    private String certificatContentType;

    @Column(name = "Motif")
    private String motif;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "Certificat")
    private byte[] certificatData;

    public enum TypeAbsence {
        COURTE,
        LONGUE
    }

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

    public LocalDateTime getAbsenceFin() {
        return absenceFin;
    }

    public void setAbsenceFin(LocalDateTime absenceFin) {
        this.absenceFin = absenceFin;
    }

    public TypeAbsence getTypeAbsence() {
        return typeAbsence;
    }

    public void setTypeAbsence(TypeAbsence typeAbsence) {
        this.typeAbsence = typeAbsence;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public boolean hasCertificat() {
        return certificatData != null && certificatData.length > 0;
    }

    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return absenceFin == null || now.isBefore(absenceFin) || now.isEqual(absenceFin);
    }

}