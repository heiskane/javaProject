<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@tag import="java.util.ArrayList, webProject.model.UserPost" %>
	<div class="center">
		<h1 class="head">Search</h1>
		<form method="POST" action="/webProject/SearchServlet">
			<input type="text" name="search" /><br>
			<input type="submit" value="search" />
		</form>
	</div>
	
<%
if (request.getAttribute("results") != null) {
	ArrayList<UserPost> results = (ArrayList<UserPost>) request.getAttribute("results");
	if (results.size() == 0) {
		out.println("No posts found");
	}
	
	//Sort posts by newest
	for(int i = 0, j = results.size() - 1; i < j; i++) {
		results.add(i, results.remove(j));
	}
	
	for (UserPost post : results) { 
		String user = post.getUser(); %>
		  <div class="content">
		  	<h1><%=post.getTitle() %></h1>
		  	<p>Posted by: <b><%= user %></b></p>
		  	<p>Post date: <%=post.getDate() %></p>
		  	<div class="data">
		  		<p><%=post.getContent() %></p>
		  	</div>
	<%-- Show delete button only for admin and original poster --%>
		<% if (session.getAttribute("loggedInUser") != null) { %>
			<% if (session.getAttribute("loggedInUser").equals(user) || session.getAttribute("loggedInUser").equals("admin")) { %>
		  	<a href="${pageContext.request.contextPath}/PostContent?action=delete&id=<%=post.getId() %>">delete</a>
		  	<br><br>
		  	<% } %>
		<% } %>
		</div>
	<% } %>
<% } %>
	  
	