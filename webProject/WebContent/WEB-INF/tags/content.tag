<!-- https://stackoverflow.com/questions/1296235/jsp-tricks-to-make-templating-easier -->
<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@tag import="java.util.ArrayList, webProject.model.UserPost, webProject.model.dao.Dao" %>
	<div class="center">
<% if (session.getAttribute("loggedInUser") != null) { %>
		<h1>Welcome <% out.println(session.getAttribute("loggedInUser")); %></h1>
<% } else { %>
		<h1>Welcome</h1>
<% } %>
		<br>
		<form method="POST" action="/webProject/PostContent">
			<input type="text" name="title" placeholder="Title" /><br>
			<textarea name="content" rows="20" cols="100" placeholder="Content"></textarea>
	    	<input type="submit" value="post" name="action" />
		</form>
	</div>
<%
Dao dao = new Dao();
ArrayList<UserPost> posts = new ArrayList<UserPost>();
posts = dao.listAllPosts();

// Sort posts by newest
for(int i = 0, j = posts.size() - 1; i < j; i++) {
	posts.add(i, posts.remove(j));
}

// print the information about every category of the list
for(UserPost post : posts) { %>
	  <div class="content">
	  	<h1><%=post.getTitle() %></h1>
	  	<p>Posted by: <b><%=post.getUser() %></b></p>
	  	<p>Post date: <%=post.getDate() %></p>
	  	<p><%=post.getContent() %></p>
<% if (session.getAttribute("loggedInUser") != null && session.getAttribute("loggedInUser").equals("admin")) { %>
	  	<a href="${pageContext.request.contextPath}/PostContent?action=delete&id=<%=post.getId() %>">delete</a>
<% } %>	  	
	  </div>
<% } %>