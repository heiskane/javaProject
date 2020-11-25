<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@tag import="java.util.ArrayList, webProject.model.UserPost, webProject.model.dao.Dao" %>
<%
ArrayList<UserPost> posts = new ArrayList<UserPost>();
if (request.getAttribute("results") != null) {
	posts = (ArrayList<UserPost>) request.getAttribute("results");
} else {
	Dao dao = new Dao();
	posts = dao.listAllPosts();
}

// Sort posts by newest
for(int i = 0, j = posts.size() - 1; i < j; i++) {
	posts.add(i, posts.remove(j));
}

for (UserPost post : posts) { 
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
	  	<a href="${pageContext.request.contextPath}/post?action=delete&id=<%=post.getId() %>">delete</a>
	  	<a href="${pageContext.request.contextPath}/edit.jsp?id=<%=post.getId() %>">edit</a>
	  	<br><br>
	  	<% } %>
	<% } %>
	</div>
<% } %>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	