<!-- https://stackoverflow.com/questions/1296235/jsp-tricks-to-make-templating-easier -->
<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" >
<title>Homepage</title>
</head>
<body>
	<div class="nav">
		<ul>
			<li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
			<li><a href="${pageContext.request.contextPath}/index.jsp">Post?</a></li>
<% if (session.getAttribute("loggedInUser") != null) { %>
			<li style="float: right;"><a href="${pageContext.request.contextPath}/LoginServlet?submit=logout">Logout</a></li>
			<li style="float: right;"><a href="${pageContext.request.contextPath}/index.jsp">Account(not ready)</a></li>
<% } else { %>
			<li style="float: right;"><a href="${pageContext.request.contextPath}/login.jsp">Login</a></li>
<% } %>
		</ul>
	</div>
	<jsp:doBody/>
</body>
</html>