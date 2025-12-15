package util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import model.Parent;
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

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("<html>")
                .append("<body>")
                .append("<h2>Bonjour ")
                .append(u.getPrenomUtilisateur())
                .append(" ")
                .append(u.getNomUtilisateur())
                .append(",</h2>")
                .append("<p>Vous avez demandé une réinitialisation de mot de passe.</p>")
                .append("<p>Veuillez cliquer sur le lien suivant :</p>")
                .append("<a href='http://localhost:8080/Projet_Agile_Groupe_2/resetPassword?uid=")
                .append(u.getIdUtilisateur())
                .append("'>Réinitialiser le mot de passe</a>")
                .append("<br/><br/>")
                .append("<p>Si vous n'êtes pas à l'origine de cette demande, ignorez ce message.</p>")
                .append("</body>")
                .append("</html>");

        String body = bodyBuilder.toString();

        sendEmail(toRecipients, subject, body);
    }

    //TODO: 这里最后要加上邀请链接
    public static void sendJoueurInvitation(Utilisateur u, model.Evenement e) throws MessagingException {
        String subject = "Invitation à l'évènement " + e.getNomEvenement();

        String email = u.getEmailUtilisateur();
        
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("<html>")
                .append("<body>")
                .append("<h2>Bonjour ")
                .append(u.getPrenomUtilisateur())
                .append(" ")
                .append(u.getNomUtilisateur())
                .append(",</h2>")
                .append("<p>Vous êtes invité à participer à un MATCH suivant :</p>")
                .append("<ul>")
                .append("<li>Nom : ")
                .append(e.getNomEvenement())
                .append("</li>")
                .append("<li>Type : ")
                .append(e.getTypeEvenement())
                .append("</li>")
                .append("<li>Date : ")
                .append(e.getDateEvenement()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .append("</li>")
                .append("<li>Lieu : ")
                .append(e.getLieuEvenement())
                .append("</li>")
                .append("</ul>")
                .append("<p>Merci de confirmer votre présence.</p>")
                .append("</body>")
                .append("</html>");

        String body = bodyBuilder.toString();

        sendEmail(email, subject, body);
    }

    public static void sendEventInvitation(Utilisateur u, model.Evenement e) throws MessagingException {
        String subject = "Invitation à l'évènement : " + e.getNomEvenement();

        String email = u.getEmailUtilisateur();

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("<html>")
                .append("<body>")
                .append("<h2>Bonjour ")
                .append(u.getPrenomUtilisateur())
                .append(" ")
                .append(u.getNomUtilisateur())
                .append(",</h2>")
                .append("<p>Nous avons le plaisir de vous inviter à participer à l'évènement suivant :</p>")
                .append("<ul>")
                .append("<li><strong>Nom :</strong> ")
                .append(e.getNomEvenement())
                .append("</li>")
                .append("<li><strong>Type :</strong> ")
                .append(e.getTypeEvenement())
                .append("</li>")
                .append("<li><strong>Date :</strong> ")
                .append(e.getDateEvenement()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .append("</li>")
                .append("<li><strong>Lieu :</strong> ")
                .append(e.getLieuEvenement())
                .append("</li>")
                .append("</ul>")
                .append("<p>Merci de bien vouloir confirmer votre participation depuis l'application.</p>")
                .append("<p>Cordialement,<br/>Le secrétariat</p>")
                .append("</body>")
                .append("</html>");

        String body = bodyBuilder.toString();

        sendEmail(email, subject, body);
    }

    public static void sendParentInvitation(Parent p, Utilisateur u, model.Evenement e) throws MessagingException {
        String subject = "Invitation – Participation de votre enfant à un MATCH suivant :</p>";

        String email = p.getEmailUtilisateur();
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("<html>")
                .append("<body>")
                .append("<h2>Bonjour ")
                .append(p.getPrenomUtilisateur())
                .append(" ")
                .append(p.getNomUtilisateur())
                .append(",</h2>")
                .append("<p>Nous souhaitons vous informer que votre enfant")
                .append(u.getNomUtilisateur())
                .append(" ")
                .append(u.getPrenomUtilisateur())
                .append(" est invité à participer à le MATCH suivant :</p>")
                .append("<ul>")
                .append("<li>Nom de l'évènement : ")
                .append(e.getNomEvenement())
                .append("</li>")
                .append("<li>Type : ")
                .append(e.getTypeEvenement())
                .append("</li>")
                .append("<li>Date : ")
                .append(e.getDateEvenement()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .append("</li>")
                .append("<li>Lieu : ")
                .append(e.getLieuEvenement())
                .append("</li>")
                .append("</ul>")
                .append("<p>Nous vous remercions de bien vouloir confirmer la participation de votre enfant.</p>")
                .append("<p>En cas de question, n'hésitez pas à contacter le secrétariat.</p>")
                .append("</body>")
                .append("</html>");

        String body = bodyBuilder.toString();

        sendEmail(email, subject, body);
    }



//    public static void main(String[] args) throws MessagingException {
//        SendEmailSSL.sendEmail(
//                "18201122059zky@gmail.com",
//                "test",
//                "ok"
//        );
//    }
}
