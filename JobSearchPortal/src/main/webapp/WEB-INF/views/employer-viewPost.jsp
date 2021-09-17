<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>View Posts</title>
<style>
table {
  width:100%;
}
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
th, td {
  padding: 10px;
  text-align: left;
}
#t01 tr:nth-child(even) {
  background-color: #eee;
}
#t01 tr:nth-child(odd) {
 background-color: #fff;
}
#t01 th {
  background-color: black;
  color: white;
}
</style>
</head>
<%@ include file="employer-header.jsp"%>
<body>
	<c:set var="path" value=" ${pageContext.request.contextPath}" />


	<div style="padding: 20px";>
		<h3>Job Posted By ${sessionScope.userSession.firstName}</h3>
		<div style="color: red">${message}</div>
		<table>
			<tr>
				<td><b>JobId</b></td>
				<td><b>Title</b></td>
				<td><b>Company Name</b></td>
				<td><b>Job Type</b></td>
				<td><b>Location</b>
				<td><b>Industry</b>
				<td><b>Major</b>
				<td><b>Minimum GPA</b></td>
				<td style="width: 5%;"><b>Job URL</b></td>
				<td style="width: 10%;"><b>Description</b></td>
				<td style="width: 25%;"><b>Action to be Performed</b></td>
			</tr>
			<c:forEach var="list" items="${list}">
				<tr>
					<td>${list.jobId}</td>
					<td>${list.title}</td>
					<td>${list.companyName}</td>
					<td>${list.typeOfJob}</td>
					<td>${list.state}, ${list.country}</td>
					<td>${list.industry}</td>
					<td>${list.major}</td>
					<td>${list.minGpa}</td>
					<td>${list.jobUrl}</td>
					<td>${list.description}</td>
					<td><a
						href="${path}/employer/updateJobDetails.htm?jobID=${list.jobId}"
						role="button" aria-pressed="true">Update</a> &nbsp;&nbsp; <a
						href="${path}/employer/deleteJob.htm?jobID=${list.jobId}"
						role="button">Delete</a> &nbsp;&nbsp;<br></br> <a
						href="${path}/employer/viewApplicants.htm?jobID=${list.jobId}"
						role="button" aria-pressed="true">View Applicants</a></td>
				</tr>

			</c:forEach>

		</table>


	</div>



</body>




</html>