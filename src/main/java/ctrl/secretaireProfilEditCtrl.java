package ctrl;

import model.Utilisateur;
import model.Joueur;
import repo.utilisateurRepo;
import service.UtilisateurService;
import repository.UtilisateurRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@MultipartConfig
public class secretaireProfilEditCtrl extends HttpServlet {

    private UtilisateurService utilisateurService = new UtilisateurService(new UtilisateurRepositoryImpl());
    private utilisateurRepo utilisateurRepo = new utilisateurRepo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        System.out.println("DEBUG secretaireProfilEditCtrl.doGet: idStr=" + idStr);

        try {
            // 检查 id 参数是否存在
            if (idStr == null || idStr.isEmpty()) {
                System.out.println("DEBUG: idStr is null or empty");
                request.setAttribute("error", "ID utilisateur manquant");
                request.getRequestDispatcher("/jsp/secretaire/profil.jsp").forward(request, response);
                return;
            }

            Long id = Long.parseLong(idStr);
            System.out.println("DEBUG secretaireProfilEditCtrl.doGet: id=" + id);
            System.out.println("DEBUG secretaireProfilEditCtrl.doGet: About to call loadUtilisateur");
            Utilisateur utilisateur = utilisateurRepo.loadUtilisateur(id);
            System.out.println("DEBUG secretaireProfilEditCtrl.doGet: utilisateur=" + utilisateur);

            if (utilisateur == null) {
                System.out.println("DEBUG secretaireProfilEditCtrl.doGet: utilisateur is null, returning error");
                request.setAttribute("error", "Utilisateur non trouvé");
                request.getRequestDispatcher("/jsp/secretaire/profil.jsp").forward(request, response);
                return;
            }

            System.out.println("DEBUG secretaireProfilEditCtrl.doGet: utilisateur found, forwarding to profilEdit.jsp");
            request.setAttribute("utilisateur", utilisateur);
            request.getRequestDispatcher("/jsp/secretaire/profilEdit.jsp").forward(request, response);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            request.setAttribute("error", "ID utilisateur invalide");
            try {
                request.getRequestDispatcher("/jsp/secretaire/profil.jsp").forward(request, response);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur: " + e.getMessage());
            try {
                request.getRequestDispatcher("/jsp/secretaire/profil.jsp").forward(request, response);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Utilisateur utilisateur = utilisateurRepo.loadUtilisateur(id);

            if (utilisateur == null) {
                response.sendRedirect(request.getContextPath() + "/secretaire/profil?error=Utilisateur non trouvé");
                return;
            }

            // 获取参数
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String dateNaissance = request.getParameter("dateNaissance");
            String adresse = request.getParameter("adresse");
            String telephone = request.getParameter("telephone");

            // 更新数据
            if (nom != null && !nom.isEmpty())
                utilisateur.setNomUtilisateur(nom);
            if (prenom != null && !prenom.isEmpty())
                utilisateur.setPrenomUtilisateur(prenom);
            if (adresse != null)
                utilisateur.setAdresseUtilisateur(adresse);
            if (telephone != null)
                utilisateur.setTelephoneUtilisateur(telephone);
            utilisateur.setProfil(true);

            // Secretaire 可以修改出生日期
            if (dateNaissance != null && !dateNaissance.isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(dateNaissance, formatter);
                    utilisateur.setDateNaissanceUtilisateur(date);
                } catch (Exception e) {
                    // 忽略无效的日期转换
                }
            }

            // 处理 Joueur 头像上传
            if (utilisateur instanceof Joueur) {
                Joueur joueur = (Joueur) utilisateur;
                Part profilePicPart = null;
                try {
                    profilePicPart = request.getPart("profilePic");
                } catch (Exception ignore) {
                    // 如果未上传文件或请求不是 multipart，忽略头像更新
                }

                if (profilePicPart != null && profilePicPart.getSize() > 0) {
                    String originalName = Paths.get(profilePicPart.getSubmittedFileName()).getFileName().toString();
                    String extension = "";
                    int dotIndex = originalName.lastIndexOf('.');
                    if (dotIndex >= 0) {
                        extension = originalName.substring(dotIndex);
                    }

                    String numero = joueur.getNumeroJoueur();
                    String baseName = (numero != null && !numero.isEmpty()) ? numero : "joueur-" + joueur.getIdUtilisateur();
                    String fileName = baseName + "-" + System.currentTimeMillis() + extension;

                    String realDir = getServletContext().getRealPath("/img/joueur_avatar");
                    Path targetDir;
                    if (realDir != null) {
                        targetDir = Paths.get(realDir);
                    } else {
                        targetDir = Paths.get("src/main/webapp/img/joueur_avatar");
                    }
                    Files.createDirectories(targetDir);
                    Path targetFile = targetDir.resolve(fileName);

                    try (InputStream input = profilePicPart.getInputStream()) {
                        Files.copy(input, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    }

                    joueur.setProfilePicRoute("/img/joueur_avatar/" + fileName);
                }
            }

            // 通过 repository 保存
            Boolean updateSuccess = utilisateurRepo.updateUtilisateur(utilisateur);

            if (!updateSuccess) {
                request.setAttribute("error", "Erreur lors de la mise à jour du profil");
                request.setAttribute("utilisateur", utilisateur);
                request.getRequestDispatcher("/jsp/secretaire/profilEdit.jsp").forward(request, response);
                return;
            }

            // 同步该邮箱的所有其他用户的信息
            String currentEmail = utilisateur.getEmailUtilisateur();
            if (currentEmail != null && !currentEmail.isEmpty()) {
                try {
                    java.util.List<Utilisateur> allUsers = utilisateurRepo.loadAllUtilisateurs();
                    for (Utilisateur otherUser : allUsers) {
                        if (otherUser != null &&
                                currentEmail.equals(otherUser.getEmailUtilisateur()) &&
                                !otherUser.getIdUtilisateur().equals(utilisateur.getIdUtilisateur())) {
                            // 同步名字和姓氏
                            if (nom != null && !nom.isEmpty()) {
                                otherUser.setNomUtilisateur(nom);
                            }
                            if (prenom != null && !prenom.isEmpty()) {
                                otherUser.setPrenomUtilisateur(prenom);
                            }
                            // 保存更新
                            utilisateurRepo.updateUtilisateur(otherUser);
                        }
                    }
                } catch (Exception e) {
                    // 同步失败不影响主用户的更新
                    e.printStackTrace();
                }
            }

            request.setAttribute("success", "Profil mis à jour avec succès");
            request.setAttribute("utilisateur", utilisateur);
            response.sendRedirect(request.getContextPath() + "/secretaire/profil?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/secretaire/profil?error=Erreur lors de la mise à jour");
        }
    }
}
