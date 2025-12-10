package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Joueur;
import model.Parent;
import model.Secretaire;
import model.Utilisateur;
import service.profilService;

@WebServlet("/Profil/*")
public class profilCtrl extends HttpServlet {
    private profilService profilService = new profilService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo != null && pathInfo.length() > 1) {
                String[] splits = pathInfo.split("/");
                if (splits.length > 1) {
                    Long joueurId = Long.parseLong(splits[1]);
                    
                    Joueur joueur = profilService.getJoueurById(joueurId);
                    
                    if (joueur != null) {
                        // 权限检查：秘书可以访问所有人，父母只能访问自己孩子的信息
                        if (utilisateur instanceof Secretaire || 
                            (utilisateur instanceof Parent && 
                             isParentOfJoueur((Parent) utilisateur, joueur))) {
                            
                            request.setAttribute("joueur", joueur);
                            
                            // 设置家长信息
                            List<Parent> parents = joueur.getParents();
                            if (parents != null && !parents.isEmpty()) {
                                request.setAttribute("parent1", parents.get(0));
                                if (parents.size() > 1) {
                                    request.setAttribute("parent2", parents.get(1));
                                } else {
                                    request.setAttribute("parent2", null);
                                }
                            } else {
                                request.setAttribute("parent1", null);
                                request.setAttribute("parent2", null);
                            }
                            
                            // 设置当前用户类型
                            request.setAttribute("isSecretaire", utilisateur instanceof Secretaire);
                            request.setAttribute("currentUserId", utilisateur.getIdUtilisateur());
                            
                            request.getRequestDispatcher("/webapp/jsp/profil.jsp")
                                   .forward(request, response);
                        } else {
                            // 没有权限
                            response.sendRedirect(request.getContextPath() + "/access-denied");
                        }
                    } else {
                        response.sendRedirect(request.getContextPath() + "/joueurs?error=notfound");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/joueurs");
                }
            } else {
                // 访问自己的个人资料
                if (utilisateur != null) {
                    response.sendRedirect(request.getContextPath() + "/Profil/" + utilisateur.getIdUtilisateur());
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/joueurs?error=invalidid");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/joueurs?error=server");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        
        if (utilisateur == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            Long idJoueur = Long.parseLong(request.getParameter("idJoueur"));
            
            Joueur joueur = profilService.getJoueurById(idJoueur);
            if (joueur == null) {
                response.sendRedirect(request.getContextPath() + "/joueurs?error=notfound");
                return;
            }
            
            // 权限验证
            if (!(utilisateur instanceof Secretaire) && 
                !(utilisateur instanceof Parent && isParentOfJoueur((Parent) utilisateur, joueur))) {
                response.sendRedirect(request.getContextPath() + "/access-denied");
                return;
            }
            
            // 更新球员信息
            joueur.setNomUtilisateur(request.getParameter("nomJoueur"));
            joueur.setPrenomUtilisateur(request.getParameter("prenomJoueur"));
            
            
            String dateNaissance = request.getParameter("dateNaissance");
            if (dateNaissance != null && !dateNaissance.isEmpty()) {
                joueur.setDateNaissanceUtilisateur(java.time.LocalDate.parse(dateNaissance));
            }
            
            // 处理父母信息
            Parent parent1 = null;
            Parent parent2 = null;
            
            // 更新第一个家长信息
            String nomParent1 = request.getParameter("nomParent1");
            String prenomParent1 = request.getParameter("prenomParent1");
            String idParent1Str = request.getParameter("idParent1");
            
            if (idParent1Str != null && !idParent1Str.trim().isEmpty()) {
                Long idParent1 = Long.parseLong(idParent1Str);
                parent1 = profilService.getParentById(idParent1);
                if (parent1 != null) {
                    // 权限检查：父母只能修改自己的信息
                    if (utilisateur instanceof Secretaire || 
                        utilisateur.getIdUtilisateur().equals(idParent1)) {
                        
                        parent1.setNomUtilisateur(nomParent1);
                        parent1.setPrenomUtilisateur(prenomParent1);
                        
                        
           
                    }
                }
            }
            
            // 更新第二个家长信息
            String nomParent2 = request.getParameter("nomParent2");
            String prenomParent2 = request.getParameter("prenomParent2");
            String idParent2Str = request.getParameter("idParent2");
            
            if (idParent2Str != null && !idParent2Str.trim().isEmpty()) {
                Long idParent2 = Long.parseLong(idParent2Str);
                parent2 = profilService.getParentById(idParent2);
                if (parent2 != null) {
                    // 权限检查：父母只能修改自己的信息
                    if (utilisateur instanceof Secretaire || 
                        utilisateur.getIdUtilisateur().equals(idParent2)) {
                        
                        parent2.setNomUtilisateur(nomParent2);
                        parent2.setPrenomUtilisateur(prenomParent2);
                        
                        
             
                    }
                }
            }
            
            // 更新数据
            profilService.updateJoueurAndParents(joueur, parent1, parent2);
            
            // 重定向回个人资料页面
            response.sendRedirect(request.getContextPath() + "/Profil/" + idJoueur + "?success=true");
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Profil/" + 
                                request.getParameter("idJoueur") + "?error=invalidinput");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Profil/" + 
                                request.getParameter("idJoueur") + "?error=updatefailed");
        }
    }
    
    // 检查家长是否是孩子的家长
    private boolean isParentOfJoueur(Parent parent, Joueur joueur) {
        if (parent == null || joueur == null || joueur.getParents() == null) {
            return false;
        }
        
        return joueur.getParents().stream()
            .anyMatch(p -> p.getIdUtilisateur().equals(parent.getIdUtilisateur()));
    }
}