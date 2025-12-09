package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    @OneToMany(mappedBy = "joueur")
    private List<EtreAbsent> absences;

    @ManyToMany(mappedBy = "joueurs")
    private List<Groupe> groupes;

    
    public Joueur() {
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
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
