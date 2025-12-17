package controller;

import model.EtreAbsent;
import model.Parent;
import service.AbsenceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@WebServlet("/DownloadCertificat")
public class ControllerDownloadCertificat extends HttpServlet {

    private AbsenceService absenceService;

    @Override
    public void init() throws ServletException {
        super.init();
        absenceService = new AbsenceService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !(session.getAttribute("user") instanceof Parent)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé");
            return;
        }

        Parent parent = (Parent) session.getAttribute("user");
        String idAbsenceStr = request.getParameter("id");

        if (idAbsenceStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID absence manquant");
            return;
        }

        Long idAbsence = Long.parseLong(idAbsenceStr);
        Optional<EtreAbsent> opt = absenceService.findAbsenceById(parent, idAbsence);

        if (!opt.isPresent()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Absence introuvable");
            return;
        }

        EtreAbsent absence = opt.get();

        if (!absence.hasCertificat()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Aucun certificat disponible");
            return;
        }

        response.setContentType(absence.getCertificatContentType());
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + absence.getCertificatName() + "\"");
        response.setContentLength(absence.getCertificatData().length);

        try (OutputStream out = response.getOutputStream()) {
            out.write(absence.getCertificatData());
            out.flush();
        }
    }
}