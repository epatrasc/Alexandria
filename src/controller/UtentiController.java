package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.implement.UtentiDaoImpl;
import model.Utente;
import utils.JSONManager;

@WebServlet("/utenti/cliente/*")
public class UtentiController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private JSONManager JSONMan = new JSONManager();
	private PrintWriter out;

	public UtentiController() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setAttribute("menu_active", "catalogo");
		HttpSession session = request.getSession();
		boolean isAndroid = getIsAndroid(request.getParameter("isAndroid"));

		Utente utente = (Utente) session.getAttribute("utente");
		boolean hasRights = utente!= null && utente.isAmministratore();
		
		out = response.getWriter();
		List<Utente> utenti = new ArrayList<Utente>();
		
		if (isAndroid && hasRights) {
			utenti = getUtenti();
		}
		
		out.println(JSONMan.serializeJson(utenti));
	}

	private boolean getIsAndroid(String input) {
		return input != null ? Boolean.parseBoolean(input) : false;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private List<Utente> getUtenti() {
		return new UtentiDaoImpl().getUtenti();
	}
}
