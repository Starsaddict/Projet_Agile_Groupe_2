package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EnvoyerMessageId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "IdReceveur", nullable = false)
    private Long idReceveur;

    @Column(name = "IdEnvoyeur", nullable = false)
    private Long idEnvoyeur;

    @Column(name = "DateMessage", nullable = false)
    private LocalDateTime dateMessage;

    public EnvoyerMessageId() {
    }

    public EnvoyerMessageId(Long receveurId, Long envoyeurId, LocalDateTime dateEnvoi) {
        this.idReceveur = receveurId;
        this.idEnvoyeur = envoyeurId;
        this.dateMessage = dateEnvoi;
    }

    public Long getIdReceveur() {
        return idReceveur;
    }

    public void setIdReceveur(Long idReceveur) {
        this.idReceveur = idReceveur;
    }

    public Long getIdEnvoyeur() {
        return idEnvoyeur;
    }

    public void setIdEnvoyeur(Long idEnvoyeur) {
        this.idEnvoyeur = idEnvoyeur;
    }

    public LocalDateTime getDateEnvoi() {
        return dateMessage;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateMessage = dateEnvoi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnvoyerMessageId)) return false;
        EnvoyerMessageId that = (EnvoyerMessageId) o;
        return Objects.equals(idReceveur, that.idReceveur) &&
                Objects.equals(idEnvoyeur, that.idEnvoyeur) &&
                Objects.equals(dateMessage, that.dateMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReceveur, idEnvoyeur, dateMessage);
    }
}