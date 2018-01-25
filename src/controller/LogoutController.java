package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

@WebServlet(name = "Logout", urlPatterns = { "/logout" })
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = -1389065591634304311L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setMaxInactiveInterval(1);
			session.invalidate();
		}
		
		// clear coockie
		Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        
		request.setAttribute("logout", true);
		
		boolean isAndroid =  !StringUtils.isEmpty(request.getParameter("isAndroid")) ? Boolean.parseBoolean(request.getParameter("isAdroid")) : false;
		if(isAndroid){
			String result = "{\"done\": \"true\"}";
			PrintWriter out = response.getWriter();
			out.append(result);
			out.close();
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/login");
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
