package service;

import model.EtrePresent;
import repo.etrePresentRepo;
import model.EtrePresentId;

public class etrePresentService {

    private final etrePresentRepo etrePresentRepo;

    public etrePresentService() {
        this.etrePresentRepo = new etrePresentRepo();
    }

    public EtrePresent getEtrePresentById(EtrePresentId id) {
        if (id == null) {
            return null;
        }

        return etrePresentRepo.getEtrePresentById(id);
    }

    public void updatePresenceReelle(EtrePresentId etrePresentId, boolean presenceReelle) {
        if (etrePresentId == null) {
            throw new IllegalArgumentException("Id EtrePresent invalide");
        }

        etrePresentRepo.updatePresenceReelle(etrePresentId, presenceReelle);
    }
}
