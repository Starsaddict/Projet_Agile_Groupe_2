package repo;

import bd.HibernateUtil;
import model.Evenement;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class EvenementRepo {

    private boolean execute(Operation op) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            op.run(s);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean save(Evenement e) {
        return execute(s -> s.saveOrUpdate(e));
    }

    public boolean delete(Evenement e) {
        return execute(s -> s.delete(e));
    }

    public Optional<Evenement> findById(int id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(s.get(Evenement.class, id));
        }
    }

    public List<Evenement> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM Evenement", Evenement.class).list();
        }
    }

    private interface Operation {
        void run(Session session);
    }
}
