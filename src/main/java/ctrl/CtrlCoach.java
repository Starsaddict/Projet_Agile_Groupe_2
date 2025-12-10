package ctrl;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.hibernate.Session;

import bd.HibernateUtil;
import model.Evenement;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/CtrlCoach")  
public class CtrlCoach extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		switch (action) {
		case "PageCoach":
			request.getRequestDispatcher("/jsp/PageCoach.jsp").forward(request, response);
			break;

		case "ConvocationGroupe":
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				Date now = new Date(); 
				List<Evenement> evenements = session
						.createQuery("from Evenement e where e.dateEvenement >= :now order by e.dateEvenement", Evenement.class)
						.setParameter("now", now).list();

				request.setAttribute("evenements", evenements);
			}
			request.getRequestDispatcher("/jsp/PageConvoquer.jsp").forward(request, response);
			break;

		case "GestionGroupe":
			// To do: US 12.
			break;

		default:
			request.getRequestDispatcher("/jsp/PageCoach.jsp").forward(request, response);
		}
	}
}
