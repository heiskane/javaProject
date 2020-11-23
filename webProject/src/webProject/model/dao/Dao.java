package webProject.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import webProject.model.User;

public class Dao {

	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep = null; 
	private String sql;
	private String db = "webProject/project.db3";

	public Dao() {
		// TODO Auto-generated constructor stub
	}
	
	private Connection connect(){
    	Connection con = null;    	
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); //Eclipsessa
    	// System.out.println("Polku on: " + path);
    	// path += "/webapps/"; //Tuotannossa. Laita kanta webapps-kansioon.
    	String url = "jdbc:sqlite:"+path+db;    	
    	try 
    	{	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        System.out.println("Connected.");
	     }
    	catch (Exception e)
    	{	
	    	 System.out.println("Connection Failed.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public ArrayList<User> listAll(){
		ArrayList<User> users = new ArrayList<User>();
		sql = "SELECT * FROM users";       
		try 
		{
			con = connect();
			if(con != null)
			{
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs != null)
				{
					while(rs.next())
					{
						User user = new User();
						user.setId(rs.getInt(1));
						user.setUsername(rs.getString(2));
						user.setPassword(rs.getString(3));
						users.add(user);
					}					
				}
			}
			con.close();
			System.out.println("Connection closed");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		return users;
	}
	
	// Replace with method in LoginServlet?
	/*
	public boolean doLogin(String user, String password) {
		sql = "SELECT * FROM users WHERE username ='" + user + "' AND password ='" + password + "'";
		try 
		{
			con = connect();
			if (con != null) 
			{
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();
        		if (rs != null) 
        		{
        			if (rs.next())
        			{
        				return true;
        			}
        		}
			}
			con.close();
			System.out.println("Connection closed");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}*/
	
	public boolean addUser(int id, String user, String password) {
		sql = "INSERT INTO users VALUES('" + id + "','" + user + "','" + password + "')";
		try 
		{
			con = connect();
			if (con != null)
			{
				Statement statement = con.createStatement(); 		
        		try 
        		{
        			statement.executeUpdate(sql);
        		}
        		catch (Exception e) 
        		{
        			e.printStackTrace();
        			System.out.println("Registration failed");
        			return false;
        		}
			}
			con.close();
			System.out.println("Connection closed");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return true;
	}

}
