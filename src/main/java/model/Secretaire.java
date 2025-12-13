package model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Secretaire")
public class Secretaire extends Utilisateur {

    public Secretaire() {
    }

}
