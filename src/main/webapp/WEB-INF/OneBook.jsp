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
    <title>View One Book Page</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
	<!-- Book title -->
	<h1><c:out value="${selectedBook.title}"/></h1>
	<!-- welcomepage link -->
	<a href="/books">back to the shelves</a>
	<!-- book.user.name read book.title by book.author -->
	<p><c:out value="${selectedBook.user.username}"/> read <c:out value="${selectedBook.title}"/> by <c:out value="${selectedBook.author}"/>.</p>
	<!-- Here are book.user's thoughts -->
	<p>Here are <c:out value="${selectedBook.user.username}"/>'s thoughts:</p>
	
	<p><c:out value="${selectedBook.myThoughts}"/></p>

	<!-- edit book.id -->
	<!-- delete book.id -->	
	<c:if test="${selectedBook.user.id == userId}">
		<a href="/books/${selectedBook.id}/edit">Edit</a>
		<a href="/books/delete/${selectedBook.id}">Delete</a>
	</c:if>

</body>
</html>