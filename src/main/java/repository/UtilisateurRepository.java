package repository;

import model.Utilisateur;

import java.util.Optional;

public interface UtilisateurRepository {
    Optional<Utilisateur> findByEmailUtilisateur(String email, String role);
}
