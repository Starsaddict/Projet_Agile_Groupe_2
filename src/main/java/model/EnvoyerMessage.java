package model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "EnvoyerMessage")
public class EnvoyerMessage {

    @EmbeddedId
    private EnvoyerMessageId id = new EnvoyerMessageId();

    @MapsId("idReceveur")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IdReceveur", nullable = false)
    private Utilisateur receveur;

    @MapsId("idEnvoyeur")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IdEnvoyeur", nullable = false)
    private Utilisateur envoyeur;

    @Column(name = "Contenu", length = 2000)
    private String contenu;

    public EnvoyerMessage() {
    }

    public EnvoyerMessage(Utilisateur receveur, Utilisateur envoyeur, LocalDateTime dateEnvoi, String contenu) {
        this.receveur = receveur;
        this.envoyeur = envoyeur;
        this.id = new EnvoyerMessageId(receveur != null ? receveur.getIdUtilisateur() : null,
                envoyeur != null ? envoyeur.getIdUtilisateur() : null,
                dateEnvoi);
        this.contenu = contenu;
    }

    public EnvoyerMessageId getId() {
        return id;
    }

    public Utilisateur getReceveur() {
        return receveur;
    }

    public void setReceveur(Utilisateur receveur) {
        this.receveur = receveur;
        if (receveur != null) this.id.setIdReceveur(receveur.getIdUtilisateur());
    }

    public Utilisateur getEnvoyeur() {
        return envoyeur;
    }

    public void setEnvoyeur(Utilisateur envoyeur) {
        this.envoyeur = envoyeur;
        if (envoyeur != null) this.id.setIdEnvoyeur(envoyeur.getIdUtilisateur());
    }

    public LocalDateTime getDateEnvoi() {
        return id.getDateEnvoi();
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.id.setDateEnvoi(dateEnvoi);
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return "EnvoyerMessage [receveur=" + (receveur != null ? receveur.getIdUtilisateur() : null)
                + ", envoyeur=" + (envoyeur != null ? envoyeur.getIdUtilisateur() : null)
                + ", dateEnvoi=" + getDateEnvoi() + "]";
    }
}