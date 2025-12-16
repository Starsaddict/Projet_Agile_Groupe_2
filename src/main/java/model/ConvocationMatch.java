package model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ConvocationMatch",
       uniqueConstraints = @UniqueConstraint(columnNames = {"IdEvenement", "IdJoueur"}))
public class ConvocationMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Match (Evenement)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IdEvenement", nullable = false)
    private Evenement match;

    // Joueur
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IdJoueur", nullable = false)
    private Joueur joueur;

    // null = pas répondu, true = oui, false = non
    @Column(name = "peutJouer")
    private Boolean peutJouer;

    @Column(name = "token", nullable = false, unique = true, length = 255)
    private String token;

    @Column(name = "lastUpdate")
    private LocalDateTime lastUpdate;

    public ConvocationMatch() {}

    public Long getId() {
        return id;
    }

    public Evenement getMatch() {
        return match;
    }

    public void setMatch(Evenement match) {
        this.match = match;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Boolean getPeutJouer() {
        return peutJouer;
    }

    public void setPeutJouer(Boolean peutJouer) {
        this.peutJouer = peutJouer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
