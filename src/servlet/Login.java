package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.implement.UtenteDaoImpl;
import model.Utente;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = -1389065591634304311L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		ServletContext ctx = getServletContext();

		String nome = request.getParameter("nome");
		String password = request.getParameter("password");

		if (nome == null || password == null) {
			RequestDispatcher rd = ctx.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
			return;
		}

		Utente utente = new UtenteDaoImpl(nome, password).getUtente();

		// login fallito, mando messaggio alla jsp
		if (utente == null) {
			request.setAttribute("loginError", true);
			request.setAttribute("messaggio", "Utente o password errati. Riprovare");

			RequestDispatcher rd = ctx.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
			return;
		}

		// login ok, redirigo ad home
		HttpSession session = request.getSession();
		session.setAttribute("isLogged", true);
		session.setAttribute("utente", utente);
		response.sendRedirect(request.getContextPath() + "/home");
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
