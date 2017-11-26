package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.implement.PrestitiDaoImpl;
import dao.interfaces.PrestitiDao;
import model.Prestito;
import model.Utente;

@WebServlet("/prestiti")
public class Prestiti extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = null;

		Utente utente = (Utente) session.getAttribute("utente");
		PrestitiDao prestitiDao = new PrestitiDaoImpl();

		List<Prestito> prestiti = prestitiDao.getPrestitiByUserId(utente.getId());
		request.setAttribute("listaPrestiti", prestiti);

		rd = ctx.getRequestDispatcher("/prestiti.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
