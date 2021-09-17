<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<c:set var="path" 	value=" ${pageContext.request.contextPath}"/>
  <meta charset="utf-8">
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

li a, .dropbtn {
	display: inline-block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

li a:hover, .dropdown:hover .dropbtn {
	background-color: red;
}

li.dropdown {
	display: inline-block;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f9f9f9;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}

.dropdown-content a:hover {
	background-color: #f1f1f1;
}

.dropdown:hover .dropdown-content {
	display: block;
}
</style>
</head>

<body>
<header>

		<ul>
			<li><a href="#">Job Search Portal</a></li>
			<li><a href="${path}/student">Home</a></li>
			<li><a href="${path}/student/viewJobs">View Jobs</a></li>
			<li> <a href="${path}/student/appliedJobs">Applied Jobs</a></li>
			<li><a href="#">Contact</a></li>

			<c:choose>
				<c:when test="${sessionScope.userSession==null}">
					<li><a href="${path}/login">Login</a></li>
				</c:when>
				<c:when test="${sessionScope.userSession != null }">
					<li class="dropdown"><a> ${userSession.firstName} </a>
						<ul class="dropdown-content">
							<li><a href="${path}/logout">Logout</a></li>
						</ul></li>
				</c:when>
			</c:choose>
		</ul>


	</header>

</body>
</html>