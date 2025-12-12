package util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import model.Joueur;
import model.Utilisateur;

import java.util.Properties;

public class SendEmailSSL {

    private static final String username = "chikaluchesi@gmail.com";
    private static final String password = "wjuhoftlmantrydu";

    public static void sendEmail(String toRecipients, String subject, String body) throws MessagingException {

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

    public void sendResetRequest(Utilisateur u) throws MessagingException {
        String subject = "Réinitialisation du mot de passe";

        String toRecipients = u.getEmailUtilisateur();

        String body =
                "<html>" +
                        "<body>" +
                        "<h2>Bonjour " + u.getPrenomUtilisateur() + " " + u.getNomUtilisateur() + ",</h2>" +
                        "<p>Vous avez demandé une réinitialisation de mot de passe.</p>" +
                        "<p>Veuillez cliquer sur le lien suivant :</p>" +
                        "<a href='http://localhost:8080/Projet_Agile_Groupe_2/resetPassword?uid=" + u.getIdUtilisateur() + "'>Réinitialiser le mot de passe</a>" +
                        "<br/><br/>" +
                        "<p>Si vous n'êtes pas à l'origine de cette demande, ignorez ce message.</p>" +
                        "</body>" +
                        "</html>";

        sendEmail(toRecipients, subject, body);
    }

//    public static void main(String[] args) throws MessagingException {
//        SendEmailSSL.sendEmail(
//                "18201122059zky@gmail.com",
//                "test",
//                "ok"
//        );
//    }
}