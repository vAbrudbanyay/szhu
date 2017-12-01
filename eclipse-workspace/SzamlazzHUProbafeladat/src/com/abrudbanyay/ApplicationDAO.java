package com.abrudbanyay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import com.abrudbanyay.pkg.DBcon;

public class ApplicationDAO extends HttpServlet {
	private DBcon dbc = new DBcon();
	PreparedStatement statement;
	
	public void create (String vezNev, String kerNev, String email, String jelszo, String hirlevel) {
		String sql = "insert into `felhasznalok` (veznev, kernev, email, jelszo, hirlevel) values (?, ?, ?, ?, ?)";
		
		Connection con = dbc.getConnection();
		
		try {
			statement = con.prepareStatement(sql);
			statement.setString(1, vezNev);
			statement.setString(2, kerNev);
			statement.setString(3, email);
			statement.setString(4, jelszo);
			statement.setString(5, hirlevel);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		dbc.closeConnection(con);
		}
		
	}

	public String szamlakiallit(String email, String[]termeknev, String[] ar, int vegosszeg, int szamlaId, int id) {
		String sql = "select * from `felhasznalok` where id=?";
		String name ="";
		String elkeszitettSzamla = null;
		Connection con = dbc.getConnection();

		try {
			statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				name = result.getString("veznev") + " " + result.getString("kernev");
			}
			
				if(!name.equals("") && szamlaId>0) {
					try {
			            // StringBuilderrel elk�sz�t�nk egy html f�jlt
						StringBuilder htmlStringBuilder=new StringBuilder();
			            
						htmlStringBuilder.append("<html><head><title>Ki�ll�tott sz�mla</title><header><h1>N�v: "+name+"</h1></header></head>");
			            htmlStringBuilder.append("<body>");

			            // l�trehozunk egy t�bl�zatot a sz�mla inform�ci�knak
			            htmlStringBuilder.append("<table border=\"1\" bordercolor=\"#CCCCCC\">");
			            htmlStringBuilder.append("<tr><td><b>Ki�ll�tott sz�mla azonos�t�: "+szamlaId+"</b></td></tr>");
			            htmlStringBuilder.append("<tr><td></td></tr>");
			            htmlStringBuilder.append("</table>");

			            
			            // l�trehozunk egy t�bl�zatot a sz�mla t�teleknek
			            htmlStringBuilder.append("<table border=\"1\" bordercolor=\"#000000\">");
			            htmlStringBuilder.append("<tr><td><b>T�tel</b></td><td><b>Term�k n�v</b></td><td><b>�sszeg</b></td></tr>");
			            
			            // kit�lt�m a t�bl�zatot a term�k nev�vel �s az �rral
			            for(int i=0; i<termeknev.length; i++) {
			            	htmlStringBuilder.append("<tr><td>"+(i+1)+"</td><td>"+termeknev[i]+"</td><td>"+ar[i]+" HUF </td></tr>");	
			            }
			            
			            // egy sor �resen marad
			            htmlStringBuilder.append("<tr><td></td><td></td><td></td></tr>");
			            // hozz�adjuk a v�g�sszeget
			            htmlStringBuilder.append("<tr><td></td><td>V�g�sszeg: </td><td>"+vegosszeg+"HUF</td></tr>");
			            
			            htmlStringBuilder.append("</table></body></html>");
			            //html f�jlba �r�sa
			            elkeszitettSzamla = szamlaKeszito(htmlStringBuilder.toString(),""+name.substring(0,3)+"_"+szamlaId+".html");
			            
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				dbc.closeConnection(con);
				System.out.println(szamlaId);
			}
		return elkeszitettSzamla;
		}
	
		private String szamlaKeszito(String tartalom, String fileNev) throws IOException {
			//String utvonal = ".";
			String utvonal = "C:\\Users\\Vero\\eclipse-workspace\\SzamlazzHUProbafeladat\\WebContent\\szamla";
	        String tempFile = utvonal + File.separator+fileNev;
	        File file = new File(tempFile);
	        // if file does exists, then delete and create a new file
	        if (file.exists()) {
	            try {
	                File newFileName = new File(utvonal + File.separator+ "backup_"+fileNev);
	                file.renameTo(newFileName);
	                file.createNewFile();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        //write to file with OutputStreamWriter
	        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
	        Writer writer=new OutputStreamWriter(outputStream);
	        writer.write(tartalom);
	        writer.close();
	        return tempFile;
		}
	
}
