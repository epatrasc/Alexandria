package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
import model.Breadcrumbs;
import model.Libro;
import model.LibroAction;
import model.LibroUtente;
import model.Utente;
import utils.JSONManager;

@WebServlet("/catalogo/*")
public class CatalogoController extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(new Object() {
	}.getClass().getEnclosingClass());
	private static final long serialVersionUID = 1L;
	private Utente utente;

	public CatalogoController() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String menu_active = (String) request.getAttribute("menu_active");

		if (menu_active == null || !menu_active.equals("home")) {
			request.setAttribute("menu_active", "catalogo");
			request.setAttribute("breadcrumbs", new Breadcrumbs(Breadcrumbs.CATALOGO_VISUALIZZA));
		}

		String action = request.getPathInfo().substring(1);
		HttpSession session = request.getSession();

		utente = (Utente) session.getAttribute("utente");

		CatalogoDaoImpl catalogo = new CatalogoDaoImpl();
		List<LibroAction> libriAction = new ArrayList<>();

		if(utente!= null){
			List<LibroUtente> libriUtente = catalogo.getLibriUtente();
			

			for (LibroUtente libroUtente : libriUtente) {
				Libro libro = libroUtente.getLibro();
				LibroAction libroAction = new LibroAction();

				libroAction.setLibro(libro);

				if (libro.isDisponibile()) {
					libroAction.setAction(LibroAction.PRESTA);
				} else {
					if (utente.isAmministratore() || utente.getId() == libroUtente.getIdUtente()) {
						libroAction.setAction(LibroAction.RESTITUISCI);
					} else {
						libroAction.setAction(LibroAction.NO_ACTION);
					}
				}

				libriAction.add(libroAction);
			}
		}else{
			List<Libro> libri = catalogo.getLibri();

			for (Libro libro : libri) {
				LibroAction libroAction = new LibroAction();
				
				libroAction.setLibro(libro);
				libroAction.setAction(LibroAction.NO_ACTION);

				libriAction.add(libroAction);
			}
		}

		request.setAttribute("libriAction", libriAction);

		boolean isAndroid = request.getParameter("isAndroid") != null ? Boolean.parseBoolean(request.getParameter("isAndroid")) : false;

		if (isAndroid) {
			JSONManager JSONMan = new JSONManager();
			String json = JSONMan.serializeJson(libriAction);

			PrintWriter out = response.getWriter();
			out.append(json);
			out.close();
			return;
		}

		Method doAction;
		try {
			logger.debug("action:" + action);
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
		if (utente != null && utente.isAmministratore()) {
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
