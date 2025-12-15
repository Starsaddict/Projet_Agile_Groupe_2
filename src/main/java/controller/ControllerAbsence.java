package controller;

import model.Joueur;
import model.Parent;
import repository.UtilisateurRepositoryImpl;
import service.AbsenceService;
import service.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/CtrlAbsence")
public class ControllerAbsence extends HttpServlet {

    private UtilisateurService utilisateurService;
    private AbsenceService absenceService;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurService = new UtilisateurService(new UtilisateurRepositoryImpl());
        absenceService = new AbsenceService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "Absence";
        String idStr = request.getParameter("id_enfant");
        HttpSession session = request.getSession(false);

        if (idStr == null || session == null || !(session.getAttribute("user") instanceof Parent)) {
            request.setAttribute("msg_absence", "Erreur : enfant ou parent non trouvé.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        Parent parent = (Parent) session.getAttribute("user");
        Long idEnfant = Long.parseLong(idStr);
        Joueur enfant = parent.getJoueurs().stream()
                .filter(j -> j.getIdUtilisateur().equals(idEnfant))
                .findFirst()
                .orElse(null);

        if (enfant == null) {
            request.setAttribute("msg_absence", "Enfant introuvable.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        // Vérification pour éviter double insertion (F5) : existe‑t‑il déjà une absence non terminée ?
        boolean dejaAbsent = enfant.getAbsences() != null &&
                enfant.getAbsences().stream().anyMatch(a -> !a.getAbsenceTerminee());

        if (dejaAbsent) {
            request.setAttribute("msg_absence", "L'enfant est déjà déclaré absent.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        boolean ok = absenceService.declareAbsence(enfant);
        if (ok) {
            // Rafraîchir les enfants et leurs absences pour l'utilisateur en session
            utilisateurService.getEnfantsAndAbsencesByParentId(parent);
            session.setAttribute("user", parent);
        }

        request.setAttribute("msg_absence", ok ? "Absence enregistrée pour " + enfant.getPrenomUtilisateur() + "." : "Erreur lors de l'enregistrement de l'absence.");
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}