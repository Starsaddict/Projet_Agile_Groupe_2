// java
package service;

import model.EtreAbsent;
import model.Joueur;
import repository.AbsenceRepository;
import repository.AbsenceRepositoryImpl;

import java.time.LocalDate;

public class AbsenceService {

    private final AbsenceRepository repo;

    public AbsenceService(AbsenceRepository repo) {
        this.repo = repo;
    }

    public AbsenceService() {
        this.repo = new AbsenceRepositoryImpl();
    }

    public boolean declareAbsence(Joueur joueur) {
        if (joueur == null) return false;
        EtreAbsent ea = new EtreAbsent();
        ea.setJoueur(joueur);
        ea.setAbsenceDebut(LocalDate.now().toString());
        ea.setAbsenceTerminee(false);
        EtreAbsent saved = repo.saveAbsence(ea);
        return saved != null;
    }

    public boolean closeAbsence(EtreAbsent absence, String fileName, String contentType, byte[] data) {
        if (absence == null) return false;
        absence.setCertificatName(fileName);
        absence.setCertificatContentType(contentType);
        absence.setCertificatData(data);
        absence.setAbsenceTerminee(true);
        EtreAbsent saved = repo.saveAbsence(absence);
        return saved != null;
    }
}