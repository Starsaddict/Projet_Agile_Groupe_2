// java
package bd;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Coach;
import model.Code;
import model.Covoiturage;
import model.EtreAbsent;
import model.EtrePresent;
import model.Evenement;
import model.Groupe;
import model.Joueur;
import model.Parent;
import model.Secretaire;
import model.Utilisateur;
import model.EnvoyerMessage;

import service.UtilisateurService;

public class BdTest {

    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // 1) Création des utilisateurs et de leurs relations
            Parent parent1 = (Parent) buildUtilisateur("Parent", "lucas.veslin@test.fr", "Veslin", "Lucas", LocalDate.of(2003, 7, 21));
            Parent parent2 = (Parent) buildUtilisateur("Parent", "oussama.lahrach@test.fr", "Lahrach", "Oussama", LocalDate.of(1902, 2, 2));
            Joueur joueur1 = (Joueur) buildUtilisateur("Joueur", "clement@test.com",  "Riols", "Clément", LocalDate.of(2018, 7, 15));
            Coach coach = (Coach) buildUtilisateur("Coach", "coach@example.com", "Berro", "Alain", LocalDate.of(1453, 5, 29));
            Secretaire secretaire = (Secretaire) buildUtilisateur("Secretaire", "sid@example.com",  "Elandaloussi", "Sid Ahmed", LocalDate.of(1989, 11, 9));

            session.save(parent1);
            session.save(parent2);
            session.save(joueur1);
            session.save(coach);
            session.save(secretaire);

            List<Parent> parents = new ArrayList<>();
            parents.add(parent1);
            parents.add(parent2);
            joueur1.setParents(parents);

            // 2) Création des groupes
            Groupe groupeA = new Groupe();
            groupeA.setNomGroupe("U13");
            Groupe groupeB = new Groupe();
            groupeB.setNomGroupe("U21");
            session.save(groupeA);
            session.save(groupeB);

            joueur1.addGroupe(groupeA);

            // 3) Création d'événements (match, entraînement, réunion)
            Evenement match = new Evenement("Match de coupe", "Stadium", LocalDateTime.of(2025, 3, 15, 14, 30), "MATCH", groupeA);
            Evenement entrainement = new Evenement("Entraînement hebdo", "Stadium", LocalDateTime.of(2026, 3, 12, 18, 0), "ENTRAINEMENT", groupeA);
            session.save(match);
            session.save(entrainement);

            // flush pour s'assurer que les ids générés sont disponibles (nécessaire pour clés composites)
            session.flush();

            // 4) Présences : EtrePresent (clé composite joueur/groupe/evenement)
            EtrePresent presenceMatch = new EtrePresent(joueur1, groupeA, match);
            presenceMatch.setConfirmerPresenceJoueur("OUI");
            presenceMatch.setConfirmerPresenceParent1("OUI");
            presenceMatch.setConfirmerPresenceParent2("NON");
            presenceMatch.setPresenceReelle(true);
            session.save(presenceMatch);

            EtrePresent presenceEntrainement = new EtrePresent(joueur1, groupeA, entrainement);
            presenceEntrainement.setConfirmerPresenceJoueur("OUI");
            session.save(presenceEntrainement);

            // 5) Absence : EtreAbsent
            EtreAbsent absence = new EtreAbsent();
            absence.setAbsenceDebut("2025-02-01");
            absence.setAbsenceTerminee(false);
            absence.setJoueur(joueur1);
            joueur1.addAbsence(absence);
            session.save(absence);

            // 6) Covoiturage et réservations
            Covoiturage covoiturage = new Covoiturage();
            covoiturage.setDateCovoiturage(LocalDateTime.of(2026, 3, 12, 12, 0));
            covoiturage.setNbPlacesMaxCovoiturage("4");
            covoiturage.setLieuDepartCovoiturage("IUT de Rodez");
            covoiturage.setEvenement(match);
            covoiturage.setConducteur(parent1);
            covoiturage.addReservation(joueur1);
            session.save(covoiturage);

            // 7) Messages entre utilisateurs (EnvoyerMessage)
            EnvoyerMessage msg1 = new EnvoyerMessage(joueur1, parent1, LocalDateTime.now().minusDays(1), "Je serai en retard pour l'entraînement");
            EnvoyerMessage msg2 = new EnvoyerMessage(parent1, coach, LocalDateTime.now().minusHours(4), "Fait jouer mon fils !");
            session.save(msg1);
            session.save(msg2);

            // 8) Codes (générés manuellement par service)
            Code registerCode = new Code("ABC12345", "Inscription");
            Code reinitializeCode = new Code("DFG54321", "Reinitialisation");
            session.save(registerCode);
            session.save(reinitializeCode);

            // 9) Affichages récapitulatifs (pour démonstration)
            System.out.println("\n--- Utilisateurs enregistrés ---");
            session.createQuery("FROM Utilisateur", Utilisateur.class).getResultList().forEach(u -> System.out.println(u));

            System.out.println("\n--- Événements ---");
            session.createQuery("FROM Evenement", Evenement.class).getResultList()
                    .forEach(e -> System.out.println(e.getIdEvenement() + " | " + e.getNomEvenement() + " | " + e.getDateEvenement()));

            System.out.println("\n--- Présences (EtrePresent) ---");
            session.createQuery("FROM EtrePresent", EtrePresent.class).getResultList()
                    .forEach(p -> System.out.println(p));

            System.out.println("\n--- Absences (EtreAbsent) ---");
            session.createQuery("FROM EtreAbsent", EtreAbsent.class).getResultList()
                    .forEach(a -> System.out.println("Absence id pour joueur: " + (a.getJoueur() != null ? a.getJoueur().getIdUtilisateur() : null)));

            System.out.println("\n--- Covoiturages ---");
            session.createQuery("FROM Covoiturage", Covoiturage.class).getResultList()
                    .forEach(c -> System.out.println("Covoiturage " + c.getIdCovoiturage() + " conducteur=" + (c.getConducteur() != null ? c.getConducteur().getIdUtilisateur() : null)));

            System.out.println("\n--- Messages ---");
            session.createQuery("FROM EnvoyerMessage", EnvoyerMessage.class).getResultList()
                    .forEach(m -> System.out.println(m));

            System.out.println("\n--- Codes ---");
            session.createQuery("FROM Code", Code.class).getResultList()
                    .forEach(c -> System.out.println(c));

            // commit final
            tx.commit();
            System.out.println("\n✔ Démonstration terminée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fabrique simple pour créer une instance concrète de Utilisateur selon le rôle demandé.
     * Evite d'instancier la classe abstraite Utilisateur.
     */

    private static Utilisateur buildUtilisateur(String role, String email, String nom, String prenom, LocalDate dateNaissance) {
        UtilisateurService us = new UtilisateurService();
        Utilisateur u= us.creerCompteUtilisateur(email,role);
        u.setNomUtilisateur(nom);
        u.setPrenomUtilisateur(prenom);
        u.setDateNaissanceUtilisateur(dateNaissance);
        switch(role) {
            case "Joueur":
                Joueur j = (Joueur) u;
                j.setNumeroJoueur(us.generateNumeroJoueur());
                return j;
            case "Parent":
                return (Parent) u;
            case "Coach":
                return (Coach) u;
            case "Secretaire":
                return (Secretaire) u;
            default:
                return null;
        }
    }
}