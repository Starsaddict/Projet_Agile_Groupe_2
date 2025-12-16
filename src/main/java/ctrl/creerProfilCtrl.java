package ctrl;

import model.Utilisateur;
import repo.utilisateurRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class creerProfilCtrl extends HttpServlet {

    private final utilisateurRepo utilisateurRepo = new utilisateurRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<Utilisateur> newAccounts = session != null
                ? (List<Utilisateur>) session.getAttribute("newAccounts")
                : null;

        if (newAccounts == null || newAccounts.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Login?error=Session+expiree");
            return;
        }

        request.setAttribute("newAccounts", newAccounts);
        request.getRequestDispatcher("/jsp/creerProfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<Utilisateur> sessionAccounts = session != null
                ? (List<Utilisateur>) session.getAttribute("newAccounts")
                : null;

        if (sessionAccounts == null || sessionAccounts.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Login?error=Session+expiree");
            return;
        }

        String[] ids = request.getParameterValues("userId");
        String[] mdps = request.getParameterValues("mdp");
        String[] confirms = request.getParameterValues("mdpConfirm");

        if (ids == null || mdps == null || confirms == null) {
            response.sendRedirect(request.getContextPath() + "/Login?error=Champs+manquants");
            return;
        }

        List<Long> updated = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            try {
                Long id = Long.parseLong(ids[i]);
                String mdp = mdps[i] != null ? mdps[i] : "";
                String confirm = confirms[i] != null ? confirms[i] : "";

                if (mdp.isEmpty() && confirm.isEmpty()) {
                    // Skip if nothing entered (possible pour joueurs optionnels)
                    continue;
                }
                if (!mdp.equals(confirm)) {
                    response.sendRedirect(request.getContextPath() + "/creerProfil?error=Mot+de+passe+different");
                    return;
                }

                Utilisateur u = utilisateurRepo.loadUtilisateur(id);
                if (u != null) {
                    u.setMdpUtilisateur(mdp);
                    utilisateurRepo.updateUtilisateur(u);
                    updated.add(id);
                }
            } catch (NumberFormatException ignore) {
            }
        }

        // Nettoyer la session et rediriger vers login
        if (session != null) {
            session.removeAttribute("newAccounts");
        }
        response.sendRedirect(request.getContextPath() + "/Login?success=inscription");
    }
}
