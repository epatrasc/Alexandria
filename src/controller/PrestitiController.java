package controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import model.Breadcrumbs;
import model.Prestito;
import model.Utente;
import utils.JSONManager;

@WebServlet("/prestiti")
public class PrestitiController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("menu_active", "prestiti");
		request.setAttribute("breadcrumbs", new Breadcrumbs(Breadcrumbs.DEFAULT_PRESTITI));
		HttpSession session = request.getSession();
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = null;

		Utente utente = (Utente) session.getAttribute("utente");
		PrestitiDao prestitiDao = new PrestitiDaoImpl();
		boolean isAmministratore = utente.getRuolo().equals(Utente.amministratore());
		List<Prestito> prestiti = null;
		
		if(isAmministratore){
			prestiti = prestitiDao.getPrestiti();
		}else{
			prestiti = prestitiDao.getPrestitiByUserId(utente.getId());
		}
		
		boolean isAndroid = request.getParameter("isAndroid") != null ? Boolean.parseBoolean(request.getParameter("isAndroid")) : false;
		if(isAndroid){
			JSONManager JSONMan = new JSONManager();
			String json = JSONMan.serializeJson(prestiti);
			
			PrintWriter out = response.getWriter();
			out.append(json);
			out.close();
			return;
		}
		
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
