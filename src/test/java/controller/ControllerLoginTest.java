// java
// File: `src/test/java/controller/ControllerLoginTest.java`
package controller;

import model.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ControllerLoginTest {

    private ControllerLogin ctrl;

    @BeforeEach
    void setup() {
        ctrl = new ControllerLogin();
    }

    private void injectService(Object svc) throws Exception {
        Field f = ControllerLogin.class.getDeclaredField("utilisateurService");
        f.setAccessible(true);
        f.set(ctrl, svc);
    }

    @Test
    void doPost_setsSessionUser_and_forwardsToHome_onSuccess() throws Exception {
        // stub UtilisateurService sans Mockito (évite Byte Buddy / Java 23 issue)
        service.UtilisateurService stubService = new service.UtilisateurService() {
            @Override
            public Optional<Utilisateur> authenticate(String email, String password, String role) {
                Utilisateur u = new Utilisateur();
                u.setEmailUtilisateur(email);
                return Optional.of(u);
            }
        };
        injectService(stubService);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher rd = mock(RequestDispatcher.class);

        when(req.getParameter("email")).thenReturn("a@a.com");
        when(req.getParameter("password")).thenReturn("pw");
        when(req.getParameter("role_connexion")).thenReturn(null);
        when(req.getSession(true)).thenReturn(session);
        when(req.getRequestDispatcher("Home")).thenReturn(rd);

        ctrl.doPost(req, resp);

        verify(session).setAttribute(eq("user"), any(Utilisateur.class));
        verify(rd).forward(req, resp);
    }

    @Test
    void doPost_forwardsToLogin_onFailure() throws Exception {
        // stub UtilisateurService qui renvoie empty
        service.UtilisateurService stubService = new service.UtilisateurService() {
            @Override
            public Optional<Utilisateur> authenticate(String email, String password, String role) {
                return Optional.empty();
            }
        };
        injectService(stubService);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher rd = mock(RequestDispatcher.class);

        when(req.getParameter("email")).thenReturn("x@x.com");
        when(req.getParameter("password")).thenReturn("bad");
        when(req.getParameter("role_connexion")).thenReturn(null);
        when(req.getRequestDispatcher("Login")).thenReturn(rd);

        ctrl.doPost(req, resp);

        verify(rd).forward(req, resp);
        verify(req).setAttribute(eq("msg_connection"), any());
    }
}