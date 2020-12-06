package webProject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// import org.json.JSONObject;

import webProject.model.Hash;
import webProject.model.dao.Dao;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
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
		
		// https://stackoverflow.com/questions/1265282/recommended-method-for-escaping-html-in-java
		// Sanitize username. Not sure if this is the best place to do this
		// but it must be sanitized because is is displayed on the page
		user = escapeHtml4(user);
		String password = request.getParameter("pass");
		String submit = request.getParameter("submit");
		
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
			Dao dao = new Dao();
			
			if (submit.equals("Login"))
			{	
				String salt = dao.getUserSalt(user);
				// Use the salt in the database for hashing
				String passwordHash = Hash.makeHash(salt, password)[1];
				// Check if username and password match
				if (dao.doLogin(user, passwordHash))
				{
					// Set session cookie username
					request.getSession().setAttribute("loggedInUser", user);
					response.getWriter().append("Logged in as: " + user);
					
					// Reset errors. Also find a better way to do this
					request.getSession().setAttribute("error", null);
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				} 
				else
				{
					// Set error message
					request.getSession().setAttribute("error", "Username and/or password wrong");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				
			}
			else if (submit.equals("Register"))
			{
				// Random salt is created when null is used
				String[] saltedHash = Hash.makeHash(null, password);
				if (dao.addUser(user, saltedHash[1], saltedHash[0]))
				{
					response.getWriter().append("User registered");
					request.getSession().setAttribute("loggedInUser", user);
					
					// Reset errors. Also find a better way to do this
					request.getSession().setAttribute("error", null);
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				else
				{
					request.getSession().setAttribute("error", "Username taken");
					request.getRequestDispatcher("login.jsp").forward(request, response);
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

}
