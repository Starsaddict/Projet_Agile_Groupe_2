// java
package service;

import model.EtreAbsent;
import model.Joueur;
import model.Parent;
import repository.AbsenceRepository;
import repository.AbsenceRepositoryImpl;

import java.time.LocalDate;
import java.util.Optional;

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
        ea.setAbsenceDebut(LocalDate.now().atStartOfDay());
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

    public Optional<EtreAbsent> findOpenAbsenceForChild(Parent parent, Long idEnfant) {
        return parent.getJoueurs().stream()
                .filter(j -> j.getIdUtilisateur().equals(idEnfant))
                .findFirst()
                .flatMap(j -> j.getAbsences().stream()
                        .filter(a -> Boolean.FALSE.equals(a.getAbsenceTerminee()))
                        .findFirst());
    }

    public Optional<EtreAbsent> findAbsenceById(Parent parent, Long idAbsence) {
        return parent.getJoueurs().stream()
                .filter(j -> j.getAbsences() != null)
                .flatMap(j -> j.getAbsences().stream())
                .filter(a -> idAbsence.equals(a.getIdEtreAbsent()))
                .findFirst();
    }

    public boolean hasOpenAbsence(Joueur enfant) {
        return enfant.getAbsences() != null &&
                enfant.getAbsences().stream()
                        .anyMatch(a -> Boolean.FALSE.equals(a.getAbsenceTerminee()));
    }

}