<jsp:include page="header.jsp"/>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="examples.pubhub.model.*" %>
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
	<h1>Add/Remove Tags</h1>
	<form id="modify" method="get" action="http://localhost:8080/PubHub/TagsController">
		<label for="searchby">Action</label>
		<select name="searchby">
			<option value="add">Add</option>
			<option value="remove">Remove</option>
		</select>
		<label style="margin-left: 2.5em" for="book">Book's ISBN:</label>
		<input type="text" id="book" name="book"/>
		<label style="margin-left: 2.5em" for="input">Tag(s):</label>
		<textarea id="input" name="input">separate tags with a comma, please</textarea>
		<button style="margin-left: 2.5em">Go!</button>
	</form>
	
	<br><br>
	
	<table id="mytable">
		<caption><c:out value="${dialogue}" /></caption>
		<thead>
			<tr>
				<td>ISBN-13:</td>
				<td>Title:</td>
				<td>Author:</td>
				<td>Publish Date:</td>
				<td>Price:</td>
				<td>Tags:</td>
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
	
	<a href="summon.jsp">Search for Books</a>
	
</div>
</section>

<jsp:include page="footer.jsp"/>