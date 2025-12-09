package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EtrePresent_id implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "IdJoueur")
    private Long idJoueur;

    @Column(name = "IdGroupe")
    private Long idGroupe;

    @Column(name = "IdEvenement")
    private Long idEvenement;

    public EtrePresent_id() {
    }

    public EtrePresent_id(Long idJoueur, Long idGroupe, Long idEvenement) {
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EtrePresent_id that = (EtrePresent_id) o;
        if (idJoueur != null ? !idJoueur.equals(that.idJoueur) : that.idJoueur != null) {
            return false;
        }
        if (idGroupe != null ? !idGroupe.equals(that.idGroupe) : that.idGroupe != null) {
            return false;
        }
        return idEvenement != null ? idEvenement.equals(that.idEvenement) : that.idEvenement == null;
    }

    @Override
    public int hashCode() {
        int result = idJoueur != null ? idJoueur.hashCode() : 0;
        result = 31 * result + (idGroupe != null ? idGroupe.hashCode() : 0);
        result = 31 * result + (idEvenement != null ? idEvenement.hashCode() : 0);
        return result;
    }
}
