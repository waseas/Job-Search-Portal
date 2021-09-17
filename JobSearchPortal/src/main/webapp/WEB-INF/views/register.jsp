<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">
<head>
<meta charset="utf-8">

<title>Registration Form</title>

</head>
<%@ include file="header.jsp"%>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<script>
		$(document).ready(function() {
			if (window.history.replaceState) {
				window.history.replaceState(null, null, window.location.href);
			}
			$("form").submit(function(e) {
				if (!$("input[name='role']:checked").val()) {
					alert('Nothing is checked!Kindly choose role');
					e.preventDefault();
				}
			});
		});
		function checkPass() {

			var password = document.getElementById('password');
			var c_password = document.getElementById('c_password');

			var message = document.getElementById('confirmMessage');

			var goodColor = "#66cc66";
			var badColor = "#ff6666";

			if (password.value == c_password.value) {

				c_password.style.backgroundColor = goodColor;
				message.style.color = goodColor;
				message.innerHTML = "Passwords Match!"
			} else {

				c_password.style.backgroundColor = badColor;
				message.style.color = badColor;
				message.innerHTML = "Passwords Do Not Match!"
			}
		}
	</script>

	<div align="center">
		<form:form name="contact-form" method="post"
			action="${contextPath}/register" modelAttribute="user">
			<h2>Register</h2>
			<div style="color: red">${error}</div>
			<div>
				<form:input type="text" class="form-control" path="firstName"
					placeholder="First Name" required="required" style="height : 30px" />
				<form:errors path="firstName" cssClass="error" />
			</div>
			<br/>
			<div>
				<form:input type="text" class="form-control" path="lastName"
					placeholder="Last Name" required="required" style="height : 30px" />
				<form:errors path="lastName" cssClass="error" />
			</div>
			<br/>
			<div>
				<form:input type="email" class="form-control" path="username"
					placeholder="Email" required="required" style="height : 30px" />
				<form:errors path="username" cssClass="error" />
			</div>
			<br/>
			<div>
				<form:input type="password" class="form-control" id="password"
					path="password" placeholder="Password" required="required"
					style="height : 30px" />
				<form:errors path="password" cssClass="error" />
			</div>
			<br/>
			<div>
				<form:input type="password" class="form-control" id="c_password"
					path="confirmPassword" placeholder="Confirm Password"
					required="required" style="height : 30px" onkeyup="checkPass()" />
				<p id="confirmMessage"></p>
			</div>
			<div>
				<label>You Are ?</label><br> <input
					type="radio" name="role" value="ROLE_STUDENT" /> Student
				&nbsp&nbsp <input type="radio" name="role" value="ROLE_EMPLOYEE" />
				Employer <br> <br>
			</div>

			<div style="margin-left: 0%">
				<label class="checkbox-inline"><input type="checkbox"
					required="required"> I accept the <a href="#">Terms of
						Use</a> &amp; <a href="#">Privacy Policy</a></label>
			</div>
			<br/>
			<div style="margin-left: 0%">
				<button type="submit" style="height: 40px">Register Now</button>
			</div>
		</form:form>
		<div>
			Already have an account? <a href="${path}/login">Sign in</a>
		</div>
	</div>
</body>

</html>