package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.implement.CatalogoDaoImpl;
import dao.implement.UtentiDaoImpl;
import model.Libro;
import model.Utente;

@WebServlet("/catalogo/*")
public class CatalogoController extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(new Object() { }.getClass().getEnclosingClass());
	private static final long serialVersionUID = 1L;
	private Utente utente;

	public CatalogoController() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		String action = request.getPathInfo().substring(1);
		HttpSession session = request.getSession();

		utente = (Utente) session.getAttribute("utente");

		CatalogoDaoImpl catalogo = new CatalogoDaoImpl();

		List<Libro> libri = utente != null && utente.isCliente() ? catalogo.getLibriDisponibili() : catalogo.getLibri();
		request.setAttribute("libri", libri);

		Method doAction;
		try {
			doAction = CatalogoController.class.getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
			doAction.invoke(this, request, response);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			response.sendError(404);
		}
	}

	public void visualizza(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = getServletContext();
		logger.info("mostra catalogo");
		if (utente.isAmministratore()) {
			List<Utente> utenti = new UtentiDaoImpl().getUtenti();
			request.setAttribute("utenti", utenti);
		}
		
		RequestDispatcher rd = ctx.getRequestDispatcher("/catalogo.jsp");
		rd.forward(request, response);
	}

	public void aggiungi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/libro/aggiungi");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
