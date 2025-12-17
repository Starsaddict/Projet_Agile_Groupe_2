package repo;

import bd.HibernateUtil;
import model.Code;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class codeRepo {

    public Code findByCode(String codeStr) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Code.class, codeStr);
        }
    }

    public Code save(Code c) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
            return c;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            throw e;
        }
    }

    public boolean delete(String codeStr) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Code c = session.get(Code.class, codeStr);
            if (c != null) {
                session.delete(c);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            throw e;
        }
    }
}
