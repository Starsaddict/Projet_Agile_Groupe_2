package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "Evenement")
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEvenement")
    private int idEvenement;

    @Column(name = "NomEvenement")
    private String nomEvenement;

    @Column(name = "LieuEvenement")
    private String lieuEvenement;

    @Column(name = "DateEvenement")
    private LocalDateTime dateEvenement;

    @Column(name = "TypeEvenement")
    private String typeEvenement;

    @ManyToOne
    @JoinColumn(name = "IdGroupe", nullable = true)
    private Groupe groupe;

    
    @OneToMany(mappedBy = "evenement", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<EtrePresent> presences;

  
    public int getIdEvenement() { return idEvenement; }
    public void setIdEvenement(int idEvenement) { this.idEvenement = idEvenement; }

    public String getNomEvenement() { return nomEvenement; }
    public void setNomEvenement(String nomEvenement) { this.nomEvenement = nomEvenement; }

    public String getLieuEvenement() { return lieuEvenement; }
    public void setLieuEvenement(String lieuEvenement) { this.lieuEvenement = lieuEvenement; }

    public LocalDateTime getDateEvenement() { return dateEvenement; }
    public void setDateEvenement(LocalDateTime dateEvenement) { this.dateEvenement = dateEvenement; }

    public String getTypeEvenement() { return typeEvenement; }
    public void setTypeEvenement(String typeEvenement) { this.typeEvenement = typeEvenement; }

    public Groupe getGroupe() { return groupe; }
    public void setGroupe(Groupe groupe) { this.groupe = groupe; }
}
