package model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Joueur")
public class Joueur extends Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUtilisateur")
    private Long idUtilisateur;

    @ManyToMany
    @JoinTable(
        name = "Parent_Joueur",
        joinColumns = @JoinColumn(name = "IdJoueur", referencedColumnName = "IdUtilisateur"),
        inverseJoinColumns = @JoinColumn(name = "IdParent", referencedColumnName = "IdUtilisateur")
    )
    private List<Parent> parents;

    @OneToMany(mappedBy = "joueur", cascade = CascadeType.ALL)
    private List<EtreAbsent> absences;

    @ManyToMany(mappedBy = "joueurs", cascade = CascadeType.ALL)
    private List<Groupe> groupes;

    
    public Joueur() {
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    
    
    public Parent getParent1() {
        if (parents != null && parents.size() > 0) {
            return parents.get(0);
        }
        return null;
    }
    
    public Parent getParent2() {
        if (parents != null && parents.size() > 1) {
            return parents.get(1);
        }
        return null;
    }
    
    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

    public List<EtreAbsent> getAbsences() {
        return absences;
    }

    public void setAbsences(List<EtreAbsent> absences) {
        this.absences = absences;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }
}
