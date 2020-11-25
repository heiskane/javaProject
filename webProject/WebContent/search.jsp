<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
	<t:requireLogin />
	<div class="center">
		<h1 class="head">Search</h1>
		<form method="POST" action="/webProject/search">
			<input type="text" name="search" /><br>
			<input type="submit" value="search" />
		</form>
	</div>
	<t:postList />
</t:layout>