package model;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Parent")
public class Parent extends Utilisateur {

    @ManyToMany(mappedBy = "parents", fetch = FetchType.LAZY)
    private List<Joueur> joueurs;

    @OneToMany(mappedBy = "conducteur")
    private List<Covoiturage> covoiturages;


    public Parent() {
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

}
