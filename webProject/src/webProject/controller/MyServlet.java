package webProject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import webProject.model.Hash;
import webProject.model.UserPost;
import webProject.model.dao.Dao;

/**
 *	Test servlet to be removed later
 *	Test servlet to be removed later
 *	Test servlet to be removed later
 *	Test servlet to be removed later
 *	Test servlet to be removed later
 *	Test servlet to be removed later
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Dao dao = new Dao();
		
		
		/*
		String[] test = Hash.makeHash(null, "guest");
		String salt = test[0];
		String hash = test[1];
		System.out.println(salt + " " + hash);
		*/
		
		// Base64 testing
		/* String originalInput = "test input";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		
		System.out.println(encodedString);
		
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		
		System.out.println(decodedString);
		
		System.out.println(request.isRequestedSessionIdValid());
		
		InputStream test = getServletContext().getResourceAsStream("/WEB-INF/test.jsp");
		*/
		// https://stackoverflow.com/questions/3608891/pass-variables-from-servlet-to-jsp
		// request.getRequestDispatcher("index.jsp").forward(request, response);
		
		/*
		System.out.println("Kurssi.doGet()");
		
		String strJSON = null;
		ArrayList<User> users;
		Dao dao = new Dao();

		users = dao.listAll();
		strJSON = new JSONObject().put("Users: ", users).toString();
	
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(strJSON);
		*/
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
