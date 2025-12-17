package repo;

import model.Covoiturage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import bd.HibernateUtil;

import java.util.List;

public class covoiturageRepo {

    // -------------------- CREATE --------------------
    public void save(Covoiturage covoiturage) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(covoiturage);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // -------------------- READ --------------------
    // Cherche par ID avec fetch des collections pour éviter LazyInitializationException
    public Covoiturage findByIdWithReservations(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT c FROM Covoiturage c " +
                    "LEFT JOIN FETCH c.reservations " +
                    "LEFT JOIN FETCH c.conducteur " +
                    "LEFT JOIN FETCH c.evenement " +
                    "WHERE c.idCovoiturage = :id",
                    Covoiturage.class
            ).setParameter("id", id).uniqueResult();
        }
    }

    // Cherche par événement avec fetch des collections
    public List<Covoiturage> findByEvenement(Long idEvenement) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT c FROM Covoiturage c " +
                    "LEFT JOIN FETCH c.reservations " +
                    "LEFT JOIN FETCH c.conducteur " +
                    "LEFT JOIN FETCH c.evenement " +
                    "WHERE c.evenement.idEvenement = :id",
                    Covoiturage.class
            ).setParameter("id", idEvenement)
             .getResultList();
        }
    }

    // Toutes les covoiturages avec fetch pour JSP
    public List<Covoiturage> findAllWithReservations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT c FROM Covoiturage c " +
                    "LEFT JOIN FETCH c.reservations " +
                    "LEFT JOIN FETCH c.conducteur " +
                    "LEFT JOIN FETCH c.evenement",
                    Covoiturage.class
            ).getResultList();
        }
    }

    // -------------------- UPDATE --------------------
    public void update(Covoiturage covoiturage) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(covoiturage);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // -------------------- DELETE --------------------
    public void delete(Covoiturage covoiturage) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(covoiturage);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
