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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        String typeAbsence = request.getParameter("type_absence");
        String dateDebut = request.getParameter("date_debut");
        String dateFin = request.getParameter("date_fin");
        String motif = request.getParameter("motif");

        HttpSession session = request.getSession(false);

        if (idStr == null || typeAbsence == null || session == null ||
                !(session.getAttribute("user") instanceof Parent)) {
            forwardWithMessage(request, response, "Erreur : paramètres manquants.", url);
            return;
        }

        Parent parent = (Parent) session.getAttribute("user");
        Long idEnfant = Long.parseLong(idStr);

        Joueur enfant = parent.getJoueurs().stream()
                .filter(j -> j.getIdUtilisateur().equals(idEnfant))
                .findFirst()
                .orElse(null);

        if (enfant == null) {
            forwardWithMessage(request, response, "Enfant introuvable.", url);
            return;
        }

        if (enfant.hasOpenAbsence()) {
            forwardWithMessage(request, response, "L'enfant est déjà déclaré absent.", url);
            return;
        }

        LocalDateTime debut = dateDebut != null ?
                LocalDate.parse(dateDebut).atStartOfDay() : LocalDateTime.now();
        LocalDateTime fin = "COURTE".equals(typeAbsence) && dateFin != null ?
                LocalDate.parse(dateFin).atTime(23, 59, 59) : null;

        boolean ok = absenceService.declareAbsence(enfant,
                EtreAbsent.TypeAbsence.valueOf(typeAbsence), debut, fin, motif);

        if (ok) {
            refreshUserSession(session, parent);
        }

        forwardWithMessage(request, response,
                ok ? "Absence enregistrée pour " + enfant.getPrenomUtilisateur() + "."
                        : "Erreur lors de l'enregistrement de l'absence.",
                url);
    }



    private void handleUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "Absence";
        HttpSession session = request.getSession(false);

        if (session == null || !(session.getAttribute("user") instanceof Parent)) {
            forwardWithMessage(request, response, "Erreur : parent non trouvé.", url);
            return;
        }

        Parent parent = (Parent) session.getAttribute("user");
        String idAbsenceStr = request.getParameter("id_absence");

        if (idAbsenceStr == null) {
            forwardWithMessage(request, response, "Absence non précisée.", url);
            return;
        }

        Long idAbsence = Long.parseLong(idAbsenceStr);
        Optional<EtreAbsent> opt = absenceService.findAbsenceById(parent, idAbsence);

        if (!opt.isPresent()) {
            forwardWithMessage(request, response, "Absence introuvable.", url);
            return;
        }

        EtreAbsent absence = opt.get();

        String dateFinStr = request.getParameter("date_fin_certificat");
        if (dateFinStr != null && !dateFinStr.isEmpty()) {
            LocalDateTime dateFin = LocalDate.parse(dateFinStr).atTime(23, 59, 59);
            absence.setAbsenceFin(dateFin);
        }

        Part filePart = request.getPart("certificat");

        if (filePart == null || filePart.getSize() == 0) {
            forwardWithMessage(request, response, "Aucun fichier sélectionné.", url);
            return;
        }

        String submitted = filePart.getSubmittedFileName();
        String contentType = filePart.getContentType();
        byte[] data;

        try (InputStream is = filePart.getInputStream()) {
            data = is.readAllBytes();
        } catch (Exception e) {
            forwardWithMessage(request, response, "Erreur lors de la lecture du fichier.", url);
            return;
        }

        boolean ok = absenceService.closeAbsence(absence, submitted, contentType, data);
        if (ok) {
            refreshUserSession(session, parent);
        }

        forwardWithMessage(request, response,
                ok ? "Certificat enregistré et absence clôturée."
                        : "Erreur lors de l'enregistrement.",
                url);
    }


    private void forwardWithMessage(HttpServletRequest request, HttpServletResponse response,
                                    String message, String url) throws ServletException, IOException {
        request.setAttribute("msg_absence", message);
        request.getRequestDispatcher(url).forward(request, response);
    }

    private void refreshUserSession(HttpSession session, Parent parent) {
        utilisateurService.getEnfantsAndAbsencesByParentId(parent);
        session.setAttribute("user", parent);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
