package service;

import java.util.ArrayList;
import java.util.List;

import repo.groupeRepo;
import repo.joueurRepo;
import model.Groupe;
import model.Joueur;

public class groupeService {

    private groupeRepo groupeDAO = new groupeRepo();
    private joueurRepo joueurDAO = new joueurRepo();

    // Créer un groupe avec une liste d'IDs de joueurs
    public void creerGroupe(String nomGroupe, List<Long> idsJoueurs) {
        Groupe groupe = new Groupe();
        groupe.setNomGroupe(nomGroupe);

        List<Joueur> joueurs = new ArrayList<>();
        for (Long id : idsJoueurs) {
            Joueur j = joueurDAO.findById(id);
            if (j != null) {
                joueurs.add(j);
            }
        }

        groupe.setJoueurs(joueurs);
        groupeDAO.saveGroupe(groupe);

        System.out.println("Groupe '" + nomGroupe + "' créé avec " + joueurs.size() + " joueurs.");
    }
}
