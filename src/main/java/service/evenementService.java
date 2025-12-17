package service;

import model.Evenement;
import repo.EvenementRepo;

import java.util.List;

public class evenementService {

    private final EvenementRepo repo = new EvenementRepo();

    public List<Evenement> findAllEvenements() {
        return repo.findAll();
    }
    
    public List<Evenement> findEvenementsFuturs() {
        return repo.findFuturs();
    }

    public Evenement findById(Long id) {
        return repo.findById(id);
    }
}
