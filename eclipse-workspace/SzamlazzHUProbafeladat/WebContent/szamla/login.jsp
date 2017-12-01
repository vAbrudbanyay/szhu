<%@page import="com.abrudbanyay.pkg.DBcon"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.abrudbanyay.ApplicationDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Belépés</title>
</head>
<body>
	<%
		ApplicationDAO dao = new ApplicationDAO();
		DBcon dbc = new DBcon();
		PreparedStatement statement;
		ResultSet result;
		String user = request.getParameter("email");
		session.putValue("email", user);
		String email = user;
		String jelszo = request.getParameter("jelszo");

		String sql = "select id from `felhasznalok` where email=? and jelszo=?";
		Connection con = dbc.getConnection();

		try {
			statement = con.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, jelszo);
			result = statement.executeQuery();
			if (result.next()) {
				int id = result.getInt("id");
				response.sendRedirect("feltolt.html");
		} else
			%>
			<script type="text/javascript">alert('Az email cím nincs regisztrálva a rendszerben!');</script>
			<%
		} catch (SQLException e) {
			%><script type="text/javascript">alert('<% e.getMessage(); %> ');</script>
			<%
		} finally {
			dbc.closeConnection(con);
		}
	%>
</body>
</html>