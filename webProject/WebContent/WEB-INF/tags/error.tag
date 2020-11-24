<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@tag import="java.util.ArrayList, webProject.model.UserPost, webProject.model.dao.Dao" %>
	<div class="center">
<% if (session.getAttribute("error") != null) { %>
		<p>Error: <%= session.getAttribute("error") %></p>		
<% } %>
	</div>