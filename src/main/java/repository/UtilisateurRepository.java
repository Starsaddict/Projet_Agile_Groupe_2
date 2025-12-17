package repository;

import model.Joueur;
import model.Parent;
import model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository {
    Optional<Utilisateur> findByEmailUtilisateur(String email, String role);

    Optional<Utilisateur> findFirstByEmail(String email);

    List<String> findRolesByEmail(String email);

    void findEnfantsAndAbsencesByParentId(Parent p);
}
