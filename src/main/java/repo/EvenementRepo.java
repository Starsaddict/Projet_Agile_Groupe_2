package repo;

import bd.HibernateUtil;
import model.Evenement;
import model.Groupe;
import model.Joueur;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;

public class EvenementRepo {

    private static final List<String> MATCH_TYPES = Arrays.asList("MATCH_OFFICIEL");

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

    public Evenement findByIdWithGroupeAndJoueurs(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Evenement evenement = session.createQuery(
                            "select distinct e from Evenement e " +
                                    "left join fetch e.groupe g " +
                                    "left join fetch g.joueurs " +
                                    "where e.idEvenement = :id",
                            Evenement.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();

            if (evenement != null) {
                Groupe groupe = evenement.getGroupe();
                if (groupe != null) {
                    Hibernate.initialize(groupe);
                    List<Joueur> joueurs = groupe.getJoueurs();
                    Hibernate.initialize(joueurs);
                    for (Joueur joueur : joueurs) {
                        Hibernate.initialize(joueur.getParents());
                    }
                }
            }
            return evenement;
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

    /* ================= FIND ALL MATCH ================= */
    public List<Evenement> findAllMatch() {
        return findByMatchType(true);
    }

    public List<Evenement> findAllNonMatch() {
        return findByMatchType(false);
    }

    private List<Evenement> findByMatchType(boolean isMatch) {
        String operator = isMatch ? "in" : "not in";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select distinct e from Evenement e " +
                            "left join fetch e.groupe g " +
                            "left join fetch g.joueurs " +
                            "where e.typeEvenement " + operator + " (:matchTypes) " +
                            "order by e.dateEvenement asc",
                    Evenement.class
            )
            .setParameterList("matchTypes", MATCH_TYPES)
            .list();
        }
    }

    public List<Evenement> findAllEntraintement() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select distinct e from Evenement e " +
                            "where e.typeEvenement = :eventType " +
                            "order by e.dateEvenement asc",
                    Evenement.class
            )
            .setParameter("eventType", "ENTRAINEMENT")
            .list();
        }
    }

    public static void main(String[] args) {
        EvenementRepo e =  new EvenementRepo();
        System.out.println(e.findAllEntraintement());
    }
}
