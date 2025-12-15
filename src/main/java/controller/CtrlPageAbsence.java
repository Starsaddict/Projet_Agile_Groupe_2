package controller;

import model.Joueur;
import model.Parent;
import service.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/CtrlFrontAbsence")
public class CtrlPageAbsence extends HttpServlet {

    private UtilisateurService utilisateurService = new UtilisateurService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "Absence";

        HttpSession session = request.getSession(false);

        utilisateurService.getEnfantsAndAbsencesByParentId((Parent)session.getAttribute("user"));

        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
