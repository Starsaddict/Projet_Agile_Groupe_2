package service;

import model.Evenement;
import model.Joueur;
import repo.EvenementRepo;

import java.util.ArrayList;
import java.util.List;

public class eventService {

    private final EvenementRepo evenementRepo = new EvenementRepo();

    public List<Evenement> loadAllMatch() {
        try {
            List<Evenement> matches = evenementRepo.findAllMatch();
            return matches != null ? matches : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Evenement> loadEvents() {
        try {
            List<Evenement> events = evenementRepo.findAllNonMatch();
            return events != null ? events : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Evenement findById(long id) {
        try {
            Evenement evenement = evenementRepo.findById(id);
            return evenement;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Evenement findByIdWithParticipants(long id) {
        try {
            return evenementRepo.findByIdWithGroupeAndJoueurs(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
