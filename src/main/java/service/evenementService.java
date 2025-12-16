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

}
