package repo;

import bd.HibernateUtil;
import model.Utilisateur;
import org.hibernate.Session;
import org.hibernate.query.Query;

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

    public boolean emailExists(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "select count(u.idUtilisateur) from Utilisateur u where u.emailUtilisateur = :email", Long.class);
            query.setParameter("email", email);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        }
    }
}
