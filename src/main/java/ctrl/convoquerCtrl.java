package ctrl;

import jakarta.mail.MessagingException;
import model.*;
import repo.ConvocationEvenementRepo;
import repo.utilisateurRepo;
import service.eventService;
import util.SendEmailSSL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@WebServlet("/secretaire/convoquer")
public class convoquerCtrl extends HttpServlet {

    /* ===================== GET ===================== */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String typeParam = request.getParameter("type");
        boolean isMatch = !"event".equalsIgnoreCase(typeParam);

        eventService service = new eventService();
        List<Evenement> evenements = isMatch
                ? service.loadAllMatch()
                : service.loadEvents();

        request.setAttribute("mode", isMatch ? "match" : "event");
        request.setAttribute("evenements", evenements);
        request.getRequestDispatcher("/jsp/convocation/convoquerEvent.jsp")
                .forward(request, response);
    }

    /* ===================== POST ===================== */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");
        String idParam = request.getParameter("idEvenement");

        if (idParam == null || idParam.isBlank()) {
            request.setAttribute("messageErreur", "Aucun événement sélectionné");
            doGet(request, response);
            return;
        }

        Long idEvenement;
        try {
            idEvenement = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            request.setAttribute("messageErreur", "Id événement invalide");
            doGet(request, response);
            return;
        }

        eventService service = new eventService();
        Evenement evenement;

        if ("match".equalsIgnoreCase(type)) {
            evenement = service.findByIdWithParticipants(idEvenement);
        } else {
            evenement = service.findById(idEvenement);
        }

        if (evenement == null) {
            request.setAttribute("messageErreur", "Événement introuvable");
            doGet(request, response);
            return;
        }

        if ("match".equalsIgnoreCase(type)) {
            traiterMatch(request, evenement);
        } else {
            traiterEvenement(request, evenement);
        }

        doGet(request, response);
    }

    /* ===================== MATCH OFFICIEL ===================== */
    private void traiterMatch(HttpServletRequest request, Evenement evenement) {

        if (evenement.getGroupe() == null) return;

        ConvocationEvenementRepo matchRepo = new ConvocationEvenementRepo();

        for (Joueur joueur : evenement.getGroupe().getJoueurs()) {

            ConvocationEvenement convocation =
                    matchRepo.findByEvenementAndJoueur(
                            evenement.getIdEvenement(),
                            joueur.getIdUtilisateur()
                    );

            if (convocation == null) {
                convocation = new ConvocationEvenement();
                convocation.setEvenement(evenement);
                convocation.setJoueur(joueur);
                convocation.setToken(UUID.randomUUID().toString());
                matchRepo.save(convocation);
            }

            String lien = buildLien(request, "/confirmation/match", convocation.getToken());

            try {
                if (joueur.getEmailUtilisateur() != null) {
                    SendEmailSSL.sendJoueurInvitation(joueur, evenement, lien);
                }

                if (joueur.getParents() != null) {
                    for (Parent parent : joueur.getParents()) {
                        if (parent.getEmailUtilisateur() != null) {
                            SendEmailSSL.sendParentInvitation(parent, joueur, evenement, lien);
                        }
                    }
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /* ===================== AUTRES ÉVÉNEMENTS ===================== */
    private void traiterEvenement(HttpServletRequest request, Evenement evenement) {

        ConvocationEvenementRepo repo = new ConvocationEvenementRepo();
        utilisateurRepo utilisateurRepo = new utilisateurRepo();

        List<Joueur> joueurs = utilisateurRepo.findAllJoueursWithParents();

        for (Joueur joueur : joueurs) {

            ConvocationEvenement convocation =
                    repo.findByEvenementAndJoueur(
                            evenement.getIdEvenement(),
                            joueur.getIdUtilisateur()
                    );

            if (convocation == null) {
                convocation = new ConvocationEvenement();
                convocation.setEvenement(evenement);
                convocation.setJoueur(joueur);
                convocation.setToken(UUID.randomUUID().toString());
                repo.save(convocation);
            }

            String lien = buildLien(request, "/confirmation/evenement", convocation.getToken());

            try {
                if (joueur.getEmailUtilisateur() != null) {
                    SendEmailSSL.sendEventInvitation(joueur, evenement, lien);
                }

                if (joueur.getParents() != null) {
                    for (Parent parent : joueur.getParents()) {
                        if (parent.getEmailUtilisateur() != null) {
                            SendEmailSSL.sendEventInvitation(parent, evenement, lien);
                        }
                    }
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /* ===================== UTIL ===================== */
    private String buildLien(HttpServletRequest req, String path, String token) {
        return req.getScheme() + "://" +
               req.getServerName() + ":" +
               req.getServerPort() +
               req.getContextPath() +
               path + "?token=" + token;
    }
}
