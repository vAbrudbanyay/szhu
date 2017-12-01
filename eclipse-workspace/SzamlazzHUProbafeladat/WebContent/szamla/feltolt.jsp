<%@page import="com.abrudbanyay.pkg.DBcon"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.abrudbanyay.ApplicationDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Termék feltöltése</title>
</head>
<body>
<% 
ApplicationDAO dao = new ApplicationDAO();
DBcon dbc = new DBcon();
String email = request.getParameter("email");
String jelszo = request.getParameter("jelszo");
String termeknev[] = request.getParameterValues("termeknev[]");
String ar[] = request.getParameterValues("ar[]");
String sql = "select id from `felhasznalok` where email=? and jelszo=?";
PreparedStatement statement;
ResultSet result;

boolean sikeres = false;

Connection con = dbc.getConnection();

try {
	
	// első prepared statement az felhasználóID kinyeréséhez
	statement = con.prepareStatement(sql);
	statement.setString(1, email);
	statement.setString(2, jelszo);
	result = statement.executeQuery();
	String osszTermek="";
	int osszAr=0;
	int szamlaId=0;
	
	// ha van találat, megvan az id
	if (result.next()) {
		int id = result.getInt("id");
		try {
			// ebben az esetben megnézzük, hány terméket töltöttünk fel, elemenként hozzáadjuk az adatbázishoz
		for(int i=0; i<termeknev.length; i++){
			// prepared statement a termékek feltöltéséhez
			statement = con.prepareStatement("insert into `termektabla` (termeknev, ar, felhasznaloID) values (?, ?, ?)");
			statement.setString(1, termeknev[i]);
			statement.setString(2, ar[i] +"");
			statement.setString(3, id+"");
			statement.execute();
			// összefűzzük a termékek neveit, és összeadjuk az áraikat
				osszTermek+=termeknev[i]+" ";
				osszAr+=Integer.parseInt(ar[i]);
			}
			// harmadik ps-sel az első ps-sel kinyert id-val feltöltjük a szamlatabla táblába
			statement = con.prepareStatement(
					"insert into `szamlatabla` (eladott_termekek, osszeg, felhasznaloID) values (?,?,?)", 
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, osszTermek);
			statement.setString(2, osszAr+"");
			statement.setString(3, id+"");
			statement.executeUpdate();
			
			//siker esetén elkérjük az újonnan generált szamlaID-t
			result = statement.getGeneratedKeys();
			
			if(result.next()){
				szamlaId= result.getInt(1);
			}
			sikeres =true;
			
			// abszolút siker esetén kiállítunk egy számlát (html-t készítünk)
			if(sikeres){
				String aSzamla = dao.szamlakiallit(email, termeknev, ar, osszAr, szamlaId, id);
				%><script type="text/javascript">alert('A szamla sikeresen elkeszult!');</script>
				<%
				response.sendRedirect(aSzamla);
			}
			
		} catch (SQLException e) {
			%><script type="text/javascript">alert('<% e.getMessage(); %> ');</script>
			<%
		} finally {
			dbc.closeConnection(con);
			System.out.println(szamlaId);
		}
	} else
		%>
		<script type="text/javascript">alert('Hibás belépési adatok!');</script>
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