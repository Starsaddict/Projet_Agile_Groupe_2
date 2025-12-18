package service;

import model.EtreAbsent;
import model.Joueur;
import model.Parent;
import repository.AbsenceRepository;
import repository.AbsenceRepositoryImpl;

import java.time.LocalDateTime;
import java.util.Optional;

public class AbsenceService {

    private final AbsenceRepository repo;

    public AbsenceService(AbsenceRepository repo) {
        this.repo = repo;
    }

    public AbsenceService() {
        this.repo = new AbsenceRepositoryImpl();
    }

    public boolean declareAbsence(Joueur joueur, EtreAbsent.TypeAbsence type, LocalDateTime debut, LocalDateTime fin, String motif) {
        if (joueur == null || type == null) return false;

        EtreAbsent ea = new EtreAbsent();
        ea.setJoueur(joueur);
        ea.setTypeAbsence(type);
        ea.setAbsenceDebut(debut);
        ea.setAbsenceFin(fin);
        ea.setMotif(motif);

        EtreAbsent saved = repo.saveAbsence(ea);
        return saved != null;
    }



    public boolean closeAbsence(EtreAbsent absence, String fileName, String contentType, byte[] data) {
        if (absence == null) return false;
        absence.setCertificatName(fileName);
        absence.setCertificatContentType(contentType);
        absence.setCertificatData(data);
        EtreAbsent saved = repo.saveAbsence(absence);
        return saved != null;
    }

    public Optional<EtreAbsent> findOpenAbsenceForChild(Parent parent, Long idEnfant) {
        return parent.getJoueurs().stream()
                .filter(j -> j.getIdUtilisateur().equals(idEnfant))
                .findFirst()
                .flatMap(j -> j.getAbsences().stream()
                        .filter(EtreAbsent::isActive)
                        .findFirst());
    }

    public Optional<EtreAbsent> findAbsenceById(Parent parent, Long idAbsence) {
        return parent.getJoueurs().stream()
                .filter(j -> j.getAbsences() != null)
                .flatMap(j -> j.getAbsences().stream())
                .filter(a -> idAbsence.equals(a.getIdEtreAbsent()))
                .findFirst();
    }

    public Optional<EtreAbsent> findAbsenceById(Long idAbsence) {
        return repo.findById(idAbsence);
    }

    public boolean hasOpenAbsence(Joueur enfant) {
        return enfant.getAbsences() != null &&
                enfant.getAbsences().stream()
                        .anyMatch(EtreAbsent::isActive);
    }

    public boolean isCurrentlyAbsent(Joueur enfant) {
        return hasOpenAbsence(enfant);
    }
}
