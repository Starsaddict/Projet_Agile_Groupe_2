package model;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(
    name = "convocation_evenement",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"IdEvenement", "IdJoueur"}
    )
)
public class ConvocationEvenement {

    @EmbeddedId
    private ConvocationEvenementId id;

    /* ================= RELATIONS ================= */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("evenementId")
    @JoinColumn(name = "IdEvenement", nullable = false)
    private Evenement evenement;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("joueurId")
    @JoinColumn(name = "IdJoueur", nullable = false)
    private Joueur joueur;

    /* ================= DONNÉES ================= */

    @Column(nullable = false, unique = true, length = 100)
    private String token;

    // null = pas répondu, true = présent, false = absent
    @Column(name = "confirme_presence")
    private Boolean confirmePresence;

    // Présence réelle enregistrée le jour J (null = non renseigné)
    @Column(name = "presence_reelle")
    private Boolean presenceReelle;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    /* ================= CONSTRUCTEUR ================= */

    public ConvocationEvenement() {}

    /* ================= GETTERS / SETTERS ================= */

    public ConvocationEvenementId getId() {
        return id;
    }

    public void setId(ConvocationEvenementId id) {
        this.id = id;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
        if (evenement != null) {
            ensureId().setEvenementId(evenement.getIdEvenement());
        }
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
        if (joueur != null) {
            ensureId().setJoueurId(joueur.getIdUtilisateur());
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getConfirmePresence() {
        return confirmePresence;
    }

    public void setConfirmePresence(Boolean confirmePresence) {
        this.confirmePresence = confirmePresence;
    }

    public Boolean getPresenceReelle() {
        return presenceReelle;
    }

    public void setPresenceReelle(Boolean presenceReelle) {
        this.presenceReelle = presenceReelle;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    private ConvocationEvenementId ensureId() {
        if (id == null) {
            id = new ConvocationEvenementId();
        }
        return id;
    }
}
