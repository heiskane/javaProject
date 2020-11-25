<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@tag import="webProject.model.dao.Dao, webProject.model.UserPost" %>
<%
int id;
try {
	id = Integer.parseInt(request.getParameter("id"));
} catch (NumberFormatException e) {
	response.sendRedirect("index.jsp");
	return;
}

request.setAttribute("id", id);

Dao dao = new Dao();
UserPost post = dao.getPostData(id);
String postUser = post.getUser();
String currentUser = (String) request.getSession().getAttribute("loggedInUser");

// Redirect if unauthorized to edit.
// This is also verified later in PostContent servlet
// Redirects cause the following error due to some overlap with requireLogin.tag or something
// ava.lang.IllegalStateException: Cannot call sendRedirect() after the response has been committed
if (currentUser.equals(postUser) || currentUser.equals("admin")) {
	String title = post.getTitle();
	String content = post.getContent();
	String date = post.getDate();

	request.setAttribute("title", title);
	request.setAttribute("content", content);
} else {
	response.sendRedirect("index.jsp");
	return;
}


%>