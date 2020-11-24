package webProject.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webProject.model.UserPost;
import webProject.model.dao.Dao;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Maybe implement the search to the main page later
		String search = request.getParameter("search");
		
		Dao dao = new Dao();
		ArrayList<UserPost> posts = dao.listAllPosts();
		ArrayList<UserPost> results = new ArrayList<UserPost>();
		
		for (UserPost post : posts)
		{
			// Search by title, content or user
			if (post.getContent().contains(search) || post.getTitle().contains(search) || post.getUser().contains(search))
			{
				results.add(post);
			}
		}
		
		request.setAttribute("results", results);
		request.getRequestDispatcher("search.jsp").forward(request, response);
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
