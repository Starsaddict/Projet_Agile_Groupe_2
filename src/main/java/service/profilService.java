package service;

import model.Joueur;
import model.Parent;
import org.hibernate.Session;
import org.hibernate.Transaction;
import bd.HibernateUtil;
import java.util.ArrayList;
import java.util.List;

public class profilService {
    
    public Joueur getJoueurById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Joueur joueur = session.get(Joueur.class, id);
            if (joueur != null && joueur.getParents() != null) {
                joueur.getParents().size(); // 初始化懒加载
            }
            return joueur;
        }
    }
    
    public Parent getParentById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Parent.class, id);
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
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}