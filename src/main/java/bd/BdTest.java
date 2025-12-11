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

    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Utilisateur conducteur = buildUtilisateur("conducteur@example.com", "pwd1", "Driver", "One", LocalDate.of(1990, 1, 1));
            Utilisateur passager = buildUtilisateur("passager@example.com", "pwd2", "Passenger", "Two", LocalDate.of(1992, 2, 2));
            session.save(conducteur);
            session.save(passager);

            // Création des parents
            Parent parent1 = buildParent("parent1@example.com", "pwd_parent1", "Parent", "Un", LocalDate.of(1985, 3, 3));
            Parent parent2 = buildParent("parent2@example.com", "pwd_parent2", "Parent", "Deux", LocalDate.of(1986, 4, 4));

            // Création des joueurs
            Joueur joueur1 = buildJoueur("lucas@test.com", "test", "Joueur", "Lucas", LocalDate.of(2005, 8, 15));
            Joueur joueur2 = buildJoueur("lea@test.com", "test2", "Joueuse", "Lea", LocalDate.of(2006, 9, 20));

            // Lier les parents aux joueurs
            // Lucas a deux parents
            parent1.addEnfant(joueur1);
            parent2.addEnfant(joueur1);
            // Lea a un parent
            parent1.addEnfant(joueur2);

            session.save(parent1);
            session.save(parent2);
            session.save(joueur1);
            session.save(joueur2);

            Coach coach = buildCoach("coach@example.com", "pwd_coach", "Coach", "C", LocalDate.of(1980, 4, 4));
            session.save(coach);

            Secretaire secretaire = buildSecretaire("secretaire@example.com", "pwd_sec", "Secretaire", "S", LocalDate.of(1975, 5, 5));
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

            System.out.println("✅ 4 événements ajoutés");

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
            absence.setJoueur(joueur1);
            session.save(absence);

            session.flush();

            EtrePresent_id epId = new EtrePresent_id(joueur1.getIdUtilisateur(), groupe.getIdGroupe(), evenement.getIdEvenement());
            EtrePresent etrePresent = new EtrePresent(epId);
            etrePresent.setJoueur(joueur1);
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
    }

    private static Parent buildParent(String email, String pwd, String nom, String prenom, LocalDate dateNaissance) {
        Parent u = new Parent();
        u.setEmailUtilisateur(email);
        u.setMdpUtilisateur(pwd);
        u.setNomUtilisateur(nom);
        u.setPrenomUtilisateur(prenom);
        u.setDateNaissanceUtilisateur(dateNaissance);
        return u;
    }

    private static Joueur buildJoueur(String email, String pwd, String nom, String prenom, LocalDate dateNaissance) {
        Joueur u = new Joueur();
        u.setEmailUtilisateur(email);
        u.setMdpUtilisateur(pwd);
        u.setNomUtilisateur(nom);
        u.setPrenomUtilisateur(prenom);
        u.setDateNaissanceUtilisateur(dateNaissance);
        return u;
    }

    private static Coach buildCoach(String email, String pwd, String nom, String prenom, LocalDate dateNaissance) {
        Coach u = new Coach();
        u.setEmailUtilisateur(email);
        u.setMdpUtilisateur(pwd);
        u.setNomUtilisateur(nom);
        u.setPrenomUtilisateur(prenom);
        u.setDateNaissanceUtilisateur(dateNaissance);
        return u;
    }

    private static Secretaire buildSecretaire(String email, String pwd, String nom, String prenom, LocalDate dateNaissance) {
        Secretaire u = new Secretaire();
        u.setEmailUtilisateur(email);
        u.setMdpUtilisateur(pwd);
        u.setNomUtilisateur(nom);
        u.setPrenomUtilisateur(prenom);
        u.setDateNaissanceUtilisateur(dateNaissance);
        return u;
    }
}