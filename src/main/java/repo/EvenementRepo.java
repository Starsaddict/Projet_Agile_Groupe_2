package repo;

import bd.HibernateUtil;
import model.Evenement;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EvenementRepo {

    /* ================= CREATE ================= */
    public void create(Evenement e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    /* ================= UPDATE ================= */
    public void update(Evenement e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(e);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    /* ================= DELETE ================= */
    public void delete(Evenement e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(e);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    /* ================= FIND BY ID ================= */
    public Evenement findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Evenement.class, id);
        }
    }

    /* ================= FIND ALL (TRI PAR DATE) ================= */
    public List<Evenement> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Evenement e ORDER BY e.dateEvenement ASC",
                    Evenement.class
            ).list();
        }
    }
}
