package model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Evenement")
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEvenement")
    private Long idEvenement;

    @Column(name = "NomEvenement")
    private String nomEvenement;

    @Column(name = "LieuEvenement")
    private String lieuEvenement;

    @Column(name = "DateEvenement")
    private LocalDateTime dateEvenement;

    @Column(name = "TypeEvenement")
    private String typeEvenement;

    public Long getIdEvenement() { return idEvenement; }
    public void setIdEvenement(Long idEvenement) { this.idEvenement = idEvenement; }

    public String getNomEvenement() { return nomEvenement; }
    public void setNomEvenement(String nomEvenement) { this.nomEvenement = nomEvenement; }

    public String getLieuEvenement() { return lieuEvenement; }
    public void setLieuEvenement(String lieuEvenement) { this.lieuEvenement = lieuEvenement; }

    public LocalDateTime getDateEvenement() { return dateEvenement; }
    public void setDateEvenement(LocalDateTime dateEvenement) { this.dateEvenement = dateEvenement; }

    public String getTypeEvenement() { return typeEvenement; }
    public void setTypeEvenement(String typeEvenement) { this.typeEvenement = typeEvenement; }

 
}
