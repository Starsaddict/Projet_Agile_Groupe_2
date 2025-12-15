package ctrl;

import repo.codeRepo;
import model.Code;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class inscriptionCtrl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login?error=Code+manquant");
            return;
        }

        codeRepo codeRepo = new codeRepo();
        Code c = codeRepo.findByCode(code);
        if (c == null || !"Inscription".equals(c.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login?error=Code+invalide");
            return;
        }

        request.setAttribute("invitationCode", code);
        request.getRequestDispatcher("/jsp/inscription.jsp").forward(request, response);
    }
}
