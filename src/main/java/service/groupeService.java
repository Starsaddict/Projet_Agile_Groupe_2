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

    /**
     * Crée un groupe avec une liste d'IDs de joueurs.
     * Vérifie que tous les joueurs existent.
     * @param nomGroupe Nom du groupe
     * @param idsJoueurs Liste des IDs des joueurs
     * @return le groupe créé
     * @throws IllegalArgumentException si un joueur n'existe pas ou liste vide
     */
    public Groupe creerGroupe(String nomGroupe, List<Long> idsJoueurs) {

        if (idsJoueurs == null || idsJoueurs.isEmpty()) {
            throw new IllegalArgumentException("La liste des joueurs ne peut pas être vide.");
        }

        List<Joueur> joueurs = new ArrayList<>();
        for (Long id : idsJoueurs) {
            Joueur j = joueurDAO.findById(id);
            if (j == null) {
                throw new IllegalArgumentException(
                    "Le joueur avec l'id " + id + " n'existe pas dans la base."
                );
            }
            joueurs.add(j);
        }

        Groupe groupe = new Groupe();
        groupe.setNomGroupe(nomGroupe);
        groupe.setJoueurs(joueurs);

        groupeDAO.saveGroupe(groupe);

        System.out.println("Groupe '" + nomGroupe + "' créé avec " + joueurs.size() + " joueurs.");

        return groupe;
    }
}
