<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Post a Job</title>
<style>
form {
	padding: 25px 280px 50px 280px;
}
</style>
</head>
<%@ include file="employer-header.jsp"%>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<script>
		$(document).ready(function() {
			if (window.history.replaceState) {
				window.history.replaceState(null, null, window.location.href);
			}
		});
	</script>

	<div style="padding: 10px;  border: 1px solid black; align: center"; >
		<form:form name="contact-form" method="post"
			action="${contextPath}/employer/postJob" modelAttribute="job">
			<h2>Post Job</h2>

			<div>
				<form:input type="text" class="form-control" path="title"
					placeholder="Title" required="required" style="height : 30px" />
				<form:errors path="title" cssClass="error" />
			</div>
			<br />
			<div>
				<form:input type="text" class="form-control" path="companyName"
					placeholder="Company Name" required="required"
					style="height : 30px" />
				<form:errors path="companyName" cssClass="error" />
			</div>
			<br />
			<div>
				<p>
					Job Type <span class="asterisk">&#42;</span>
				<p>
				<div>
					<form:radiobutton required="required" value="Full Time"
						path="typeOfJob" id="job_type_full" />
					<label for="job_type_full">Full Time</label>
				</div>

				<div>
					<form:radiobutton required="required" value="Co-op"
						path="typeOfJob" id="job_type_coop" />
					<label for="job_type_coop">Co-op</label>
				</div>

				<div>
					<form:radiobutton required="required" value="Contract"
						path="typeOfJob" id="job_type_contract" />
					<label for="job_type_contract">Contract</label>
				</div>
			</div>
			<br />
			<div>
				<div>

					<script type="text/javascript"
						src="${contextPath}/resources/countries.js"></script>
					<select required="required" id="job_type_country" name="country"></select>
					<br> <br /> <label for="job_type_state">State</label><br>
					<select required="required" name="state" id="job_type_state"></select>

				</div>
			</div>

			<script>
				populateCountries("job_type_country", "job_type_state"); // first parameter is id of country drop-down and second parameter is id of state drop-down
			</script>
			<br />
			<div>
				<form:input type="text" id="industry" path="industry"
					placeholder="industry" required="required" style="height : 30px"
					onkeyup="checkPass()" />
				<form:errors path="industry" cssClass="error" />
			</div>
			<br />
			<br />
			<div>
				<script src="${contextPath}/resources/majorsList.js"></script>
				<select required="required" id="majorCat" name="majCategory"></select>
				<br> <br> <label for="majorList">Select the major
					applicable for this job <span>&#42;</span>
				</label><br> <select required="required" name="major" id="majorList"></select>
			</div>
			<script type="text/javascript">
				populateCategories("majorCat", "majorList"); // first parameter is id of country drop-down and second parameter is id of state drop-down
			</script>
			<br />
			<label>GPA<span>&#42;</span>
				<div>
					<form:input type="number" path="minGpa" step="0.1" min="0" max="4"
						class="form-control" placeholder="minGpa" required="required"
						style="height : 30px" onkeyup="checkPass()" />
				</div> <br />
				<div>
					<form:input type="text" path="jobUrl" placeholder="job_url"
						class="form-control" required="required" style="height : 30px"
						onkeyup="checkPass()" />

				</div> <br />
				<div>
					<form:input type="text" path="description" class="form-control"
						placeholder="description" required="required"
						style="height : 30px" onkeyup="checkPass()" />

				</div> <br />
				<div style="margin-left: 0%">
					<button type="submit" style="height: 40px">Post a Job</button>
				</div>
		</form:form>
		<br />
	</div>
</body>


</html>
</body>
</html>