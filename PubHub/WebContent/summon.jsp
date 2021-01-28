<jsp:include page="header.jsp" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="examples.pubhub.model.*" %>
<%@ page import="examples.pubhub.servlets.BookTagsControllerServlet" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<style>
		table {
			border: 1px solid black;
			width: 100%;
			border-collapse: collapse;
		}
		#mytable th,td {
			border: 1px solid black;
			padding: 8px;
		}
		#mytable tr:nth-child(even){background-color: #f2f2f2;}
		#mytable tr:hover {background-color: #ddd;}
		#mytable th {
  			padding-top: 12px;
  			padding-bottom: 12px;
  			text-align: left;
  			background-color: #4CAF50;
			color: white;
  		}
	</style>	
</head>

<body>
<section>
<div class="container">
	<h1>Find Book</h1>
	<form id="search" method="get" action="http://localhost:8080/PubHub/TagsController">
		<label for="searchby">Search By:</label>
		<select name="searchby">
			<option value="ISBN">ISBN</option>
			<option value="Title">Title</option>
			<option value="Author">Author</option>
			<option value="Price (up to)">Price (up to)</option>
			<option value="Tag">Tag</option>
		</select>
		<label style="margin-left: 2.5em" for="input">Input:</label>
		<input type="text" id="input" name="input"/>
		<button style="margin-left: 2.5em">Go!</button>
	</form>
	<br>

	<table id="mytable">
		<caption>Here's what we found for you:</caption>
		<thead>
			<tr>
				<th>ISBN-13:</th>
				<th>Title:</th>
				<th>Author:</th>
				<th>Publish Date:</th>
				<th>Price:</th>
				<th>Tags:</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="book" items="${books1}">
				<tr>
					<td><c:out value="${book.isbn13}" /></td>						
					<td><c:out value="${book.title}" /></td>
					<td><c:out value="${book.author}" /></td>
					<td><c:out value="${book.publishDate}" /></td>
					<td><c:out value="${book.price}" /></td>
					<td><c:out value="${book.tags}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<br><br>
	
	<a href="modify.jsp">Add/Remove Tags</a>
	
</div>
</section>
<jsp:include page="footer.jsp"/>