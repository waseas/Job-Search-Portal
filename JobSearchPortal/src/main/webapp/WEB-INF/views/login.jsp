<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<html lang="en">
<head>
<title>Login Form</title>

<style>
* {
	box-sizing: border-box;
}

input[type=text], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	resize: vertical;
}

label {
	padding: 12px 12px 12px 0;
	display: inline-block;
}

input[type=submit] {
	background-color: #4CAF50;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	float: right;
}

input[type=submit]:hover {
	background-color: #45a049;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

.col-25 {
	float: left;
	width: 25%;
	margin-top: 6px;
}

.col-75 {
	float: left;
	width: 75%;
	margin-top: 6px;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}

/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
	.col-25, .col-75, input[type=submit] {
		width: 100%;
		margin-top: 0;
	}
}
</style>

</head>
<%@ include file="header.jsp"%>

<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<div align="center">
		<form:form method="post" name="contact-form"
			action="${contextPath}/login" modelAttribute="user">
			<h2>Login</h2>

			<div style="color: red">${error}</div>

			<div>
				<form:input type="email" class="form-control" path="username"
					placeholder="Email" required="required" style="height : 30px" />
			</div>
			<br />
			<div>
				<form:input type="password" class="form-control" path="password"
					placeholder="Password" required="required" style="height : 30px" />
			</div>
			<br />
			<div>
				<button type="submit" style="height: 40px">Sign in</button>
			</div>
		</form:form>
		<div class="text-center">
			Don't have an account? <a href="${contextPath}/register">Register
				Here!!</a>
		</div>
	</div>


</body>



</html>