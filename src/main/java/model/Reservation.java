package model;
import javax.persistence.*;

@Entity
@Table(name = "Reserver")
public class Reservation {

    public Long getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(Long idReservation) {
		this.idReservation = idReservation;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Covoiturage getCovoiturage() {
		return covoiturage;
	}

	public void setCovoiturage(Covoiturage covoiturage) {
		this.covoiturage = covoiturage;
	}

	public int getNbPlaces() {
		return nbPlaces;
	}

	public void setNbPlaces(int nbPlaces) {
		this.nbPlaces = nbPlaces;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @ManyToOne
    @JoinColumn(name = "IdUtilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "IdCovoiturage", nullable = false)
    private Covoiturage covoiturage;

    @Column(name = "NbPlaces", nullable = false)
    private int nbPlaces;

}
