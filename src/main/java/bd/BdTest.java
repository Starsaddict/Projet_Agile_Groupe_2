package bd;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.*;

public class BdTest {

    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Random rand = new Random();

         // ======================= 1) Utilisateurs =======================
            Secretaire secretaire = (Secretaire) buildUtilisateur("Secretaire", "lahrach.oussama01@gmail.com",
                    "Clément", "Riols", LocalDate.of(1989, 11, 9));

            Coach coach = (Coach) buildUtilisateur("Coach", "coach1.test@test.com", "Sid Ahmed", "Elandaloussi",
                    LocalDate.of(1980, 5, 29));

            Coach coach2 = (Coach) buildUtilisateur("Coach", "coach2.test@test.com", "Sabrina", "Flavien",
                    LocalDate.of(1982, 5, 29));

            session.save(secretaire);
            session.save(coach);
            session.save(coach2);

	         // ======================= 2) Parents =======================
	
	         // Parent spécial
	         Parent parentSpecial = (Parent) buildUtilisateur(
	                 "Parent",
	                 "yasmine.aloukas28@gmail.com",
	                 "Doe",
	                 "John",
	                 LocalDate.of(1975, 6, 15)
	         );
	         session.save(parentSpecial);
	
	         List<String> parentPrenoms = Arrays.asList("Lucas", "Oussama", "Marie", "Élodie", "Hassan", "Claire");
	         List<String> parentNoms = Arrays.asList("Veslin", "Lahrach", "Dupont", "Moreau", "Benali", "Petit");
	
	         List<Parent> parents = new ArrayList<>();
	         Map<Parent, Integer> parentChildCount = new HashMap<>();
	
	         for (int i = 0; i < 6; i++) {
	             Parent p = (Parent) buildUtilisateur(
	                     "Parent",
	                     "parent" + (i + 1) + "@test.com",
	                     parentNoms.get(i),
	                     parentPrenoms.get(i),
	                     LocalDate.of(1970 + i, rand.nextInt(12) + 1, rand.nextInt(28) + 1)
	             );
	             session.save(p);
	             parents.add(p);
	             parentChildCount.put(p, 0);
	         }
	
	         // ajouter le parent spécial
	         parents.add(parentSpecial);
	         parentChildCount.put(parentSpecial, 0);


            // ======================= 3) Groupes =======================
            Groupe groupeA = new Groupe();
            groupeA.setNomGroupe("U13");
            Groupe groupeB = new Groupe();
            groupeB.setNomGroupe("U21");
            session.save(groupeA);
            session.save(groupeB);

         // ======================= 4) Joueurs =======================
            List<Joueur> joueurs = new ArrayList<>();

            // Joueur spécial (fils du parent spécial)
            Joueur joueurSpecial = (Joueur) buildUtilisateur(
                    "Joueur",
                    "yasminealoukas2002@gmail.com",
                    "Doe",
                    "Junior",
                    LocalDate.of(2011, 3, 20)
            );
            
            Joueur joueurSpecial2 = (Joueur) buildUtilisateur(
                    "Joueur",
                    "lucas.veslin@outlook.fr",
                    "Lukas",
                    "Veslin",
                    LocalDate.of(2011, 3, 20)
            );

            joueurSpecial.setParents(List.of(parentSpecial));
            parentChildCount.put(parentSpecial, 1);
            joueurSpecial.addGroupe(groupeA);

            session.save(joueurSpecial);
            joueurs.add(joueurSpecial);

            session.save(joueurSpecial2);
            joueurs.add(joueurSpecial2);
            
            
            
            List<String> joueurPrenoms = Arrays.asList(
                    "Léo","Emma","Gabriel","Chloé","Louis","Jade","Lucas","Manon","Nathan","Louise",
                    "Raphaël","Sarah","Arthur","Inès","Hugo","Camille","Ethan","Lina","Maxime","Zoé"
            );

            List<String> joueurNoms = Arrays.asList(
                    "Martin","Bernard","Thomas","Petit","Robert","Richard","Durand","Dubois","Moreau","Laurent",
                    "Simon","Michel","Lefebvre","Garcia","David","Bertrand","Roux","Vincent","Fournier","Morel"
            );

            for (int i = 0; i < 20; i++) {
                Joueur j = (Joueur) buildUtilisateur(
                        "Joueur",
                        "joueur" + (i + 1) + "@test.com",
                        joueurNoms.get(i),
                        joueurPrenoms.get(i),
                        LocalDate.of(2010, rand.nextInt(12) + 1, rand.nextInt(28) + 1)
                );

                List<Parent> pList = new ArrayList<>();
                Collections.shuffle(parents);

                for (Parent p : parents) {
                    if (pList.size() >= 2) break;
                    if (parentChildCount.get(p) < 3) {
                        pList.add(p);
                        parentChildCount.put(p, parentChildCount.get(p) + 1);
                    }
                }

                j.setParents(pList);

                if (i < 10)
                    j.addGroupe(groupeA);
                else
                    j.addGroupe(groupeB);

                session.save(j);
                joueurs.add(j);
            }


            // ======================= 5) Événements =======================
            Evenement match = new Evenement("Match de coupe", "Stadium Toulouse", LocalDateTime.of(2026, 3, 15, 14, 30),
                    "MATCH_OFFICIEL", groupeA);
            Evenement entrainement = new Evenement("Entraînement", "Toulouse Rugby Club",
                    LocalDateTime.of(2026, 3, 12, 18, 0), "ENTRAINEMENT", groupeA);

            
            
            session.save(match);
            session.save(entrainement);

            session.flush(); // ids disponibles

            // ======================= 6) Absences =======================
            EtreAbsent absence = new EtreAbsent();
            absence.setAbsenceDebut(LocalDateTime.of(2025, 3, 12, 14, 30));
            absence.setJoueur(joueurs.get(0)); // 1er joueur
            joueurs.get(0).addAbsence(absence);
            session.save(absence);

            // ======================= 7) Covoiturages =======================
            Covoiturage covoiturage = new Covoiturage();
            covoiturage.setDateCovoiturage(LocalDateTime.of(2026, 3, 12, 12, 0));
            covoiturage.setNbPlacesMaxCovoiturage(4);
            covoiturage.setLieuDepartCovoiturage("METRO JEAN JAURES");
            covoiturage.setEvenement(match);
            covoiturage.setConducteur(parents.get(0));
            session.save(covoiturage);

            // ======================= 8) Messages =======================
            EnvoyerMessage msg1 = new EnvoyerMessage(joueurs.get(0), parents.get(0), LocalDateTime.now().minusDays(1),
                    "Je serai en retard pour l'entraînement");
            EnvoyerMessage msg2 = new EnvoyerMessage(parents.get(0), coach, LocalDateTime.now().minusHours(4), "Fait jouer mon fils !");
            session.save(msg1);
            session.save(msg2);

            // ======================= 9) Codes =======================
            Code registerCode = new Code("ABC12345", "Inscription");
            Code reinitializeCode = new Code("DFG54321", "Reinitialisation");
            session.save(registerCode);
            session.save(reinitializeCode);

            tx.commit();
            System.out.println("✔ Base test remplie avec 20 joueurs, parents, groupes, covoiturages, événements, absences, messages et codes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Utilisateur buildUtilisateur(String role, String email, String nom, String prenom, LocalDate dateNaissance) {
        Utilisateur u;
        switch (role) {
            case "Joueur":
                Joueur j = new Joueur();
                j.setEmailUtilisateur(email);
                j.setNomUtilisateur(nom);
                j.setPrenomUtilisateur(prenom);
                j.setMdpUtilisateur("pwd");
                j.setDateNaissanceUtilisateur(dateNaissance);
                j.setProfilePicRoute("/img/joueur_avatar/default.png");
                u = j;
                break;
            case "Parent":
                Parent p = new Parent();
                p.setEmailUtilisateur(email);
                p.setNomUtilisateur(nom);
                p.setPrenomUtilisateur(prenom);
                p.setMdpUtilisateur("pwd");
                p.setDateNaissanceUtilisateur(dateNaissance);
                u = p;
                break;
            case "Coach":
                Coach c = new Coach();
                c.setEmailUtilisateur(email);
                c.setNomUtilisateur(nom);
                c.setPrenomUtilisateur(prenom);
                c.setMdpUtilisateur("pwd");
                c.setDateNaissanceUtilisateur(dateNaissance);
                u = c;
                break;
            case "Secretaire":
                Secretaire s = new Secretaire();
                s.setEmailUtilisateur(email);
                s.setNomUtilisateur(nom);
                s.setPrenomUtilisateur(prenom);
                s.setMdpUtilisateur("pwd");
                s.setDateNaissanceUtilisateur(dateNaissance);
                u = s;
                break;
            default:
                throw new IllegalArgumentException("Rôle inconnu : " + role);
        }
        return u;
    }
}
