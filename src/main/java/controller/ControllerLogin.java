package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import bd.HibernateUtil;
import model.Evenement;
import model.Groupe;
import model.Utilisateur;
import repository.UtilisateurRepositoryImpl;
import service.UtilisateurService;

@WebServlet("/CtrlLogin")
public class ControllerLogin extends HttpServlet {

    private UtilisateurService utilisateurService;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurService = new UtilisateurService(new UtilisateurRepositoryImpl());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role_connexion");

		Optional<Utilisateur> opt = utilisateurService.authenticate(email, password, role);
		if (opt.isPresent()) {
			HttpSession session = request.getSession(true);
			session.setAttribute("user", opt.get());				
			String url = "";
			url = role;
			switch(url) {
			case"Coach":
				 try (Session sessionH = HibernateUtil.getSessionFactory().openSession()) {
					 LocalDateTime now = LocalDateTime.now();
					 List<Evenement> evenements = sessionH
	                	        .createQuery(
	                	                "from Evenement e where e.dateEvenement >= :now and e.typeEvenement = :typeEvt order by e.dateEvenement",
	                	                Evenement.class)
	                	        .setParameter("now", now)
	                	        .setParameter("typeEvt", "Match-officiel")
	                	        .list();

                        List<Groupe> groupes = sessionH.createQuery("from Groupe", Groupe.class).list();
                        for (Groupe g : groupes) {
                            g.getJoueurs().size();
                        }

                        request.setAttribute("evenements", evenements);
                        request.setAttribute("groupes", groupes);
                    }
                    break;
            }
            request.getRequestDispatcher(url).forward(request, response);
        } else {
            request.setAttribute("msg_connection", "Connexion échouée : identifiants invalides");
            request.setAttribute("role_connexion", role);
            request.getRequestDispatcher("Login").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doPost(request, response);
    }
}
