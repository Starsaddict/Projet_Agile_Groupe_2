package repository;

import bd.HibernateUtil;
import model.Utilisateur;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;

public class UtilisateurRepositoryImpl implements UtilisateurRepository{

    @Override
    public Optional<Utilisateur> findByEmailUtilisateur(String email, String role) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Utilisateur u " +
                    "LEFT JOIN FETCH u.joueurs " +
                    "WHERE u.emailUtilisateur = :email AND u.typeU = :role";
            Query<Utilisateur> q = session.createQuery(hql, Utilisateur.class);
            q.setParameter("email", email);
            q.setParameter("role", role);
            return q.uniqueResultOptional();

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
