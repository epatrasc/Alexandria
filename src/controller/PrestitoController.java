package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.implement.LibroDaoImpl;
import dao.implement.PrestitoDaoImpl;
import dao.interfaces.LibroDao;
import dao.interfaces.PrestitoDao;
import model.Prestito;
import model.StatusResponse;
import model.Utente;
import utils.JSONManager;

@WebServlet("/prestito/*")
public class PrestitoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JSONManager JSONMan = new JSONManager();
	private int idLibro;
	private PrestitoDao prestito;
	private PrintWriter out;
	private Utente utente;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo().substring(1);
		HttpSession session = request.getSession();

		utente = (Utente) session.getAttribute("utente");
		idLibro = Integer.parseInt(request.getParameter("idLibro"));
		out = response.getWriter();
		
		switch (action) {
		case "dettaglio":
			PrestitoDaoImpl prestitoDao = new PrestitoDaoImpl();
			Prestito prestito = prestitoDao.getPrestitoById(utente.getId(), idLibro);
			out.println(JSONMan.serializeJson(prestito));
	        
			break;
		case "presta":
			presta();

			break;
		case "restituisci":
			restituisci();

			break;
		}
	}

	private void presta() {
		LibroDao libroDao = new LibroDaoImpl(idLibro);

		if (!libroDao.exists()) {
			out.println(JSONMan.serializeJson(new StatusResponse(true, String.format("Il libro con id = %d non esiste", idLibro))));
			return;
		}
		
		prestito = new PrestitoDaoImpl(utente.getId(), idLibro);
		if (prestito.exists()) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, "Hai gia' preso in prestito questo libro")));
			return;
		}

		if (prestito.presta()) {
			out.println(JSONMan.serializeJson(new StatusResponse(true, "Operazione conclusa con successo")));
			return;
		}

		out.println(JSONMan.serializeJson(new StatusResponse(true, "Errore durante l'operazione di inserimento")));
	}

	private void restituisci() {
		prestito = new PrestitoDaoImpl(utente.getId(), idLibro);
		if (!prestito.exists()) {
			out.println(JSONMan.serializeJson(new StatusResponse(false, "Hai gia' restituito questo libro")));
			return;
		}

		if (prestito.restituisci()) {
			out.println(JSONMan.serializeJson(new StatusResponse(true, "Operazione conclusa con successo")));
			return;
		}

		out.println(JSONMan.serializeJson(new StatusResponse(true, "Errore durante l'operazione di inserimento")));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
