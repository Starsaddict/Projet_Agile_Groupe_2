package ctrl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import model.Utilisateur;
import repo.utilisateurRepo;

public class secretaireModifierCompteCtrl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        utilisateurRepo utilisateurRepo = new utilisateurRepo();
        List<Utilisateur> utilisateurs;
        try {
            utilisateurs = utilisateurRepo.loadAllUtilisateurs();
        } catch (Throwable e) {
            utilisateurs = java.util.Collections.emptyList();
            request.setAttribute("error", "Impossible de charger les comptes : " + e.getMessage());
        }
        request.setAttribute("utilisateurs", utilisateurs);

        request.getRequestDispatcher(
                "/jsp/secretaire/modifier.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
}
