package repo;

import bd.HibernateUtil;
import model.ConvocationMatch;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ConvocationMatchRepo {

    /* ================= CREATE ================= */
    public void save(ConvocationMatch convocation) {
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
    public void update(ConvocationMatch convocation) {
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
    public ConvocationMatch findByToken(String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select cm from ConvocationMatch cm " +
                    "join fetch cm.joueur j " +
                    "join fetch cm.match m " +
                    "where cm.token = :token",
                    ConvocationMatch.class
            )
            .setParameter("token", token)
            .uniqueResult();
        }
    }

    /* ================= FIND BY MATCH + JOUEUR ================= */
    public ConvocationMatch findByMatchAndJoueur(Long idMatch, Long idJoueur) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select cm from ConvocationMatch cm " +
                    "where cm.match.idEvenement = :idMatch " +
                    "and cm.joueur.idUtilisateur = :idJoueur",
                    ConvocationMatch.class
            )
            .setParameter("idMatch", idMatch)
            .setParameter("idJoueur", idJoueur)
            .uniqueResult();
        }
    }
}
