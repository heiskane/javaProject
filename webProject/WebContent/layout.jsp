<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<div class="page">
	  <h1 class="head">Login</h1>
	  <form action="/webProject/LoginServlet" method="POST">
		  <label class="field field_v2">
		    <input class="field__input" placeholder="e.g. John">
		    <span class="field__label-wrap">
		      <span class="field__label">First name</span>
		    </span>
		  </label>
		  <label class="field field_v2">
		    <input class="field__input" placeholder="e.g. Smith">
		    <span class="field__label-wrap">
		      <span class="field__label">Last name</span>
		    </span>
		  </label>
		  <input type="submit" value="Login" name="submit" />
	  </form>
	</div>
</t:layout>