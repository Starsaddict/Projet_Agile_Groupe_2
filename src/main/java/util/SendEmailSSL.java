package util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import model.Parent;
import model.Utilisateur;
import model.Evenement;

import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class SendEmailSSL {

    private static final String username = "rugbyclub999@gmail.com";
    private static final String password = "bxslvvlbqaijhoeh";

    /* =========================================================
       CONFIG SMTP
       ========================================================= */
    public static void sendEmail(String toRecipients, String subject, String body)
            throws MessagingException {

        // ✅ PROTECTION CONTRE EMAIL NULL / VIDE (évite HTTP 500)
        if (toRecipients == null || toRecipients.isBlank()) {
            System.err.println("⚠️ Email non envoyé : destinataire null/vide. Sujet=" + subject);
            return;
        }

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipients));
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=UTF-8");

        Transport.send(message);
    }

    /* =========================================================
       RESET MOT DE PASSE
       ========================================================= */
    public void sendResetRequest(Utilisateur u) throws MessagingException {

        String subject = "Réinitialisation du mot de passe";
        String email = (u != null) ? u.getEmailUtilisateur() : null;

        String body =
                "<html><body>"
                + "<h2>Bonjour " + u.getPrenomUtilisateur() + " "
                + u.getNomUtilisateur() + ",</h2>"
                + "<p>Vous avez demandé une réinitialisation de mot de passe.</p>"
                + "<p><a href='http://localhost:8080/Projet_Agile_Groupe_2/resetPassword?uid="
                + u.getIdUtilisateur() + "'>"
                + "👉 Réinitialiser le mot de passe</a></p>"
                + "<p>Si vous n'êtes pas à l'origine de cette demande, ignorez ce message.</p>"
                + "</body></html>";

        sendEmail(email, subject, body);
    }

    /* =========================================================
       CONVOCATION MATCH — JOUEUR (AVEC LIEN)
       ========================================================= */
    public static void sendJoueurInvitation(Utilisateur joueur, Evenement match, String lienConfirmation)
            throws MessagingException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String subject = "Convocation – Match officiel : " + match.getNomEvenement();
        String email = (joueur != null) ? joueur.getEmailUtilisateur() : null;

        String body =
                "<html><body>"
                + "<h2>Bonjour " + joueur.getPrenomUtilisateur() + " " + joueur.getNomUtilisateur() + ",</h2>"
                + "<p>Vous êtes convoqué pour le match suivant :</p>"
                + "<ul>"
                + "<li><strong>Match :</strong> " + match.getNomEvenement() + "</li>"
                + "<li><strong>Date :</strong> " + match.getDateEvenement().format(formatter) + "</li>"
                + "<li><strong>Lieu :</strong> " + match.getLieuEvenement() + "</li>"
                + "</ul>"
                + "<p><strong>Merci d’indiquer si le joueur peut jouer :</strong></p>"
                + "<p><a href='" + lienConfirmation + "'>👉 Confirmer / modifier la disponibilité</a></p>"
                + "<p style='font-size:12px;color:gray;'>La dernière réponse enregistrée sera prise en compte.</p>"
                + "</body></html>";

        sendEmail(email, subject, body);
    }

    /* =========================================================
       CONVOCATION MATCH — PARENT (AVEC LIEN)
       ========================================================= */
    public static void sendParentInvitation(Parent parent, Utilisateur joueur, Evenement match, String lienConfirmation)
            throws MessagingException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String subject = "Convocation – Match officiel : " + match.getNomEvenement();
        String email = (parent != null) ? parent.getEmailUtilisateur() : null;

        String body =
                "<html><body>"
                + "<h2>Bonjour " + parent.getPrenomUtilisateur() + " " + parent.getNomUtilisateur() + ",</h2>"
                + "<p>Votre enfant <strong>" + joueur.getPrenomUtilisateur() + " " + joueur.getNomUtilisateur()
                + "</strong> est convoqué pour le match suivant :</p>"
                + "<ul>"
                + "<li><strong>Match :</strong> " + match.getNomEvenement() + "</li>"
                + "<li><strong>Date :</strong> " + match.getDateEvenement().format(formatter) + "</li>"
                + "<li><strong>Lieu :</strong> " + match.getLieuEvenement() + "</li>"
                + "</ul>"
                + "<p><strong>Merci d’indiquer si le joueur peut jouer :</strong></p>"
                + "<p><a href='" + lienConfirmation + "'>👉 Confirmer / modifier la disponibilité</a></p>"
                + "<p style='font-size:12px;color:gray;'>La dernière réponse enregistrée sera prise en compte.</p>"
                + "</body></html>";

        sendEmail(email, subject, body);
    }

    /* =========================================================
       AUTRES ÉVÉNEMENTS
       ========================================================= */
    public static void sendEventInvitation(Utilisateur u, Evenement e, String lienConfirmation)
            throws MessagingException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String subject = "Confirmation de présence – " + e.getNomEvenement();
        String email = (u != null) ? u.getEmailUtilisateur() : null;

        String body =
                "<html><body>"
                + "<h2>Bonjour " + u.getPrenomUtilisateur() + " " + u.getNomUtilisateur() + ",</h2>"
                + "<p>Merci de confirmer votre présence à l'évènement :</p>"
                + "<ul>"
                + "<li><strong>Nom :</strong> " + e.getNomEvenement() + "</li>"
                + "<li><strong>Date :</strong> " + e.getDateEvenement().format(formatter) + "</li>"
                + "<li><strong>Lieu :</strong> " + e.getLieuEvenement() + "</li>"
                + "</ul>"
                + "<p><a href='" + lienConfirmation + "'>👉 Confirmer / modifier ma présence</a></p>"
                + "<p style='font-size:12px;color:gray;'>La dernière réponse enregistrée sera prise en compte.</p>"
                + "</body></html>";

        sendEmail(email, subject, body);
    }
}
