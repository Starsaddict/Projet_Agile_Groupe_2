package repo;

import org.hibernate.Session;
import model.Joueur;
import bd.HibernateUtil;

public class joueurRepo {

    public Joueur findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Joueur.class, id);
        }
    }
}
