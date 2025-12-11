package ctrl;

import model.Parent;
import model.Utilisateur;
import repo.utilisateurRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class parentProfilEditCtrl extends HttpServlet {

    private utilisateurRepo utilisateurRepo = new utilisateurRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String parentIdStr = request.getParameter("parentId");

        try {
            Long id = Long.parseLong(idStr);
            Utilisateur utilisateur = utilisateurRepo.loadUtilisateur(id);

            if (utilisateur == null) {
                String redirectUrl = request.getContextPath() + "/parent/profil";
                if (parentIdStr != null) {
                    redirectUrl += "?parentId=" + parentIdStr + "&error=Utilisateur non trouvé";
                } else {
                    redirectUrl += "?error=Utilisateur non trouvé";
                }
                response.sendRedirect(redirectUrl);
                return;
            }

            // Vérifier que c'est le parent lui-même ou un enfant du parent
            if (parentIdStr != null) {
                Long parentId = Long.parseLong(parentIdStr);
                Utilisateur parentUser = utilisateurRepo.loadUtilisateur(parentId);
                if (!(parentUser instanceof Parent)) {
                    response.sendRedirect(
                            request.getContextPath() + "/parent/profil?parentId=" + parentId + "&error=Accès refusé");
                    return;
                }
            } else if (!(utilisateur instanceof Parent)) {
                response.sendRedirect(request.getContextPath() + "/parent/profil?error=Accès refusé");
                return;
            }

            request.setAttribute("utilisateur", utilisateur);
            request.setAttribute("parentId", parentIdStr);
            request.getRequestDispatcher("/jsp/parent/profilEdit.jsp").forward(request, response);

        } catch (Exception e) {
            String redirectUrl = request.getContextPath() + "/parent/profil";
            if (parentIdStr != null) {
                redirectUrl += "?parentId=" + parentIdStr + "&error=Erreur";
            } else {
                redirectUrl += "?error=Erreur";
            }
            response.sendRedirect(redirectUrl);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String description = request.getParameter("description");
            String parentIdStr = request.getParameter("parentId");

            Utilisateur utilisateur = utilisateurRepo.loadUtilisateur(id);

            if (utilisateur == null) {
                String redirectUrl = request.getContextPath() + "/parent/profil";
                if (parentIdStr != null) {
                    redirectUrl += "?parentId=" + parentIdStr + "&error=Utilisateur non trouvé";
                } else {
                    redirectUrl += "?error=Utilisateur non trouvé";
                }
                response.sendRedirect(redirectUrl);
                return;
            }

            // Vérifier l'accès: le parent lui-même ou un enfant du parent
            if (parentIdStr != null) {
                Long parentId = Long.parseLong(parentIdStr);
                Utilisateur parentUser = utilisateurRepo.loadUtilisateur(parentId);
                if (!(parentUser instanceof Parent)) {
                    response.sendRedirect(
                            request.getContextPath() + "/parent/profil?parentId=" + parentId + "&error=Accès refusé");
                    return;
                }
                // Le utilisateur doit être soit le parent lui-même, soit un enfant du parent
                if (!parentId.equals(utilisateur.getIdUtilisateur())) {
                    Parent parent = (Parent) parentUser;
                    boolean isChild = parent.getJoueurs() != null && parent.getJoueurs().stream()
                            .anyMatch(j -> j.getIdUtilisateur().equals(utilisateur.getIdUtilisateur()));
                    if (!isChild) {
                        response.sendRedirect(request.getContextPath() + "/parent/profil?parentId=" + parentId
                                + "&error=Accès refusé");
                        return;
                    }
                }
            } else if (!(utilisateur instanceof Parent)) {
                response.sendRedirect(request.getContextPath() + "/parent/profil?error=Accès refusé");
                return;
            }

            // Mettre à jour la description
            utilisateur.setDescription(description);
            utilisateurRepo.updateUtilisateur(utilisateur);

            // Rediriger vers la page de profil
            String redirectUrl = request.getContextPath() + "/parent/profil?success=1";
            if (parentIdStr != null) {
                redirectUrl += "&parentId=" + parentIdStr;
            } else if (utilisateur instanceof Parent) {
                redirectUrl += "&parentId=" + utilisateur.getIdUtilisateur();
            }
            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            e.printStackTrace();
            String parentIdStr = request.getParameter("parentId");
            String redirectUrl = request.getContextPath() + "/parent/profil";
            if (parentIdStr != null) {
                redirectUrl += "?parentId=" + parentIdStr + "&error=Erreur";
            } else {
                redirectUrl += "?error=Erreur";
            }
            response.sendRedirect(redirectUrl);
        }
    }
}
