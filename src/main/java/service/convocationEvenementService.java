package service;

import model.ConvocationEvenement;
import model.ConvocationEvenementId;
import repo.ConvocationEvenementRepo;

import java.util.Collections;
import java.util.List;

public class convocationEvenementService {

    private final ConvocationEvenementRepo convocationRepo = new ConvocationEvenementRepo();

    public ConvocationEvenement findById(ConvocationEvenementId id) {
        if (id == null) {
            return null;
        }
        return convocationRepo.findById(id);
    }

    public List<ConvocationEvenement> findConfirmedByEvenement(Long idEvenement) {
        if (idEvenement == null) {
            return Collections.emptyList();
        }
        List<ConvocationEvenement> list = convocationRepo.findConfirmedByEvenement(idEvenement);
        return list != null ? list : Collections.emptyList();
    }

    public void updatePresenceReelle(ConvocationEvenementId convocationId, boolean presenceReelle) {
        if (convocationId == null) {
            throw new IllegalArgumentException("Id ConvocationEvenement invalide");
        }
        convocationRepo.updatePresenceReelle(convocationId, presenceReelle);
    }
}
