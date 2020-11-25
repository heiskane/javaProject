<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%
// If user hasnt logged in redirect to login page
if (session.getAttribute("loggedInUser") == null) {	
	// Dont think i need this
	//session.setAttribute("targetPage", request.getRequestURL());
	session.setAttribute("error", "You must be logged in");
	response.sendRedirect("login.jsp");
	return;
}

// Deny going to the page with the back buton after loggin out
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>