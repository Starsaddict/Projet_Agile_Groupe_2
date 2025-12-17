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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ================= RELATIONS ================= */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IdEvenement", nullable = false)
    private Evenement evenement;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IdJoueur", nullable = false)
    private Joueur joueur;

    /* ================= DONNÉES ================= */

    @Column(nullable = false, unique = true, length = 100)
    private String token;

    // null = pas répondu, true = présent, false = absent
    @Column(name = "confirme_presence")
    private Boolean confirmePresence;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    /* ================= CONSTRUCTEUR ================= */

    public ConvocationEvenement() {}

    /* ================= GETTERS / SETTERS ================= */

    public Long getId() {
        return id;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
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

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
