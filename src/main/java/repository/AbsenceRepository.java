package repository;

import model.EtreAbsent;

public interface AbsenceRepository {

    EtreAbsent saveAbsence(EtreAbsent absence);
}