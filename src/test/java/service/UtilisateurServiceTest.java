//// java
//package service;
//
//import model.Utilisateur;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import repository.UtilisateurRepositoryImpl;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UtilisateurServiceTest {
//
//    private StubRepo repoStub;
//    private UtilisateurService service;
//
//    @BeforeEach
//    void setup() {
//        repoStub = new StubRepo();
//        service = new UtilisateurService(repoStub);
//    }
//
//    @Test
//    void authenticate_success_when_password_matches() {
//        String email = "test@example.com";
//        String rawPassword = "secret";
//        Utilisateur u = new Utilisateur();
//        u.setEmailUtilisateur(email);
//        u.setMdpUtilisateur(rawPassword); // le service compare directement
//
//        repoStub.setNext(Optional.of(u));
//
//        Optional<Utilisateur> res = service.authenticate(email, rawPassword, null);
//        assertTrue(res.isPresent());
//        assertEquals(email, res.get().getEmailUtilisateur());
//    }
//
//    @Test
//    void authenticate_empty_when_wrong_password() {
//        String email = "test2@example.com";
//        Utilisateur u = new Utilisateur();
//        u.setEmailUtilisateur(email);
//        u.setMdpUtilisateur("storedHash");
//
//        repoStub.setNext(Optional.of(u));
//
//        Optional<Utilisateur> res = service.authenticate(email, "bad", null);
//        assertFalse(res.isPresent());
//    }
//
//    // Stub simple pour remplacer Mockito sur la classe concrète
//    private static class StubRepo extends UtilisateurRepositoryImpl {
//        private Optional<Utilisateur> next = Optional.empty();
//
//        void setNext(Optional<Utilisateur> next) {
//            this.next = next;
//        }
//
//        @Override
//        public Optional<Utilisateur> findByEmailUtilisateur(String email, String role) {
//            return next;
//        }
//    }
//}