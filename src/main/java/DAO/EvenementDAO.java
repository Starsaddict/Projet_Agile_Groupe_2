package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bd.HibernateUtil;
import model.Evenement;

public class EvenementDAO {

    // CREATE
    public void creer(Evenement e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
            System.out.println("✅ Evénement créé");
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    // UPDATE
    public void modifier(Evenement e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(e);
            tx.commit();
            System.out.println("✅ Evénement modifié");
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    // DELETE
    public void supprimer(Evenement e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(e);
            tx.commit();
            System.out.println("🗑 Evénement supprimé");
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    // READ
    public Evenement trouverParId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Evenement.class, id);
        }
    }

    public List<Evenement> toutAfficher() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Evenement", Evenement.class).list();
        }
    }
}
