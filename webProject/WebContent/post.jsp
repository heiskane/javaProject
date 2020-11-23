<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
	<div class="center">
		<h1 class="head">Write a post</h1>
		<form method="POST" action="/webProject/PostContent">
			<input type="text" name="title" placeholder="Title" /><br>
			<textarea name="content" rows="20" cols="100" placeholder="Content"></textarea><br>
	    	<input type="submit" class="button" value="post" name="action" />
		</form>
	</div>
</t:layout>
