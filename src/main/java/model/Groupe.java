package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Groupe")
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdGroupe")
    private Long idGroupe;

    @Column(name = "NomGroupe")
    private String nomGroupe;

    @ManyToMany
    @JoinTable(
        name = "Groupe_Joueur",
        joinColumns = @JoinColumn(name = "IdGroupe"),
        inverseJoinColumns = @JoinColumn(name = "IdJoueur")
    )
    private List<Joueur> joueurs = new ArrayList<>();

    // ⭐ OneToMany avec Evenement
    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Evenement> evenements = new ArrayList<>();

    public Groupe() {}

    public Long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    public void addEvenement(Evenement evt) {
        if (evt != null && !evenements.contains(evt)) {
            evenements.add(evt);
            evt.setGroupe(this);
        }
    }

    public void removeEvenement(Evenement evt) {
        if (evt != null && evenements.remove(evt)) {
            evt.setGroupe(null);
        }
    }
}
