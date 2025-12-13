package model;

import javax.persistence.*;

@Entity
@Table(name = "EtrePresent")
public class EtrePresent {

    @EmbeddedId
    private EtrePresentId etrePresentId;

    @Column(name = "ConfirmerPresenceJoueur")
    private String confirmerPresenceJoueur;

    @Column(name = "ConfirmerPresenceParent1")
    private String confirmerPresenceParent1;

    @Column(name = "ConfirmerPresenceParent2")
    private String confirmerPresenceParent2;

    @Column(name = "PresenceReelle")
    private Boolean presenceReelle;

    @MapsId("idJoueur")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IdJoueur", referencedColumnName = "IdUtilisateur", nullable = false)
    private Joueur joueur;

    @MapsId("idGroupe")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IdGroupe", referencedColumnName = "IdGroupe", nullable = false)
    private Groupe groupe;

    @MapsId("idEvenement")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IdEvenement", referencedColumnName = "IdEvenement", nullable = false)
    private Evenement evenement;

    public EtrePresent() {}

    public EtrePresent(Joueur joueur, Groupe groupe, Evenement evenement) {
        this.joueur = joueur;
        this.groupe = groupe;
        this.evenement = evenement;
        this.etrePresentId = new EtrePresentId(
                joueur != null ? joueur.getIdUtilisateur() : null,
                groupe != null ? groupe.getIdGroupe() : null,
                evenement != null ? evenement.getIdEvenement() : null
        );
    }

    public EtrePresentId getEtrePresentId() {
        return etrePresentId;
    }

    public void setEtrePresentId(EtrePresentId etrePresent_id) {
        this.etrePresentId = etrePresent_id;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public String getConfirmerPresenceJoueur() {
        return confirmerPresenceJoueur;
    }

    public void setConfirmerPresenceJoueur(String confirmerPresenceJoueur) {
        this.confirmerPresenceJoueur = confirmerPresenceJoueur;
    }

    public String getConfirmerPresenceParent1() {
        return confirmerPresenceParent1;
    }

    public void setConfirmerPresenceParent1(String confirmerPresenceParent1) {
        this.confirmerPresenceParent1 = confirmerPresenceParent1;
    }

    public String getConfirmerPresenceParent2() {
        return confirmerPresenceParent2;
    }

    public void setConfirmerPresenceParent2(String confirmerPresenceParent2) {
        this.confirmerPresenceParent2 = confirmerPresenceParent2;
    }

    public Boolean getPresenceReelle() {
        return presenceReelle;
    }

    public void setPresenceReelle(Boolean presenceReelle) {
        this.presenceReelle = presenceReelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EtrePresent)) return false;
        EtrePresent that = (EtrePresent) o;
        return etrePresentId != null && etrePresentId.equals(that.etrePresentId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(etrePresentId);
    }

    @Override
    public String toString() {
        return "EtrePresent{" +
                "id=" + etrePresentId +
                ", joueur=" + (joueur != null ? joueur.getIdUtilisateur() : null) +
                ", groupe=" + (groupe != null ? groupe.getIdGroupe() : null) +
                ", evenement=" + (evenement != null ? evenement.getIdEvenement() : null) +
                '}';
    }
}
