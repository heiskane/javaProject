package webProject.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.sql.Statement;
import java.util.ArrayList;

import webProject.model.User;
import webProject.model.UserPost;

public class Dao {

	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep = null; 
	private String sql;
	private String db = "webProject/project.db3";

	public Dao() {
		// TODO Auto-generated constructor stub
	}
	
	private Connection connect() {
    	Connection con = null;    	
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); //Eclipsessa
    	// System.out.println("Polku on: " + path);
    	// path += "/webapps/"; //Tuotannossa. Laita kanta webapps-kansioon.
    	String url = "jdbc:sqlite:" + path + db;    	
    	try 
    	{	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);
	     }
    	catch (Exception e)
    	{	
	    	System.out.println("Connection Failed.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public ArrayList<UserPost> listAllPosts() {
		ArrayList<UserPost> posts = new ArrayList<UserPost>();
		sql = "SELECT * FROM posts";       
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
						UserPost post = new UserPost();
						post.setId(rs.getInt(1));
						post.setUser(rs.getString(2));
						post.setTitle(rs.getString(3));
						post.setContent(rs.getString(4));
						post.setDate(rs.getString(5));
						posts.add(post);
					}					
				}
			}
			con.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		return posts;
	}
	
	public ArrayList<User> listAllUsers() {
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
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}*/
	
	public boolean addUser(int id, String user, String password) {
		//sql = "INSERT INTO users VALUES('" + id + "','" + user + "','" + password + "')";
		sql = "INSERT INTO users(id, username, password) VALUES(?, ?, ?)";
		try 
		{
			con = connect();
			if (con != null)
			{ 		
        		try 
        		{
        			// Apparently this sanitizes?
        			PreparedStatement prepped = con.prepareStatement(sql);
        			prepped.setInt(1, id);
        			prepped.setString(2, user);
        			prepped.setString(3, password);
        			prepped.executeUpdate();
        		}
        		catch (Exception e) 
        		{
        			e.printStackTrace();
        			System.out.println("Registration failed");
        			return false;
        		}
			}
			con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return true;
	}

	public boolean addPost(int id, String user, String title, String content, String date) {
		sql = "INSERT INTO posts(id, username, title, content, date) VALUES(?, ?, ?, ?, ?)";
		try 
		{
			con = connect();
			if (con != null)
			{ 		
        		try 
        		{
        			PreparedStatement prepped = con.prepareStatement(sql);
        			prepped.setInt(1, id);
        			prepped.setString(2, user);
        			prepped.setString(3, title);
        			prepped.setString(4, content);
        			prepped.setString(5, date);
        			prepped.executeUpdate();
        		}
        		catch (Exception e) 
        		{
        			e.printStackTrace();
        			return false;
        		}
			}
			con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public void deletePost(int id) {
		// https://www.sqlitetutorial.net/sqlite-java/delete/
		sql = "DELETE FROM posts WHERE id = ?";
		try
		{
			con = connect();
			if (con != null)
			{
				try
				{
					PreparedStatement prepped = con.prepareStatement(sql);
					prepped.setInt(1, id);
					prepped.executeUpdate();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
