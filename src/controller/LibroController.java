package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
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

import dao.implement.LibroDaoImpl;
import dao.implement.LibroUtenteDaoImpl;
import dao.implement.PrestitoDaoImpl;
import dao.implement.UtentiDaoImpl;
import model.Breadcrumbs;
import model.Libro;
import model.LibroAction;
import model.LibroUtente;
import model.Prestito;
import model.StatusResponse;
import model.Utente;
import utils.JSONManager;

@WebServlet("/libro/*")
public class LibroController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private JSONManager JSONMan = new JSONManager();
	private Utente utente;
	private Libro libro;
	private PrintWriter out;

	public LibroController() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setAttribute("menu_active", "catalogo");

		String action = request.getPathInfo().substring(1);

		HttpSession session = request.getSession();

		utente = (Utente) session.getAttribute("utente");
		boolean isAndroid = getIsAndroid(request.getParameter("isAndroid"));

		if (isAndroid) {
			int idLibro = Integer.parseInt(request.getParameter("idLibro"));
			LibroUtente libroUtente = getLibroUtente(idLibro);
			LibroAction libroAction = getLibroAction(libroUtente);

			String json = JSONMan.serializeJson(libroAction);
			out = response.getWriter();
			out.println(json);
			return;
		}

		if (!hasRights(utente, action)) {
			if (utente != null) {
				logger.info(String.format("Utente %d non ha accesso all'azione %s", utente.getId(), action));
			} else {
				logger.info(String.format("Utente non loggato e non autorizzato all'azione %s", action));
			}

			response.sendError(403, "Accesso negato");
			return;
		}

		Method doAction;
		try {
			doAction = LibroController.class.getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
			doAction.invoke(this, request, response);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}

	private LibroUtente getLibroUtente(int idLibro) {
		return new LibroUtenteDaoImpl().getLibroUtente(idLibro);
	}

	private LibroAction getLibroAction(LibroUtente libroUtente) {
		boolean isDisponibile = libroUtente.getLibro().isDisponibile();
		Libro libro = libroUtente.getLibro();

		String action = libro.isDisponibile() ? LibroAction.PRESTA : LibroAction.RESTITUISCI;

		if (!utente.isAmministratore() && !isDisponibile && utente.getId() != libroUtente.getIdUtente()) {
			action = LibroAction.NO_ACTION;
		}

		return new LibroAction(libroUtente.getLibro(), action);
	}

	public void visualizza(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("breadcrumbs", new Breadcrumbs(Breadcrumbs.CATALOGO_DETTAGLIO));
		RequestDispatcher rd;
		ServletContext ctx = getServletContext();
		int idLibro = Integer.parseInt(request.getParameter("idLibro"));

		if (idLibro == 0) {
			// invalid parameter
			String msg = String.format("Parametro %s non valido", request.getPathInfo());

			logger.info(msg);
			request.setAttribute("errore", msg);

			rd = ctx.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
			return;
		}

		libro = getLibro(idLibro);
		if (libro == null) {
			// invalid parameter
			String msg = String.format("Nessun libro trovato con id %d", idLibro);

			logger.info(msg);
			request.setAttribute("errore", msg);

			rd = ctx.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
			return;
		}
		
		if (utente != null && utente.isAmministratore()) {
			List<Utente> utenti = new UtentiDaoImpl().getUtenti();
			request.setAttribute("utenti", utenti);
		}
		
		request.setAttribute("action", "visualizza");
		request.setAttribute("libro", libro);

		rd = getServletContext().getRequestDispatcher("/libro.jsp");
		rd.forward(request, response);
	}

	public void modifica(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("breadcrumbs", new Breadcrumbs(Breadcrumbs.CATALOGO_MODIFICA));
		RequestDispatcher rd;
		ServletContext ctx = getServletContext();
		int idLibro = Integer.parseInt(request.getParameter("idLibro"));

		if (idLibro == 0) {
			// invalid parameter
			String msg = String.format("Parametro %s non valido", request.getPathInfo());

			logger.info(msg);
			request.setAttribute("errore", msg);

			rd = ctx.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
			return;
		}

		libro = getLibro(idLibro);
		if (libro == null) {
			// invalid parameter
			String msg = String.format("Nessun libro trovato con id %d", idLibro);

			logger.info(msg);
			request.setAttribute("errore", msg);

			rd = ctx.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
			return;
		}

		request.setAttribute("action", "modifica");
		request.setAttribute("libro", libro);

		rd = getServletContext().getRequestDispatcher("/libro.jsp");
		rd.forward(request, response);
	}

	public void aggiungi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("breadcrumbs", new Breadcrumbs(Breadcrumbs.CATALOGO_AGGIUNGI));
		ServletContext ctx = getServletContext();
		request.setAttribute("action", "aggiungi");
		RequestDispatcher rd = ctx.getRequestDispatcher("/libro.jsp");
		rd.forward(request, response);
	}

	public void cancella(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();

		int idLibro = Integer.parseInt(request.getParameter("idLibro"));
		libro = new Libro(idLibro);
		LibroDaoImpl libroDao = new LibroDaoImpl(libro);

		if (!libroDao.exists()) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, String.format("Libro con id '%s' gia' rimosso o non presente a catalogo", idLibro))));
			return;
		}

		Prestito prestito = new PrestitoDaoImpl().getPrestitoByIdLibro(idLibro);
		if (prestito != null) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, String.format("Il libro con l'id %s e' in prestito all'utente %s, non possibile la cancellazione", prestito.getIdLibro(), prestito.getIdUtente()))));
			return;
		}

		if (!libroDao.delete()) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, "Errore durante il tentativo di rimozione del libro con id" + libro.getId())));
			return;
		}

		out.println(JSONMan.serializeJson(new StatusResponse(true, "Libro rimosso dal catalogo!", Integer.toString(libro.getId()))));
		return;
	}

	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();

		Libro libro = new Libro();
		libro.setId(Integer.parseInt(request.getParameter("idLibro")));
		libro.setTitolo(request.getParameter("titolo"));
		libro.setAutori(request.getParameter("autori"));
		libro.setEditore(request.getParameter("editore"));
		libro.setImageUrl(request.getParameter("url"));
		libro.setDescrizione(request.getParameter("descrizione"));

		if (!isValid(libro)) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, "Parametri input non validi")));
			return;
		}

		LibroDaoImpl libroDao = new LibroDaoImpl(libro);

		if (!libroDao.exists()) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, String.format("Libro con id '%s' non presente a catalogo", libro.getId()))));
			return;
		}

		if (!libroDao.update()) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, "Errore durante il tentativo di modifica del libro a catalogo, con id" + libro.getId())));
			return;
		}

		out.println(JSONMan.serializeJson(new StatusResponse(true, "Libro modificato con successo!", Integer.toString(libro.getId()))));
		return;
	}

	public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();

		Libro libro = new Libro();
		libro.setTitolo(request.getParameter("titolo"));
		libro.setAutori(request.getParameter("autori"));
		libro.setEditore(request.getParameter("editore"));
		libro.setImageUrl(request.getParameter("url"));
		libro.setDescrizione(request.getParameter("descrizione"));

		if (!isValid(libro)) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, "Parametri input non validi")));
			return;
		}

		LibroDaoImpl libroDao = new LibroDaoImpl(libro);

		if (libroDao.existsByTitolo()) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, String.format("Libro con titolo '%s' gia' presente a catalogo.", libro.getTitolo()))));
			return;
		}

		if (!libroDao.insert()) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, "Errore durante il tentativo di aggiunta del libro a catalogo.")));
			return;
		}

		out.println(JSONMan.serializeJson(new StatusResponse(true, "Libro aggiunto al catalogo!", Integer.toString(libro.getId()))));
		return;
	}

	private boolean isValid(Libro libro) {
		if (libro.getTitolo() == null || libro.getTitolo().equals("")) {
			return false;
		}

		if (libro.getAutori() == null || libro.getAutori().equals("")) {
			return false;
		}

		if (libro.getEditore() == null || libro.getEditore().equals("")) {
			return false;
		}

		if (libro.getImageUrl() == null || libro.getImageUrl().equals("")) {
			return false;
		}

		if (libro.getDescrizione() == null || libro.getDescrizione().equals("")) {
			return false;
		}

		return true;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private Libro getLibro(int idLibro) {
		return new LibroDaoImpl(idLibro).getLibro();
	}

	private boolean getIsAndroid(String input) {
		return input != null ? Boolean.parseBoolean(input) : false;
	}

	private boolean hasRights(Utente utente, String action) {
		if (action.equalsIgnoreCase("visualizza"))
			return true;

		if (utente == null) {
			return false;
		}

		if (utente != null && utente.isCliente())
			return false;

		return true;
	}

}
