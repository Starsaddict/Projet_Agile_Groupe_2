package service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Groupe;
import model.Joueur;
import bd.HibernateUtil;

public class testMain {

    public static void main(String[] args) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // ======= Créer de nouveaux joueurs =======
            Joueur j1 = new Joueur();
            j1.setNomUtilisateur("Alice");
            j1.setPrenomUtilisateur("A");
            session.save(j1);

            Joueur j2 = new Joueur();
            j2.setNomUtilisateur("Bob");
            j2.setPrenomUtilisateur("B");
            session.save(j2);

            Joueur j3 = new Joueur();
            j3.setNomUtilisateur("Charlie kirk");
            j3.setPrenomUtilisateur("C");
            session.save(j3);

            // Flush pour générer les IDs
            session.flush();

            // Mettre les joueurs dans une liste
            List<Joueur> joueurs = new ArrayList<>();
            joueurs.add(j1);
            joueurs.add(j2);
            joueurs.add(j3);

            System.out.println(" 3 nouveaux joueurs ajoutés à la table Utilisateur.");

            // ======= Créer un groupe et associer ces joueurs =======
            Groupe groupe = new Groupe();
            groupe.setNomGroupe("Equipe party Yajing");

            for (Joueur j : joueurs) {
                groupe.getJoueurs().add(j);

                if (j.getGroupes() == null) {
                    j.setGroupes(new ArrayList<>());
                }
                j.getGroupes().add(groupe);
            }

            session.save(groupe);

            System.out.println(" Groupe '" + groupe.getNomGroupe() + "' créé avec les 3 joueurs.");

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
