package util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import model.Utilisateur;

import java.util.Properties;

public class SendEmailSSL {

    private final String username = "chikaluchesi@gmail.com";
    private final String password = "wjuhoftlmantrydu";

    public void sendEmail(String toRecipients, String subject, String body) throws MessagingException {

        String from = username;

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
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipients));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    public void sendResetRequest(String toRecipients, Utilisateur u) throws MessagingException {
        String subject = "";

        String body = "";



        sendEmail(toRecipients, subject, body);
    }
}
