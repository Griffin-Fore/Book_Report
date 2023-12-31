<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) --> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
	<h1>Welcome, <c:out value="${user.username}"/></h1>
	<p>Books from everyone's shelves.</p>
	<a href="/auth/logout">logout</a>
	<a href="/books/new">+ Add to my shelf!</a>
	<!-- All books list -->
	<!-- book.id | book.name | book.author | book.user.name -->
	<!-- the name is a link to /books/${book.id}-->
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Title</th>
				<th>Author Name</th>
				<th>Posted By</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="onebook" items="${all_books}">
				<tr>
					<td><c:out value="${onebook.id}"/></td>
					<td><a href="/books/${onebook.id}"><c:out value="${onebook.title}"/></a></td>
					<td><c:out value="${onebook.author}"/></td>
					<td><c:out value="${onebook.user.username}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>

