package repo;

import bd.HibernateUtil;
import model.ConvocationEvenement;
import model.ConvocationEvenementId;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ConvocationEvenementRepo {

    /* ================= CREATE ================= */
    public void save(ConvocationEvenement convocation) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(convocation);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /* ================= UPDATE ================= */
    public void update(ConvocationEvenement convocation) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(convocation);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public ConvocationEvenement findById(ConvocationEvenementId id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(ConvocationEvenement.class, id);
        }
    }

    /* ================= FIND BY TOKEN ================= */
    public ConvocationEvenement findByToken(String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            ConvocationEvenement ce = session.createQuery(
                "select ce from ConvocationEvenement ce " +
                "join fetch ce.joueur j " +
                "join fetch ce.evenement e " +
                "where ce.token = :token",
                ConvocationEvenement.class
            )
            .setParameter("token", token)
            .uniqueResult();

            if (ce != null) {
                // Initialisation contrôlée
                if (ce.getJoueur() != null) {
                    Hibernate.initialize(ce.getJoueur().getParents());
                }
            }

            return ce;
        }
    }


    /* ================= FIND BY EVENEMENT + JOUEUR ================= */
    public ConvocationEvenement findByEvenementAndJoueur(
            Long idEvenement, Long idJoueur) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(
                    ConvocationEvenement.class,
                    new ConvocationEvenementId(idEvenement, idJoueur)
            );
        }
    }

    /* ================= LISTE DES CONFIRMÉS POUR UN ÉVÈNEMENT ================= */
    public List<ConvocationEvenement> findConfirmedByEvenement(Long idEvenement) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select ce from ConvocationEvenement ce " +
                    "join fetch ce.joueur j " +
                    "where ce.evenement.idEvenement = :idEvt " +
                    "and ce.confirmePresence = true",
                    ConvocationEvenement.class
            )
            .setParameter("idEvt", idEvenement)
            .list();
        }
    }

    /* ================= MAJ PRESENCE REELLE ================= */
    public void updatePresenceReelle(ConvocationEvenementId convocationId, boolean presenceReelle) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            ConvocationEvenement ce = session.get(ConvocationEvenement.class, convocationId);
            if (ce == null) {
                throw new IllegalArgumentException("ConvocationEvenement introuvable pour l'id : " + convocationId);
            }

            ce.setPresenceReelle(presenceReelle);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
