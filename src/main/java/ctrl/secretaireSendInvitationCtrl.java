package ctrl;

import repo.codeRepo;
import repo.utilisateurRepo;
import util.SendEmailSSL;
import model.Code;

import jakarta.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

public class secretaireSendInvitationCtrl extends HttpServlet {

    private static final SecureRandom rnd = new SecureRandom();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/secretaire/profil?error=Email+manquant");
            return;
        }

        utilisateurRepo utilisateurRepo = new utilisateurRepo();
        if (utilisateurRepo.emailExists(email)) {
            response.sendRedirect(request.getContextPath() + "/secretaire/profil?error=Email+deja+utilise");
            return;
        }

        String code = generateCode(8);
        codeRepo codeRepo = new codeRepo();
        Code c = new Code(code, "Inscription");
        codeRepo.save(c);

        // Build absolute URL without duplicating the context path
        String scheme = request.getScheme();
        String host = request.getServerName();
        int port = request.getServerPort();
        String portPart = (port == 80 || port == 443) ? "" : ":" + port;
        String link = scheme + "://" + host + portPart + request.getContextPath() + "/inscription?code=" + code;
        String subject = "Invitation à créer un compte - Club";
        String body = "<html><body>Bonjour,<br/><p>Vous pouvez créer votre compte et celui de votre enfant en cliquant sur le lien suivant :</p>"
                + "<a href='" + link + "'>Créer mon compte</a><br/><p>Code d'invitation : <b>" + code
                + "</b></p></body></html>";

        try {
            SendEmailSSL.sendEmail(email, subject, body);
        } catch (MessagingException e) {
            // cleanup created code
            codeRepo.delete(code);
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/secretaire/profil?error=Erreur+en+envoi+de+l'email");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/secretaire/profil?success=invitation");
    }

    private String generateCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
