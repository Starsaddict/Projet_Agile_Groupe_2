package repo;

import bd.HibernateUtil;
import model.Utilisateur;
import org.hibernate.Session;

public class utilisateurRepo {
    public Utilisateur loadUtilisateur(int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Utilisateur u =  (Utilisateur) session.get(Utilisateur.class, id);
            session.close();
            return u;
        }
    }

    public Utilisateur saveUtilisateur(Utilisateur u) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(u);
            session.getTransaction().commit();
            session.close();
            return u;
        }
    }

    public Utilisateur updateUtilisateur(Utilisateur u) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(u);
            session.getTransaction().commit();
            session.close();
            return u;
        }
    }

    public Utilisateur deleteUtilisateur(Utilisateur u) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(u);
            session.getTransaction().commit();
            session.close();
            return u;
        }
    }

    public Utilisateur refreshUtilisateur(Utilisateur u) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            long id = u.getIdUtilisateur();
            Utilisateur u2 = (Utilisateur) session.get(Utilisateur.class, id);
            session.close();
            return u2;
        }
    }
}
