package bd;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Coach;
import model.Covoiturage;
import model.EtreAbsent;
import model.EtrePresent;
import model.EtrePresent_id;
import model.Evenement;
import model.Groupe;
import model.Joueur;
import model.Parent;
import model.Secretaire;
import model.Utilisateur;

public class BdTest {

    /*public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Utilisateur conducteur = buildUtilisateur("conducteur@example.com", "pwd1", "Driver", "One", LocalDate.of(1990, 1, 1));
            Utilisateur passager = buildUtilisateur("passager@example.com", "pwd2", "Passenger", "Two", LocalDate.of(1992, 2, 2));
            session.save(conducteur);
            session.save(passager);

            Parent parent = new Parent();
            parent.setEmailUtilisateur("parent@example.com");
            parent.setNomUtilisateur("Parent");
            parent.setPrenomUtilisateur("P");
            session.save(parent);

            Joueur joueur = new Joueur();
            joueur.setEmailUtilisateur("lucas@test.com");
            joueur.setMdpUtilisateur("test");
            joueur.setNomUtilisateur("Joueur");
            joueur.setPrenomUtilisateur("J");
            session.save(joueur);

            Coach coach = new Coach();
            coach.setNomUtilisateur("Coach");
            coach.setPrenomUtilisateur("C");
            session.save(coach);

            Secretaire secretaire = new Secretaire();
            secretaire.setNomUtilisateur("Secretaire");
            secretaire.setPrenomUtilisateur("S");
            session.save(secretaire);

            Groupe groupe = new Groupe();
            groupe.setNomGroupe("Groupe A");
            session.save(groupe);

            Evenement evenement = new Evenement();
            evenement.setNomEvenement("Match");
            evenement.setLieuEvenement("Stadium");
            evenement.setDateEvenement(LocalDateTime.of(2024, 1, 1, 10, 0));
            evenement.setTypeEvenement("GAME");
            evenement.setGroupe(groupe);
            session.save(evenement);
            
         // ================= SUPPRESSION DE L'ÉVÉNEMENT MODIFIÉ =================
           // session.delete(evenement);
           // System.out.println("🗑 Événement modifié supprimé");


            // ================= AJOUT DE 3 AUTRES ÉVÉNEMENTS =================

            Evenement e2 = new Evenement();
            e2.setNomEvenement("Entraînement Mardi");
            e2.setLieuEvenement("Salle A");
            e2.setDateEvenement(LocalDateTime.of(2024, 1, 3, 18, 0));
            e2.setTypeEvenement("ENTRAINEMENT");
            e2.setGroupe(groupe);
            session.save(e2);

            Evenement e3 = new Evenement();
            e3.setNomEvenement("Tournoi Régional");
            e3.setLieuEvenement("Complexe Sportif");
            e3.setDateEvenement(LocalDateTime.of(2024, 1, 10, 9, 0));
            e3.setTypeEvenement("TOURNOI");
            e3.setGroupe(groupe);
            session.save(e3);

            Evenement e4 = new Evenement();
            e4.setNomEvenement("Réunion Parents");
            e4.setLieuEvenement("Salle de réunion");
            e4.setDateEvenement(LocalDateTime.of(2024, 1, 5, 20, 0));
            e4.setTypeEvenement("REUNION");
            e4.setGroupe(groupe);
            session.save(e4);

            System.out.println("✅ 3 événements ajoutés");


            // ================= AFFICHAGE FINAL =================
            System.out.println("\n📋 LISTE FINALE DES ÉVÉNEMENTS :");

            for (Evenement e : session
                    .createQuery("FROM Evenement", Evenement.class)
                    .getResultList()) {

                System.out.println("📅 " + e.getIdEvenement()
                        + " | " + e.getNomEvenement()
                        + " | " + e.getLieuEvenement()
                        + " | " + e.getDateEvenement());
            }


            
            
            
            
            

            Covoiturage covoiturage = new Covoiturage();
            covoiturage.setDateCovoiturage(LocalDateTime.of(2024, 2, 1, 8, 30));
            covoiturage.setNbPlacesMax("4");
            covoiturage.setLieuDepart("City Center");
            covoiturage.setEvenement(evenement);
            covoiturage.setConducteur(conducteur);
            covoiturage.addUtilisateur(passager);
            session.save(covoiturage);

            EtreAbsent absence = new EtreAbsent();
            absence.setCertificat("certificat.pdf");
            absence.setAbsenceDebut("2024-01-01");
            absence.setAbsenceTerminee(true);
            absence.setJoueur(joueur);
            session.save(absence);

            session.flush(); // ensure generated IDs are available for composite keys

            EtrePresent_id epId = new EtrePresent_id(joueur.getIdUtilisateur(), groupe.getIdGroupe(), evenement.getIdEvenement());
            EtrePresent etrePresent = new EtrePresent(epId);
            etrePresent.setJoueur(joueur);
            etrePresent.setGroupe(groupe);
            etrePresent.setEvenement(evenement);
            etrePresent.setConfirmerPresenceJoueur("YES");
            etrePresent.setConfirmerPresenceParent1("NO");
            etrePresent.setConfirmerPresenceParent2("MAYBE");
            etrePresent.setPresenceReelle(true);
            session.save(etrePresent);
            
            
            
            
            
            
            
            
            

            tx.commit();
            System.out.println("C'est tout bon");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Utilisateur buildUtilisateur(String email, String pwd, String nom, String prenom, LocalDate dateNaissance) {
        Utilisateur u = new Utilisateur();
        u.setEmailUtilisateur(email);
        u.setMdpUtilisateur(pwd);
        u.setNomUtilisateur(nom);
        u.setPrenomUtilisateur(prenom);
        u.setDateNaissanceUtilisateur(dateNaissance);
        return u;
    }*/
}
