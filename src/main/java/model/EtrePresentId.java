package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EtrePresentId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "IdJoueur")
    private Long idJoueur;

    @Column(name = "IdGroupe")
    private Long idGroupe;

    @Column(name = "IdEvenement")
    private Long idEvenement;

    public EtrePresentId() {
    }

    public EtrePresentId(Long idJoueur, Long idGroupe, Long idEvenement) {
        this.idJoueur = idJoueur;
        this.idGroupe = idGroupe;
        this.idEvenement = idEvenement;
    }

    public Long getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(Long idJoueur) {
        this.idJoueur = idJoueur;
    }

    public Long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public Long getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(Long idEvenement) {
        this.idEvenement = idEvenement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EtrePresentId that = (EtrePresentId) o;
        return Objects.equals(idJoueur, that.idJoueur) &&
                Objects.equals(idGroupe, that.idGroupe) &&
                Objects.equals(idEvenement, that.idEvenement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJoueur, idGroupe, idEvenement);
    }
}
