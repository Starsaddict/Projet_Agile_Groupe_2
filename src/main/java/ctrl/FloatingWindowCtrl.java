package ctrl;

import model.Utilisateur;
import service.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/floating")
public class FloatingWindowCtrl extends HttpServlet {

    UtilisateurService utilisateurService =  new UtilisateurService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Utilisateur> utilisateurs = utilisateurService.loadAllUtilisateurs();
        Map<String, Set<Utilisateur>> mapUtilisateur = new HashMap<>();
        utilisateurs.forEach(u -> mapUtilisateur
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
                    .append("\",\"email\":\"").append(escapeJson(item.get("email")))
                    .append("\",\"roles\":\"").append(escapeJson(item.get("roles")))
                    .append("\",\"title\":\"").append(escapeJson(item.get("title")))
                    .append("\"}");
            if (i < items.size() - 1) {
                payload.append(",");
            }
        }
        payload.append("]}");

        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(payload.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Placeholder response for POST action
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
