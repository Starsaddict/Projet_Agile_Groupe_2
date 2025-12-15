package ctrl;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jakarta.mail.MessagingException;
import model.*;
import service.UtilisateurService;
import service.eventService;
import util.SendEmailSSL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static util.SendEmailSSL.sendEventInvitation;

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

        // Deduplicate because of join fetch on joueurs
        evenements = new ArrayList<>(new LinkedHashSet<>(evenements));

        request.setAttribute("mode", isMatchType ? "match" : "event");
        request.setAttribute("evenements", evenements);
        request.getRequestDispatcher("/jsp/convocation/convoquerEvent.jsp").forward(request, response);
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

        Long idEvenement = null;
        try {
            idEvenement = Long.parseLong(idEvtParam);
            request.setAttribute("selectedEventId", idEvenement);
        } catch (NumberFormatException e) {
            request.setAttribute("messageErreur", "Id d'événement invalide.");
            doGet(request, response);
            return;
        }

        switch (type) {
            case "match":{
                eventService service = new eventService();
                Evenement evenement = service.findByIdWithParticipants(idEvenement);
                if (evenement == null || evenement.getGroupe() == null) {
                    request.setAttribute("messageErreur", "Impossible de charger l'événement ou le groupe.");
                    break;
                }

                Groupe groupe = evenement.getGroupe();
                List<Joueur> joueurs = groupe.getJoueurs();
                if (joueurs == null) {
                    request.setAttribute("messageErreur", "Aucun joueur associé à ce groupe.");
                    break;
                }

                for (Joueur joueur : joueurs) {
                    if (joueur.getEmailUtilisateur() != null) {
                        try {
                            SendEmailSSL.sendJoueurInvitation(joueur,evenement);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    List<Parent> parents = joueur.getParents();
                    if (parents == null) {
                        continue;
                    }
                    for (Parent parent : parents) {
                        if (parent.getEmailUtilisateur() == null) {
                            continue;
                        }
                        try{
                            SendEmailSSL.sendParentInvitation(parent,joueur,evenement);
                        }catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                break;
            }
            default:{
                eventService service = new eventService();
                Evenement evenement = service.findByIdWithParticipants(idEvenement);
                UtilisateurService utilisateurService = new UtilisateurService();
                List<Utilisateur> utilisateurs = utilisateurService.loadAllUtilisateurs();
                for (Utilisateur utilisateur : utilisateurs) {
                    if(utilisateur.getEmailUtilisateur()!=null) {
                        try {
                            sendEventInvitation(utilisateur,evenement);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        doGet(request, response);
    }


}
