package repo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Groupe;
import bd.HibernateUtil;

public class groupeRepo {

    public void saveGroupe(Groupe groupe) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(groupe);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Groupe findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Groupe.class, id);
        }
    }

    public List<Groupe> getAllGroupes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Groupe", Groupe.class).list();
        }
    }
}
