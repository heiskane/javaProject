<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>webProject</title>
</head>
<body style="text-align: center;">
	<div>
		<form method="POST" action="/webProject/LoginServlet">
			<p>Username: </p><input type="text" name="user" /><br>
			<p>Password: </p><input type="password" name="pass" /><br><br>
			<input type="submit" value="Login" name="submit" />
			<input type="submit" value="Register" name="submit" />
		</form>
	</div>
</body>
</html>