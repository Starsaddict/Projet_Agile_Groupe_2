package repo;

import bd.HibernateUtil;
import model.Joueur;
import model.Parent;
import model.Utilisateur;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class utilisateurRepo {
    public Utilisateur loadUtilisateur(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Utilisateur u = (Utilisateur) session.get(Utilisateur.class, id);
            if (u instanceof Joueur) {
                Joueur joueur = (Joueur) u;
                List<Parent> parents = joueur.getParents();
                if (parents != null) {
                    parents.size(); // force initialization
                }
            }
            if (u instanceof Parent) {
                Parent parent = (Parent) u;
                List<Joueur> joueurs = parent.getJoueurs();
                if (joueurs != null) {
                    joueurs.size(); // force initialization
                }
            }
            session.close();
            return u;
        }
    }

    public Utilisateur saveUtilisateur(Utilisateur u) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(u);
            session.getTransaction().commit();
            session.close();
            return u;
        }
    }

    public Utilisateur updateUtilisateur(Utilisateur u) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(u);
            session.getTransaction().commit();
            session.close();
            return u;
        }
    }

    public Utilisateur deleteUtilisateur(Utilisateur u) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(u);
            session.getTransaction().commit();
            session.close();
            return u;
        }
    }

    public Utilisateur refreshUtilisateur(Utilisateur u) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            long id = u.getIdUtilisateur();
            Utilisateur u2 = (Utilisateur) session.get(Utilisateur.class, id);
            session.close();
            return u2;
        }
    }

    public boolean emailExists(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "select count(u.idUtilisateur) from Utilisateur u where u.emailUtilisateur = :email", Long.class);
            query.setParameter("email", email);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        }
    }

    public List<Utilisateur> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Utilisateur> query = session.createQuery(
                    "from Utilisateur u where u.emailUtilisateur = :email", Utilisateur.class);
            query.setParameter("email", email);
            return query.list();
        }
    }

    public Parent loadParent(Long id) {
        Utilisateur utilisateur = loadUtilisateur(id);
        if (utilisateur instanceof Parent) {
            return (Parent) utilisateur;
        }
        return null;
    }

    public List<Utilisateur> loadAllUtilisateurs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Utilisateur> query = session.createQuery("from Utilisateur", Utilisateur.class);
            return query.list();
        }
    }

    public void updateJoueurAndParents(Joueur joueur, Parent parent1, Parent parent2) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(joueur);

            if (parent1 != null) {
                session.update(parent1);
            }

            if (parent2 != null) {
                session.update(parent2);
            }

            if (parent1 != null || parent2 != null) {
                List<Parent> parents = new ArrayList<>();
                if (parent1 != null) {
                    parents.add(parent1);
                }
                if (parent2 != null) {
                    parents.add(parent2);
                }
                joueur.setParents(parents);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
