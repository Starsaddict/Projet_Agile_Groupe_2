package repo;

import bd.HibernateUtil;
import model.ConvocationEvenement;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            return session.createQuery(
                "select ce from ConvocationEvenement ce " +
                "where ce.evenement.idEvenement = :idEvt " +
                "and ce.joueur.idUtilisateur = :idJoueur",
                ConvocationEvenement.class
            )
            .setParameter("idEvt", idEvenement)
            .setParameter("idJoueur", idJoueur)
            .uniqueResult();
        }
    }
}
