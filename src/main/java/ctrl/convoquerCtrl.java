package ctrl;

import jakarta.mail.MessagingException;
import model.*;
import repo.ConvocationMatchRepo;
import service.UtilisateurService;
import service.eventService;
import util.SendEmailSSL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/secretaire/convoquer")
public class convoquerCtrl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String typeParam = request.getParameter("type");
        boolean isMatchType = !"event".equalsIgnoreCase(typeParam);

        eventService service = new eventService();
        List<Evenement> evenements = isMatchType
                ? service.loadAllMatch()
                : service.loadEvents();

        evenements = new ArrayList<>(new LinkedHashSet<>(evenements));

        request.setAttribute("mode", isMatchType ? "match" : "event");
        request.setAttribute("evenements", evenements);
        request.getRequestDispatcher("/jsp/convocation/convoquerEvent.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idEvtParam = request.getParameter("idEvenement");
        String type = request.getParameter("type");

        if (idEvtParam == null || idEvtParam.isBlank()) {
            request.setAttribute("messageErreur", "Aucun événement sélectionné.");
            doGet(request, response);
            return;
        }

        Long idEvenement;
        try {
            idEvenement = Long.parseLong(idEvtParam);
        } catch (NumberFormatException e) {
            request.setAttribute("messageErreur", "Id d'événement invalide.");
            doGet(request, response);
            return;
        }

        eventService service = new eventService();
        Evenement evenement;

        // 🔒 SÉPARATION STRICTE MATCH / AUTRE EVENT
        if ("match".equals(type)) {
            evenement = service.findByIdWithParticipants(idEvenement);
        } else {
            evenement = service.findById(idEvenement);
        }

        if (evenement == null) {
            request.setAttribute("messageErreur", "Événement introuvable.");
            doGet(request, response);
            return;
        }


        /* ======================================================
           🟥 CAS MATCH OFFICIEL — AVEC LIEN DE CONFIRMATION
           ====================================================== */
        if ("match".equals(type)) {

            if (evenement.getGroupe() == null) {
                request.setAttribute("messageErreur", "Aucun groupe associé.");
                doGet(request, response);
                return;
            }

            List<Joueur> joueurs = evenement.getGroupe().getJoueurs();
            if (joueurs == null || joueurs.isEmpty()) {
                request.setAttribute("messageErreur", "Aucun joueur dans ce groupe.");
                doGet(request, response);
                return;
            }

            ConvocationMatchRepo convocationRepo = new ConvocationMatchRepo();

            for (Joueur joueur : joueurs) {

                // 1️⃣ Créer ou récupérer la convocation
                ConvocationMatch convocation =
                        convocationRepo.findByMatchAndJoueur(
                                evenement.getIdEvenement(),
                                joueur.getIdUtilisateur()
                        );

                if (convocation == null) {
                    convocation = new ConvocationMatch();
                    convocation.setMatch(evenement);
                    convocation.setJoueur(joueur);
                    convocation.setToken(UUID.randomUUID().toString());
                    convocationRepo.save(convocation);
                }

                // 2️⃣ Construire le lien
                String lienConfirmation =
                        request.getScheme() + "://" +
                        request.getServerName() + ":" +
                        request.getServerPort() +
                        request.getContextPath() +
                        "/confirmation/match?token=" +
                        convocation.getToken();

                // 3️⃣ Envoyer mail AU JOUEUR
                if (joueur.getEmailUtilisateur() != null) {
                    try {
                        SendEmailSSL.sendJoueurInvitation(
                                joueur,
                                evenement,
                                lienConfirmation
                        );
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }

                // 4️⃣ Envoyer mail AUX PARENTS (même lien)
                if (joueur.getParents() != null) {
                    for (Parent parent : joueur.getParents()) {
                        if (parent.getEmailUtilisateur() != null) {
                            try {
                                SendEmailSSL.sendParentInvitation(
                                        parent,
                                        joueur,
                                        evenement,
                                        lienConfirmation
                                );
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
        /* ======================================================
           🟦 CAS AUTRES ÉVÉNEMENTS (TON CODE EXISTANT)
           ====================================================== */
        else {

            UtilisateurService utilisateurService = new UtilisateurService();
            List<Utilisateur> utilisateurs = utilisateurService.loadAllUtilisateurs();

            for (Utilisateur utilisateur : utilisateurs) {
                if (utilisateur.getEmailUtilisateur() != null) {
                    try {
                        SendEmailSSL.sendEventInvitation(utilisateur, evenement);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        doGet(request, response);
    }
}
