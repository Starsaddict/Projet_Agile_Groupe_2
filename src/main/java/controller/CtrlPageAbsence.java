package controller;

import model.Parent;
import service.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/CtrlFrontAbsence")
public class CtrlPageAbsence extends HttpServlet {

    private UtilisateurService utilisateurService = new UtilisateurService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "Absence";
        HttpSession session = request.getSession(false);
        if (session == null || !(session.getAttribute("user") instanceof Parent)) { //vérifie la connexion
            request.getRequestDispatcher("Login").forward(request, response);
            return;
        }

        Parent parent = (Parent) session.getAttribute("user");
        utilisateurService.getEnfantsAndAbsencesByParentId(parent);

        Object msg = session.getAttribute("msg_absence");
        if (msg != null) {
            request.setAttribute("msg_absence", msg);
            session.removeAttribute("msg_absence");
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}