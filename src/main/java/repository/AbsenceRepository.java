package repository;

import model.EtreAbsent;

public interface AbsenceRepository {

    EtreAbsent saveAbsence(EtreAbsent absence);

    java.util.Optional<EtreAbsent> findById(Long idAbsence);
}
