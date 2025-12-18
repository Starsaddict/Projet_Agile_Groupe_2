package ctrl;

import jakarta.mail.MessagingException;
import model.Utilisateur;
import service.UtilisateurService;
import util.SendEmailSSL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/floating")
public class FloatingWindowCtrl extends HttpServlet {

    UtilisateurService utilisateurService =  new UtilisateurService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        try {
            List<Utilisateur> utilisateurs = Optional.ofNullable(utilisateurService.loadAllUtilisateurs())
                    .orElse(Collections.emptyList());
            Map<String, Set<Utilisateur>> mapUtilisateur = new HashMap<>();
            utilisateurs.stream()
                    .filter(Objects::nonNull)
                    .filter(u -> u.getEmailUtilisateur() != null && !u.getEmailUtilisateur().isEmpty())
                    .forEach(u -> mapUtilisateur
                            .computeIfAbsent(u.getEmailUtilisateur(), k -> new HashSet<>())
                            .add(u));

            List<Map<String, String>> items = new ArrayList<>();
            mapUtilisateur.forEach((email, users) -> {
                if (users.isEmpty()) {
                    return;
                }
                Utilisateur premierUtilisateur = users.iterator().next();
                String roles = users.stream()
                        .map(Utilisateur::getRoleLabel)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.joining("/"));

                Map<String, String> dto = new LinkedHashMap<>();
                dto.put("nom", premierUtilisateur.getNomUtilisateur());
                dto.put("prenom", premierUtilisateur.getPrenomUtilisateur());
                dto.put("email", premierUtilisateur.getEmailUtilisateur());
                dto.put("roles", roles);
                dto.put("title", premierUtilisateur.getNomUtilisateur() + " - " + email + " - " + roles);
                items.add(dto);
            });

            StringBuilder payload = new StringBuilder();
            payload.append("{\"items\":[");
            for (int i = 0; i < items.size(); i++) {
                Map<String, String> item = items.get(i);
                payload.append("{\"nom\":\"").append(escapeJson(item.get("nom")))
                        .append("\",\"prenom\":\"").append(escapeJson(item.get("prenom")))
                        .append("\",\"email\":\"").append(escapeJson(item.get("email")))
                        .append("\",\"roles\":\"").append(escapeJson(item.get("roles")))
                        .append("\",\"title\":\"").append(escapeJson(item.get("title")))
                        .append("\"}");
                if (i < items.size() - 1) {
                    payload.append(",");
                }
            }
            payload.append("]}");

            resp.getWriter().write(payload.toString());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"items\":[]}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String titre = req.getParameter("title");
        String contenu = req.getParameter("content");
        req.setAttribute("email", email);
        req.setAttribute("title", titre);
        req.setAttribute("content", contenu);

        HttpSession session = req.getSession(false);
        Utilisateur utilisateur = session != null ? (Utilisateur) session.getAttribute("user") : null;
        if (utilisateur == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"status\":\"unauthorized\"}");
            return;
        }

        try {
            SendEmailSSL.encoyerMessage(utilisateur,email, titre, contenu);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write("{\"status\":\"ok\"}");
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder escaped = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '\\':
                    escaped.append("\\\\");
                    break;
                case '"':
                    escaped.append("\\\"");
                    break;
                case '\b':
                    escaped.append("\\b");
                    break;
                case '\f':
                    escaped.append("\\f");
                    break;
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                default:
                    if (c < 32) {
                        String hex = Integer.toHexString(c);
                        escaped.append("\\u");
                        for (int j = hex.length(); j < 4; j++) {
                            escaped.append('0');
                        }
                        escaped.append(hex);
                    } else {
                        escaped.append(c);
                    }
            }
        }
        return escaped.toString();
    }
}
