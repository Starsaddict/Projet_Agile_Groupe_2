package controller;

import model.EtreAbsent;
import model.Joueur;
import model.Parent;
import repository.UtilisateurRepositoryImpl;
import service.AbsenceService;
import service.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@WebServlet("/CtrlAbsence")
@MultipartConfig
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
        String action = request.getParameter("action");
        if (action == null) action = "declare";

        switch (action) {
            case "upload":
                handleUpload(request, response);
                break;
            case "declare":
            default:
                handleDeclare(request, response);
                break;
        }
    }

    private void handleDeclare(HttpServletRequest request, HttpServletResponse response)
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

        boolean dejaAbsent = enfant.getAbsences() != null &&
                enfant.getAbsences().stream().anyMatch(a -> !Boolean.TRUE.equals(a.getAbsenceTerminee()));

        if (dejaAbsent) {
            request.setAttribute("msg_absence", "L'enfant est déjà déclaré absent.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        boolean ok = absenceService.declareAbsence(enfant);
        if (ok) {
            utilisateurService.getEnfantsAndAbsencesByParentId(parent);
            session.setAttribute("user", parent);
        }

        request.setAttribute("msg_absence", ok ? "Absence enregistrée pour " + enfant.getPrenomUtilisateur() + "." : "Erreur lors de l'enregistrement de l'absence.");
        request.getRequestDispatcher(url).forward(request, response);
    }

    private void handleUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "Absence";
        HttpSession session = request.getSession(false);

        if (session == null || !(session.getAttribute("user") instanceof Parent)) {
            request.setAttribute("msg_absence", "Erreur : parent non trouvé.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        Parent parent = (Parent) session.getAttribute("user");
        String idAbsenceStr = request.getParameter("id_absence");
        if (idAbsenceStr == null) {
            request.setAttribute("msg_absence", "Absence non précisée.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        Long idAbsence = Long.parseLong(idAbsenceStr);
        Optional<EtreAbsent> opt = parent.getJoueurs().stream()
                .filter(j -> j.getAbsences() != null)
                .flatMap(j -> j.getAbsences().stream())
                .filter(a -> a.getIdEtreAbsent() != null && a.getIdEtreAbsent().equals(idAbsence))
                .findFirst();

        if (!opt.isPresent()) {
            request.setAttribute("msg_absence", "Absence introuvable.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        EtreAbsent absence = opt.get();
        Part filePart = request.getPart("certificat");
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("msg_absence", "Aucun fichier sélectionné.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        String submitted = filePart.getSubmittedFileName();
        String contentType = filePart.getContentType();
        byte[] data;
        try (InputStream is = filePart.getInputStream()) {
            data = is.readAllBytes();
        } catch (Exception e) {
            request.setAttribute("msg_absence", "Erreur lors de la lecture du fichier.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        boolean ok = absenceService.closeAbsence(absence, submitted, contentType, data);
        if (ok) {
            utilisateurService.getEnfantsAndAbsencesByParentId(parent);
            session.setAttribute("user", parent);
        }

        request.setAttribute("msg_absence", ok ? "Certificat enregistré et absence clôturée." : "Erreur lors de l'enregistrement.");
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}