package webProject.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webProject.model.UserPost;
import webProject.model.dao.Dao;

/**
 * Servlet implementation class PostContent
 */
@WebServlet("/post")
public class PostContent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostContent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		// Get the previous page for redirections later
		String[] referHeader = request.getHeader("referer").split("/");
		String referer = "/" + referHeader[referHeader.length - 1];
		
		if (session.getAttribute("loggedInUser") != null)
		{
			String action = request.getParameter("action");
			if (action != null)
			{
				Dao dao = new Dao();
				if (action.equals("post"))
				{	
					String title = request.getParameter("title");
					// TODO Add base64 encoding to content later maybe?
					String content = request.getParameter("content");
					String currentUser = (String) session.getAttribute("loggedInUser");
					
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					LocalDateTime now = LocalDateTime.now();
					String date = dtf.format(now);
					
					dao.addPost(currentUser, title, content, date);
					
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				else if (action.equals("delete"))
				{
					// https://jaxenter.com/convert-java-string-int-134101.html
					int id = Integer.parseInt(request.getParameter("id"));
					// Get username from posts data
					String user = dao.getPostData(id).getUser();
					// Allow post deletion if username is admin or current user posted it
					if (session.getAttribute("loggedInUser").equals("admin") || session.getAttribute("loggedInUser").equals(user))
					{
						dao.deletePost(id);
						// https://stackoverflow.com/questions/12013707/servlet-forward-response-to-caller-previous-page
						// Redirect to previous page
						response.sendRedirect(request.getContextPath() + referer);	
					}
					else {
						response.sendRedirect(request.getContextPath() + "/index.jsp");
					}

				}
				else if (action.equals("update"))
				{
					// https://jaxenter.com/convert-java-string-int-134101.html
					// Check if user has permission to edit
					int id = Integer.parseInt(request.getParameter("id"));
					UserPost post = dao.getPostData(id);
					String user = post.getUser();
					if (session.getAttribute("loggedInUser").equals("admin") || session.getAttribute("loggedInUser").equals(user))
					{
						String title = request.getParameter("title");
						String content = request.getParameter("content");
						
						dao.updatePost(title, content, id);
						
						response.sendRedirect(request.getContextPath() + "/index.jsp");
					}
					else {
						response.sendRedirect(request.getContextPath() + "/index.jsp");
					}
				}
			}
		}
		else
		{
			request.getSession().setAttribute("error", "You must login to post");
			request.getRequestDispatcher("login.jsp").forward(request, response);
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
