<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home Page</title>
	</head>
	<body style="text-align: center;">
		<h1>Type stuff</h1>
		<form method="get" action="/webServer/SendMessage">
		  <input type="text" name="name" />
		  <input type="submit" value="Send" />
		</form>
	</body>
</html>