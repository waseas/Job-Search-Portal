<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<c:set var="path" value=" ${pageContext.request.contextPath}" />
<meta charset="utf-8">
<title>Job Search Portal</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #333;
}

li {
	float: left;
}

li a {
	display: block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

li a:hover:not(.active) {
	background-color: #111;
}

.active {
	background-color: #4CAF50;
}
</style>
</head>

<body>
	<header>

		<ul>
			<li><a href="${path}/">Job Search Portal</a></li>
			<li><a href="${path}/">Home</a></li>
			<li><a href="${path}/login">Login</a></li>
			<li><a href="${path}/register">Register</a></li>
		</ul>


	</header>
</body>
</html>