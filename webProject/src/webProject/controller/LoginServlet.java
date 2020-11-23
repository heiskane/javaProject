package webProject.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import webProject.model.Hash;
import webProject.model.User;
import webProject.model.dao.Dao;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("user");
		String password = request.getParameter("pass");
		
		String submit = request.getParameter("submit");
		
		System.out.println(request.getContextPath());
		
		String passwordHash = Hash.makeHash(password);
		
		Dao dao = new Dao();
		
		ArrayList<User> users;
		users = dao.listAll();
		
		if (submit.equals("Login"))
		{	
			User localUser = findUser(user, users);
			if (passwordHash.equals(localUser.getPassword()))
			{
				request.getSession().setAttribute("loggedInUser", user);
				response.getWriter().append("Logged in as: " + user);
			}
			else
			{
				response.getWriter().append("Login failed");
			}
		}
		else if (submit.equals("Register"))
		{
			if (findUser(user, users) != null)
			{
				response.getWriter().append("Username taken!");
			}
			else
			{
				int userCount = users.size();
				// Set user id to length of users list + 1
				if (dao.addUser(userCount + 1, user, passwordHash))
				{
					response.getWriter().append("User registered");
				}
				else
				{
					response.getWriter().append("Registration failed");
				}
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private static User findUser(String username, ArrayList<User> users) {
		for (User i : users)
		{
			String name = i.getUsername();
			
			if (name.equals(username))
			{
				return i;
			}
			else if (users.indexOf(i) == users.size() - 1)
			{
				return null;
			}
		}
		return null;
	}

}
