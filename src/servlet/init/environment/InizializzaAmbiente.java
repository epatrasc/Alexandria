package servlet.init.environment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.db.init.DatabaseInit;

@WebServlet(name = "InizializzaAmbiente", urlPatterns = { "/init" })
public class InizializzaAmbiente extends HttpServlet {
	private static final long serialVersionUID = -1389065591634304311L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		ServletContext ctx = getServletContext();
		PrintWriter out = response.getWriter();
		
		DatabaseInit initDb = new DatabaseInit(ctx);
		
		if (!initDb.isConnected()) {
			out.println("Database offline o configurazione errata");
			return;
		}
		
		if(initDb.create(true)) {
			out.println("Struttura db e dati creati");
			return;
		}
		
		out.println("Errore inizializzazione database. Visualizzare il log per maggiori info.");
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
