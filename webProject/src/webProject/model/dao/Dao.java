package webProject.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.sqlite.SQLiteException;

import webProject.model.UserPost;

public class Dao {

	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement prepped = null; 
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
		sql = "SELECT * FROM posts ORDER BY id DESC;";      
		try 
		{
			con = connect();
			if(con != null)
			{
				prepped = con.prepareStatement(sql);        		
        		rs = prepped.executeQuery();   
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

	public boolean doLogin(String user, String password) {
		sql = "SELECT * FROM users WHERE username = ? AND password = ?;";
		try 
		{
			con = connect();
			if (con != null) 
			{
				prepped = con.prepareStatement(sql);
    			prepped.setString(1, user);
    			prepped.setString(2, password);
        		rs = prepped.executeQuery();
        		if (rs != null) 
        		{
        			// Check if user with the password was found then close connection and return true
        			if (rs.next())
        			{
        				con.close();
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
	}
	
	public boolean addUser(String user, String password, String salt) {
		// Well apparently the id get automatically set
		sql = "INSERT INTO users(username, password, salt) VALUES(?, ?, ?);";
		try 
		{
			con = connect();
			if (con != null)
			{ 		
        		try 
        		{
        			prepped = con.prepareStatement(sql);
        			//prepped.setInt(1, id);
        			prepped.setString(1, user);
        			prepped.setString(2, password);
        			prepped.setString(3, salt);
        			prepped.executeUpdate();
        			con.close();
        			return true;
        		}
        		catch (SQLiteException e) 
        		{
        			e.printStackTrace();
        			System.out.println("Username taken");
        			return false;
        		}
			}
			con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean addPost(String user, String title, String content, String date) {
		sql = "INSERT INTO posts(username, title, content, date) VALUES(?, ?, ?, ?);";
		try 
		{
			con = connect();
			if (con != null)
			{ 		
    			prepped = con.prepareStatement(sql);
    			prepped.setString(1, user);
    			prepped.setString(2, title);
    			prepped.setString(3, content);
    			prepped.setString(4, date);
    			prepped.executeUpdate();
    			con.close();
    			return true;
			}
			con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public void deletePost(int id) {
		// https://www.sqlitetutorial.net/sqlite-java/delete/
		sql = "DELETE FROM posts WHERE id = ?;";
		try
		{
			con = connect();
			if (con != null)
			{
				prepped = con.prepareStatement(sql);
				prepped.setInt(1, id);
				prepped.executeUpdate();
			}
			con.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public UserPost getPostData(int id) {
		// https://www.sqlitetutorial.net/sqlite-java/delete/
		sql = "SELECT * FROM posts WHERE id = ?;";
		UserPost post = new UserPost();
		try
		{
			con = connect();
			if (con != null)
			{
					prepped = con.prepareStatement(sql);
					prepped.setInt(1, id);       		
	        		rs = prepped.executeQuery();
	        		// ID
	        		post.setId(id);
	        		// Username
	        		post.setUser(rs.getString(2));
	        		// Title
	        		post.setTitle(rs.getString(3));
	        		// Content
	        		post.setContent(rs.getString(4));
	        		// Date
	        		post.setDate(rs.getString(5));
			}
			con.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return post;
	}
	
	public String getUserSalt(String username) {
		// https://www.sqlitetutorial.net/sqlite-java/delete/
		sql = "SELECT salt FROM users WHERE username = ?;";
		String salt = "";
		try
		{
			con = connect();
			if (con != null)
			{
					prepped = con.prepareStatement(sql);
					prepped.setString(1, username);       		
	        		rs = prepped.executeQuery();
	        		salt = rs.getString(1);
			}
			con.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return salt;
	}
	
	public void updatePost(String title, String content, int id) {
		// https://www.sqlitetutorial.net/sqlite-java/delete/
		sql = "UPDATE posts SET title = ?, content = ? WHERE id = ?;";
		try
		{
			con = connect();
			if (con != null)
			{
					prepped = con.prepareStatement(sql);
					prepped.setString(1, title); 
					prepped.setString(2, content);
					prepped.setInt(3, id);
	        		prepped.executeUpdate();
			}
			con.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}













