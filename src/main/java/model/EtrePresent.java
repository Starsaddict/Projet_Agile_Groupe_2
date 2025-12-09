package model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "EtrePresent")
public class EtrePresent {

    @EmbeddedId
    private EtrePresent_id etrePresent_id;

    @MapsId("idJoueur")
    @ManyToOne(optional = false)
    @JoinColumn(name = "IdJoueur", referencedColumnName = "IdUtilisateur", nullable = false)
    private Joueur joueur;

    @MapsId("idGroupe")
    @ManyToOne(optional = false)
    @JoinColumn(name = "IdGroupe", nullable = false)
    private Groupe groupe;

    @MapsId("idEvenement")
    @ManyToOne(optional = false)
    @JoinColumn(name = "IdEvenement", nullable = false)
    private Evenement evenement;

    @Column(name = "ConfirmerPresenceJoueur")
    private String confirmerPresenceJoueur;

    @Column(name = "ConfirmerPresenceParent1")
    private String confirmerPresenceParent1;

    @Column(name = "ConfirmerPresenceParent2")
    private String confirmerPresenceParent2;

    @Column(name = "PresenceReelle")
    private Boolean presenceReelle;

    public EtrePresent() {
    }

    public EtrePresent(EtrePresent_id etrePresent_id) {
        this.etrePresent_id = etrePresent_id;
    }

    public EtrePresent_id getEtrePresent_id() {
        return etrePresent_id;
    }

    public void setEtrePresent_id(EtrePresent_id etrePresent_id) {
        this.etrePresent_id = etrePresent_id;
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

}
