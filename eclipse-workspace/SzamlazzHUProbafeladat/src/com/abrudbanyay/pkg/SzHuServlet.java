package com.abrudbanyay.pkg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abrudbanyay.ApplicationDAO;

/**
 * Servlet implementation class SzHuServlet
 */
@WebServlet("/SzHuServlet")
public class SzHuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApplicationDAO dao = new ApplicationDAO();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SzHuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String action= request.getParameter("action");
		String user=request.getParameter("email");
		String vezNev=request.getParameter("vezeteknev");
		String kerNev=request.getParameter("keresztnev");
		String email = user;
		String jelszo=request.getParameter("jelszo");
		String jelszomegerositese = request.getParameter("jelszomegerositese");
		String hirlevel;
		PrintWriter writer = response.getWriter();
	
		if("reg".equals(action)){
			if(request.getParameter("hirlevel") == null){
				hirlevel ="N";
			} else{
				hirlevel ="Y";
			}
	
			if(!jelszo.equals(jelszomegerositese)){
				response.setContentType("text/html");
				writer.println("<script type=\"text/javascript\">");
				writer.println("alert('A két jelszó nem létezik!');");
				writer.println("</script>");
				
			} 
			
		 	if(request.getParameter("aszf-any")!=null && jelszo.equals(jelszomegerositese)){
				dao.create(vezNev, kerNev, email, jelszo, hirlevel);	
				response.sendRedirect("login.html");
			} else if(request.getParameter("aszf") == null){
				response.setContentType("text/html");
				writer.println("<script type=\"text/javascript\">");
				writer.println("alert('Az ASZF-et el kell fogadni!');");
				writer.println("</script>");
				
			}  
		}	
	}

}
