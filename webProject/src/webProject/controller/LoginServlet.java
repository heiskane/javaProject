package webProject.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// import org.json.JSONObject;

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
		
		// Get name of the logged in user
		// https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSession.html
		HttpSession session = request.getSession();
		
		// https://stackoverflow.com/questions/13963720/how-to-effectively-destroy-session-in-java-servlet
		if (request.getParameter("submit").equals("logout"))
		{
			session.invalidate();
			response.sendRedirect("login.jsp");
			return;
		}
		
		// Handle empty usernames and/or passwords
		if (password == null || user == null || password.equals("") || user.equals(""))
		{
			request.getSession().setAttribute("error", "Username and password cannot be empty");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		else
		{
			String passwordHash;
			passwordHash = Hash.makeHash(password);
			
			Dao dao = new Dao();
			
			ArrayList<User> users;
			users = dao.listAllUsers();
			
			if (submit.equals("Login"))
			{	
				User localUser = findUser(user, users);
				
				// Verify password and user
				if (localUser != null && passwordHash.equals(localUser.getPassword()))
				{
					request.getSession().setAttribute("loggedInUser", user);
					response.getWriter().append("Logged in as: " + user);
					
					// Reset errors. Also find a better way to do this
					request.getSession().setAttribute("error", null);
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				else
				{
					request.getSession().setAttribute("error", "Username and/or password wrong");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			}
			else if (submit.equals("Register"))
			{
				if (findUser(user, users) != null)
				{
					request.getSession().setAttribute("error", "Username taken");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				else
				{
					int userCount = users.size();
					int id = users.get(userCount - 1).getId();
					
					// Set user id to last users id + 1
					if (dao.addUser(id + 1, user, passwordHash))
					{
						response.getWriter().append("User registered");
						request.getSession().setAttribute("loggedInUser", user);
						
						// Reset errors. Also find a better way to do this
						request.getSession().setAttribute("error", null);
						response.sendRedirect(request.getContextPath() + "/index.jsp");
					}
					else
					{
						request.getSession().setAttribute("error", "Something went wrong");
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
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
