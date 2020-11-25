<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
	<t:requireLogin />
	<t:edit />
	<div class="center">
		<h1 class="head">Edit post</h1>
		<form method="POST" action="/webProject/post">
			<input type="hidden" name="id" value="${ id }" />
			<input type="text" name="title" value="${ title }" /><br>
			<textarea name="content" rows="20" cols="100">${ content }</textarea><br>
	    	<input type="submit" class="button" value="update" name="action" />
		</form>
	</div>
</t:layout>