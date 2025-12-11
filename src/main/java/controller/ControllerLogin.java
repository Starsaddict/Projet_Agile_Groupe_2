package controller;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utilisateur;
import repository.UtilisateurRepositoryImpl;
import service.UtilisateurService;

@WebServlet("/CtrlLogin")
public class ControllerLogin extends HttpServlet {

    private UtilisateurService utilisateurService;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurService = new UtilisateurService(new UtilisateurRepositoryImpl());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role_connexion");

        Optional<Utilisateur> opt = utilisateurService.authenticate(email, password, role);
        if (opt.isPresent()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", opt.get());
            request.getRequestDispatcher("Home").forward(request, response);
        } else {
            request.setAttribute("msg_connection", "Connexion échouée : identifiants invalides");
            request.getRequestDispatcher("Login").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doPost(request, response);
    }
}
