package service;

import model.Evenement;
import repo.EvenementRepo;

import java.util.Collections;
import java.util.List;

public class eventService {

    private final EvenementRepo evenementRepo = new EvenementRepo();

    /* ================= MATCH OFFICIEL ================= */
    public List<Evenement> loadAllMatch() {
        List<Evenement> matches = evenementRepo.findAllMatch();
        return matches != null ? matches : Collections.emptyList();
    }

    /* ================= AUTRES EVENEMENTS ================= */
    public List<Evenement> loadEvents() {
        List<Evenement> events = evenementRepo.findAllNonMatch();
        return events != null ? events : Collections.emptyList();
    }

    /* ================= EVENT SIMPLE (sans groupe) ================= */
    public Evenement findById(long id) {
        return evenementRepo.findById(id);
    }

    /* ================= MATCH (avec groupe + joueurs + parents) ================= */
    public Evenement findByIdWithParticipants(long id) {
        return evenementRepo.findByIdWithGroupeAndJoueurs(id);
    }
}
