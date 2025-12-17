package repository;

import bd.HibernateUtil;
import model.EtreAbsent;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AbsenceRepositoryImpl implements AbsenceRepository {

    @Override
    public EtreAbsent saveAbsence(EtreAbsent absence) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(absence);
            tx.commit();
            return absence;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }
}