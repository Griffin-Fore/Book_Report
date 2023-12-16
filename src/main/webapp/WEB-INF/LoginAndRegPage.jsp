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
    <title>Login And Registration Page</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
   <h1>Book Club</h1>
   <p>A place for friends to share thoughts on books.</p>
   <!-- Register form -->
   <c:out value="${passwordMatch}"/>
   <c:out value="${invalidEmail}"/>
   <c:out value="${invalidPassword}"/>
   <c:out value="${notLoggedIn}"/>
   <h1>Register</h1>
   		<form:form action="/auth/register" method="POST" modelAttribute="Register">
   		
   			<form:label path="username" class="form-label">Name:</form:label>
   			<form:input path="username" class="form-control"/>
   			<form:errors path="username"/>
   			
   			<form:label path="email" class="form-label">Email:</form:label>
   			<form:input type="email" path="email" calss="form-control"/>
   			<form:errors path="email"/>
   			
   			<form:label path="password" class="form-label">Password:</form:label>
   			<form:input type="password" path="password" class="form-control"/>
   			<form:errors path="password"/>
   			
   			<form:label path="confirmPassword" class="form-label">Confirm PW:</form:label>
   			<form:input type="password" path="confirmPassword" class="form-control"/>
   			<form:errors path="confirmPassword"/>
   			
   			<button type="submit">Submit</button>
   		</form:form>
   		
   <!-- Login form -->
   		<h1>Log In</h1>
   		<form:form action="/auth/login" method="POST" modelAttribute="LoginPath">
   			<form:label path="email" class="form-label">Email:</form:label>
   			<form:input type="email" path="email" class="form-control"/>
   			<form:errors path="email"/>
   			
   			<form:label path="password" class="form-label">Password:</form:label>
   			<form:input type="password" path="password" class="form-control"/>
   			<form:errors path="password"/>
   			
   			<button type="submit">Submit</button>
   		</form:form>
</body>
</html>

