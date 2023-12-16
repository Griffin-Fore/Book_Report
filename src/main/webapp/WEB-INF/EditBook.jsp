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
    <title>Edit Book Page</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
	<!-- Change Your Entry -->
	<!-- back to welcomepage -->
	<!-- Edit book form includes only Title, author and myThoughts, user is left off the form -->
	<h1>Change your Entry</h1>
	<a href="/books">back to the shelves</a>
	<form:form action="/edit_book/${existingBook.id}" method="POST" modelAttribute="EditBook">
		<input type="hidden" name="_method" value="PUT">
		
		<form:label path="title">Title:</form:label>
		<form:input path="title" value="${existingBook.title}"/>
		<form:errors path="title"/>
		
		<form:label path="author">Author:</form:label>
		<form:input path="author" value="${existingBook.author}"/>
		<form:errors path="author"/>
		
		<form:label path="myThoughts">My thoughts:</form:label>
		<form:input path="myThoughts" value="${existingBook.myThoughts}"/>
		<form:errors path="myThoughts"/>
		
		<input type="submit" value="Submit"/>
	</form:form>
</body>
</html>