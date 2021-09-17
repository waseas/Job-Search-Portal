<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

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
<%@ include file="student-header.jsp"%>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />


	<div align="center">
		<form:form method="post" name="contact-form"
			action="${contextPath}/student/applyJob" modelAttribute="files"
			enctype="multipart/form-data">
			<h2>Upload Files</h2>

			<div style="color: red">${error}</div>
			<input type="hidden" name="id" value="${id}">
			<br>
			<div>
				<label>Resume/CV</label>
				<form:input type="file" class="form-control" path="resume"
					placeholder="Upload CV" required="required" style="height : 30px" />
			</div>
			<div>
				<label>Cover Letter</label>
				<form:input type="file" class="form-control" path="coverLetter"
					placeholder="Upload CoverLetter" required="required"
					style="height : 30px" />
			</div>
			<br/>
			<div style="margin-left: 0%">
				<button type="submit" style="height: 40px">Submit</button>
			</div>
		</form:form>

	</div>
</body>

</html>