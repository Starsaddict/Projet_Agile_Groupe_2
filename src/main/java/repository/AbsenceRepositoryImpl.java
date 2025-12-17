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

    @Override
    public java.util.Optional<EtreAbsent> findById(Long idAbsence) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            EtreAbsent ea = session.get(EtreAbsent.class, idAbsence);
            return java.util.Optional.ofNullable(ea);
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Optional.empty();
        }
    }
}
