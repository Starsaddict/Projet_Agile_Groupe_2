package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Groupe")
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdGroupe")
    private Long idGroupe;

    @Column(name = "NomGroupe")
    private String nomGroupe;

    @OneToMany(mappedBy = "groupe", fetch = FetchType.LAZY)
    private List<Evenement> evenements = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "Groupe_Joueur",
        joinColumns = @JoinColumn(name = "IdGroupe", referencedColumnName = "IdGroupe"),
        inverseJoinColumns = @JoinColumn(name = "IdJoueur", referencedColumnName = "IdUtilisateur")
    )
    private List<Joueur> joueurs = new ArrayList<>();

    public Groupe() {
    }

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

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    public void addEvenement(Evenement evenement) {
        if (evenement == null) {
            return;
        }
        if (evenements == null) {
            evenements = new ArrayList<>();
        }
        if (!evenements.contains(evenement)) {
            evenements.add(evenement);
        }
        evenement.setGroupe(this);
    }

    public void removeEvenement(Evenement evenement) {
        if (evenement == null || evenements == null) {
            return;
        }
        evenements.remove(evenement);
        if (evenement.getGroupe() == this) {
            evenement.setGroupe(null);
        }
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }
}
