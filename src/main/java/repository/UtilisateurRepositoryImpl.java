package repository;

import bd.HibernateUtil;
import model.Utilisateur;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UtilisateurRepositoryImpl implements UtilisateurRepository{

    @Override
    public Optional<Utilisateur> findByEmailUtilisateur(String email, String role) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Utilisateur u WHERE u.emailUtilisateur = :email" +
                    " AND u.typeU = :role";
            Query<Utilisateur> q = session.createQuery(hql, Utilisateur.class);
            q.setParameter("email", email);
            q.setParameter("role", role);
            return q.uniqueResultOptional();

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Utilisateur> findFirstByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Utilisateur u WHERE u.emailUtilisateur = :email";
            Query<Utilisateur> q = session.createQuery(hql, Utilisateur.class);
            q.setParameter("email", email);
            q.setMaxResults(1);
            return q.uniqueResultOptional();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<String> findRolesByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT DISTINCT u.typeU FROM Utilisateur u WHERE u.emailUtilisateur = :email";
            Query<String> q = session.createQuery(hql, String.class);
            q.setParameter("email", email);
            return q.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
