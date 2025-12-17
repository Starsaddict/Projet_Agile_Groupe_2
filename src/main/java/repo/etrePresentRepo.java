package repo;

import bd.HibernateUtil;
import model.EtrePresent;
import model.EtrePresentId;
import org.hibernate.*;

public class etrePresentRepo {
    public EtrePresent getEtrePresentById(EtrePresentId id){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(EtrePresent.class, id);
        }
    }

    public void updatePresenceReelle(EtrePresentId etrePresentId, boolean presenceReelle) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            EtrePresent ep = session.get(EtrePresent.class, etrePresentId);
            if (ep == null) {
                throw new IllegalArgumentException("EtrePresent introuvable pour l'id : " + etrePresentId);
            }

            ep.setPresenceReelle(presenceReelle);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }}
