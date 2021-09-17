<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>AddMember</title>
<script>
	/*Jquery for Job Description*/
	$(document).ready(function() {

		$(".alert").hide();

		$('button').click(function() {
			$('.alert').show();
		})

	});
</script>
</head>
<%@ include file="employer-header.jsp"%>
<body>
	<c:set var="path" value=" ${pageContext.request.contextPath}" />
		<div>
			<h2>Applicant Info</h2>
			<div style="color: red">${message}</div>
			<table>
				<tr>
					<td><b>FirstName</b></td>
					<td><b>LastName</b></td>
					<td><b>Email</b>
					<td><b>Resume</b>
					<td><b>CoverLetter</b>
				</tr>
				<%-- <c:forEach var="user" items="${listuser}" --%>


				<c:forEach var="app" items="${listapp}">
					<tr>
						<td>${app.user.firstName}</td>
						<td>${app.user.lastName}</td>
						<td>${app.user.username}</td>
						<td><a
							href="${path}/employer/downloadResume.htm?path=${app.resumePath}&email=${app.user.username}"
							class="btn btn-default" role="button" aria-pressed="true">Download
								Resume</a></td>
						<td><a
							href="${path}/employer/downloadCoverLetter.htm?path=${app.coverLetterPath}&email=${app.user.username}"
							class="btn btn-default" role="button" aria-pressed="true">Download
								CoverLetter</a></td>
					</tr>
				</c:forEach>
				<%-- </c:forEach> --%>

			</table>

			<div  id="">
				<c:if test="${not empty message}">
					<font color="blue"><h4>${ssmessage}</h4></font>
				</c:if>

				<div role="alert">
					<strong>${message}</strong>
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">X</button>
				</div>
			</div>
		</div>

</body>


</html>
</html>