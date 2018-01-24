package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.implement.UtenteDaoImpl;
import model.StatusResponse;
import model.Utente;
import utils.JSONManager;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = -1389065591634304311L;
	private JSONManager JSONMan = new JSONManager();
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setAttribute("menu_active", "login");
		
		ServletContext ctx = getServletContext();
		
		String nome = request.getParameter("nome");
		String password = request.getParameter("password");
		boolean isAndroid = getIsAndroid(request.getParameter("isAndroid"));
		
		if (nome == null || password == null) {
			if(isAndroid){
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.append(JSONMan.serializeJson(new StatusResponse(false, String.format("Parametri input non validi"))));
				out.close();
				return;
			}
			
			RequestDispatcher rd = ctx.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
			return;
		}
		
		
		Utente utente = new UtenteDaoImpl(nome, password).getUtente();

		// login fallito, utente o password non validi
		if (utente == null) {
			if(isAndroid){
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.append(JSONMan.serializeJson(new StatusResponse(false, String.format("Utente o password errati. Riprovare"))));
				out.close();
				return;
			}
			
			request.setAttribute("loginError", true);
			request.setAttribute("messaggio", "Utente o password errati. Riprovare");

			RequestDispatcher rd = ctx.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
			return;
		}

		// login ok, se android mando json se no redirigo su home
		HttpSession session = request.getSession();
		session.setAttribute("isLogged", true);
		session.setAttribute("utente", utente);
		
		if(isAndroid){
			response.setContentType("application/json");
			String loginOk = String.format("{\"done\": \"true\", utente: %s}", JSONMan.serializeJson(utente));
			PrintWriter out = response.getWriter();
			out.append(loginOk);
			out.close();
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/home");
	}

	private boolean getIsAndroid(String input) {
		return input != null ? Boolean.parseBoolean(input) : false;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}
