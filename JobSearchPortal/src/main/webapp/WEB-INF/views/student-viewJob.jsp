<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Post</title>
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
<script>
$(document).ready(function(){
	 $(".alert").hide();

	 if (window.location.href.indexOf("?jobID=") > -1) {
		    $(".alert").show();
		}
	 
	
		  $("#search").on("keyup", function() {
		    var value = $(this).val().toLowerCase();
		    $("#jobTable tr").filter(function() {
		      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		    });
		  });
		
	
	});


</script>


</head>
<%@ include file="student-header.jsp"%>

<body>
	<c:set var="path" value=" ${pageContext.request.contextPath}" />


		<div  style="padding: 20px";>
			<h2>Open Job Listings</h2>

			<div style="color: red">${error}</div>
			<input type="text" id="search" placeholder="Search here.."
				style="border: 1px solid black;"/>
				<br/>
				<br/>
			<table id="jobTable" class="table table-bordered">
				<%-- <input type="hidden" name = "jobID" value = "${j.id}" /> --%>

				<tr>
					<th>Job ID</th>
					<th>Title</th>
					<th>Company Name</th>
					<th>Job Type</th>
					<th>Location</th>
					<th>Industry</th>
					<th>Job For..</th>
					<th>Job-URL</th>
					<th>Description</th>
					<th>Posted On</th>
					<th>Action</th>
				</tr>
				<c:forEach var="list" items="${allJobs}">
					<tr>
						<td>${list.jobId}</td>
						<td>${list.title}</td>
						<td>${list.companyName}</td>
						<td>${list.typeOfJob}</td>
						<td>${list.state},${list.country}</td>
						<td>${list.industry}</td>
						<td>${list.major}</td>

						<c:if test="${empty list.jobUrl}">
							<td>No link available. Please check the company's website</td>
						</c:if>
						<c:if test="${not empty list.jobUrl}">
							<td>${list.jobUrl}</td>
						</c:if>

						<c:if test="${empty list.description}">
							<td>No description provided</td>
						</c:if>
						<c:if test="${not empty list.description}">
							<td>${list.description}</td>
						</c:if>

						<td>${list.postedOn}</td>
						<td><a
							href="${path}/student/applyJob.htm?jobID=${list.jobId}"
						 role="button"
							aria-pressed="true">Apply for this job</a></td>
					</tr>

				</c:forEach>
			</table>



		</div>



</body>
</html>
