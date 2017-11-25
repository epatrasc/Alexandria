package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.implement.PrestitoDaoImpl;

@WebServlet("/prestiti/*")
public class Prestiti extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo().substring(1);
		ServletContext ctx = getServletContext();
		PrintWriter out = response.getWriter();
		
		int idUtente = Integer.parseInt(request.getParameter("idUtente"));
		int idLibro = Integer.parseInt(request.getParameter("idLibro"));
		
		String status = "";
		switch(action){
		case "visualizza": 
            // TODO
            break;
		case "presta": 
            PrestitoDaoImpl prestito = new PrestitoDaoImpl(idUtente, idLibro);
            if(prestito.exists()){
            	status = "{\"status\": \"false\", \"messaggio\": \"Hai gia' in prestito questo libro\"}";
                out.println(status);
            	return;
            }
            
            prestito.insert();
            status = "{\"status\": \"true\", \"messaggio\": \"Operazione conclusa con successo\"}";
            out.println(status);
            
            break;
		case "restituisci": 
			// TODO
            break;
		}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
