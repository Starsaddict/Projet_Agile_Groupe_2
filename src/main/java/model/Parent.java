package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Parent")
public class Parent extends Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUtilisateur")
    private Long idUtilisateur;

    @ManyToMany(mappedBy = "parents")
    private List<Joueur> joueurs;

    @OneToMany(mappedBy = "conducteur")
    private List<Covoiturage> covoiturages;

    @ManyToMany(mappedBy = "reservers")
    private List<Covoiturage> covoituragesReserves;

    public Parent() {
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public List<Covoiturage> getCovoiturages() {
        return covoiturages;
    }

    public void setCovoiturages(List<Covoiturage> covoiturages) {
        this.covoiturages = covoiturages;
    }

    public List<Covoiturage> getCovoituragesReserves() {
        return covoituragesReserves;
    }

    public void setCovoituragesReserves(List<Covoiturage> covoituragesReserves) {
        this.covoituragesReserves = covoituragesReserves;
    }
}
