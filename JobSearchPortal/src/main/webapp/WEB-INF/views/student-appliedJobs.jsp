<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Applied Jobs</title>
<script>
$(document).ready(function(){
	 $(".alert").hide();

	 if (window.location.href.indexOf("?jobID=") > -1) {
		    $(".alert").show();
		}
	 
	 if ( window.history.replaceState ) {
	        window.history.replaceState( null, null, window.location.href );
	    }
	
	});
</script>
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
<%@ include file="student-header.jsp"%>

<body>
	<c:set var="path" value=" ${pageContext.request.contextPath}" />

		<div style="padding: 20px";>
			<h2>Applied Job Listings</h2>
			<a href="${path}/student/downloadPdf"
				class="btn btn-primary btn-sm active" role="button"
				aria-pressed="true">Download Applications List</a> <br>
			<br>


			<table id="jobTable" class="table table-bordered">
				<%-- <input type="hidden" name = "jobID" value = "${j.id}" /> --%>

				<tr class="header">
					<th style="width: 5%;">Job ID</th>
					<th style="width: 10%;">Title</th>
					<th style="width: 5%;">Company Name</th>
					<th style="width: 5%;">Job Type</th>
					<th style="width: 10%;">Location</th>
					<th style="width: 10%;">Industry</th>
					<th style="width: 10%;">Job For..</th>
					<th style="width: 20%;">Job-URL</th>
					<th style="width: 20%;">Description</th>
					<th style="width: 5%;">Posted On</th>
					<th style="width: 10%;">Action</th>
				</tr>
				<c:forEach var="list" items="${jobs}">
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
							href="${path}/student/deleteApplication?jobId=${list.jobId}"
							class="btn btn-primary btn-sm active" role="button"
							aria-pressed="true">Withdraw</a></td>
					</tr>

				</c:forEach>
			</table>



		</div>

</body>
</html>
