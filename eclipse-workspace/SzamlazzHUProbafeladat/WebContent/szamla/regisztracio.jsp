<%@page import="com.abrudbanyay.ApplicationDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Regisztrácio</title>
</head>
<body>
        
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%
ApplicationDAO dao = new ApplicationDAO();
String user=request.getParameter("email");
//session.putValue("email", user);
String vezNev=request.getParameter("vezeteknev");
String kerNev=request.getParameter("keresztnev");
String email = user;
String jelszo=request.getParameter("jelszo");
String jelszomegerositese = request.getParameter("jelszomegerositese");
String hirlevel;

	if(request.getParameter("hirlevel") == null){
		hirlevel ="N";
	} else{
		hirlevel ="Y";
	}

	if(!jelszo.equals(jelszomegerositese)){
		%><script type="text/javascript">alert('A két jelszó nem létezik!');</script>
		<%
	} 
	
 	if(request.getParameter("aszf-any")!=null && jelszo.equals(jelszomegerositese)){
		dao.create(vezNev, kerNev, email, jelszo, hirlevel);	
		response.sendRedirect("login.html");
	} else if(request.getParameter("aszf") == null){
		%><script type="text/javascript">alert('Az ASZF-et el kell fogadni!');</script>
		<%
	}  
 %>

    
    </body>
</html>