<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Database content retrieval</title>
	</head>
	
	<h1> List of sellers: </h1>

	<body>
	<h1>  </h1>
	
	<c:forEach var="i" items="${names}">
   		Name <c:out value="${i.getCustomerFirstName()}"/><p>
   		<li>${i}</li>
	</c:forEach>
	
	<p>
	<a href="logout">Logout</a>
	</body>
</html>