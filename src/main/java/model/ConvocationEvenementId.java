package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConvocationEvenementId implements Serializable {

    @Column(name = "IdEvenement")
    private Long evenementId;

    @Column(name = "IdJoueur")
    private Long joueurId;

    public ConvocationEvenementId() {}

    public ConvocationEvenementId(Long evenementId, Long joueurId) {
        this.evenementId = evenementId;
        this.joueurId = joueurId;
    }

    public Long getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(Long evenementId) {
        this.evenementId = evenementId;
    }

    public Long getJoueurId() {
        return joueurId;
    }

    public void setJoueurId(Long joueurId) {
        this.joueurId = joueurId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConvocationEvenementId that = (ConvocationEvenementId) o;
        return Objects.equals(evenementId, that.evenementId) &&
               Objects.equals(joueurId, that.joueurId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evenementId, joueurId);
    }
}
