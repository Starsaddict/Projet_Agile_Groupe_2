package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Joueur")
public class Joueur extends Utilisateur {

    @Column(name = "NumLicenceJoueur")
    private String numLicenceJoueur;

    @ManyToMany
    @JoinTable(
            name = "Parent_Joueur",
            joinColumns = @JoinColumn(name = "IdJoueur", referencedColumnName = "IdUtilisateur"),
            inverseJoinColumns = @JoinColumn(name = "IdParent", referencedColumnName = "IdUtilisateur")
    )
    private List<Parent> parents = new ArrayList<>();

    @OneToMany(mappedBy = "joueur", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EtreAbsent> absences = new ArrayList<>();

    @ManyToMany(mappedBy = "joueurs")
    private List<Groupe> groupes = new ArrayList<>();

    @OneToMany(mappedBy = "joueur", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EtrePresent> presences = new ArrayList<>();

    public Joueur() {
    }

    public String getNumLicenceJoueur() {
        return numLicenceJoueur;
    }

    public void setNumLicenceJoueur(String numLicenceJoueur) {
        this.numLicenceJoueur = numLicenceJoueur;
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

    public void setParent1(Parent p) {
        parents.add(0, p);
        p.getJoueurs().add(this);
    }

    public void setParent2(Parent p) {
        parents.add(1, p);
        p.getJoueurs().add(this);
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

    public List<EtrePresent> getPresences() {
        return presences;
    }

    public void setPresences(List<EtrePresent> presences) {
        this.presences = presences;
    }

    public void addPresence(EtrePresent p) {
        if (p != null && !presences.contains(p)) {
            presences.add(p);
            p.setJoueur(this);
        }
    }

    public void removePresence(EtrePresent p) {
        if (p != null && presences.remove(p)) {
            p.setJoueur(null);
        }
    }

    public void addAbsence(EtreAbsent a) {
        if (a != null && !absences.contains(a)) {
            absences.add(a);
            a.setJoueur(this);
        }
    }

    public void removeAbsence(EtreAbsent a) {
        if (a != null && absences.remove(a)) {
            a.setJoueur(null);
        }
    }

    public void addGroupe(Groupe g) {
        if (g == null) return;
        if (!groupes.contains(g)) {
            groupes.add(g);
            if (g.getJoueurs() != null && !g.getJoueurs().contains(this)) {
                g.getJoueurs().add(this);
            }
        }
    }

    public void removeGroupe(Groupe g) {
        if (g == null) return;
        if (groupes.remove(g)) {
            if (g.getJoueurs() != null) {
                g.getJoueurs().remove(this);
            }
        }
    }
}