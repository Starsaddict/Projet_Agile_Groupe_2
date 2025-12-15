package model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Coach")
public class Coach extends Utilisateur {

    public Coach() {
    }

}
