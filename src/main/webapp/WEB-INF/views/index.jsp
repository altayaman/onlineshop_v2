<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

	<head>
		<meta charset="UTF-8">
		<title>Index</title>
	</head>

	<h1> Welcome to our online store </h1>

	<body>
		<form action = "index" method = "POST" >
			<fieldset>
				Email: <input name = "email"><p>
				Password: <input name = "password"><p>

				<input type = "submit" value = "  Login  "/><p>
				<a href="clientRegistration">Create new account</a>
			</fieldset>
		</form>
	</body>
	
</html>