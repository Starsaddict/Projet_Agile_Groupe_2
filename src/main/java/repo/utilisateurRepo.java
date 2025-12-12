package repo;

import bd.HibernateUtil;
import model.Joueur;
import model.Parent;
import model.Utilisateur;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.mdpUtil;

import java.util.List;

public class utilisateurRepo {
    public Utilisateur loadUtilisateur(Long id) {
        System.out.println("DEBUG: loadUtilisateur called with id=" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            System.out.println("DEBUG: Transaction started");
            String hql = "FROM Utilisateur WHERE idUtilisateur = :id";
            System.out.println("DEBUG: Executing HQL query: " + hql);
            Utilisateur u = session.createQuery(hql, Utilisateur.class)
                    .setParameter("id", id)
                    .uniqueResult();

            System.out.println("DEBUG: loadUtilisateur - id=" + id + ", u=" + u);

            if (u != null) {
                System.out.println("DEBUG: User found, initializing collections");
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
            }

            transaction.commit();
            System.out.println("DEBUG: Transaction committed, returning u=" + u);
            return u;
        } catch (Exception e) {
            System.out.println("DEBUG: Exception in loadUtilisateur: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("DEBUG: Exception in loadUtilisateur: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            session.close();
            return u;
        }
    }

    public Boolean resetPassword(Long id, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Utilisateur u = loadUtilisateur(id);
            u.setMdpUtilisateur(mdpUtil.mdpString(password));
            return updateUtilisateur(u);
        } catch (Exception ex) {
            return false;
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

    public Boolean updateUtilisateur(Utilisateur u) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(u);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
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

}
