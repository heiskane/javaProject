<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@tag import="java.util.ArrayList, webProject.model.UserPost, webProject.model.dao.Dao" %>
	<div class="center">
<% if (session.getAttribute("loggedInUser") != null) { %>
		<h1 class="head">Hello <%= session.getAttribute("loggedInUser") %></h1>		
<% } %>
	</div>