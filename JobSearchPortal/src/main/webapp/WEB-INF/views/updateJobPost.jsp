<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Post a Job</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<script>
$(document).ready(function(){
	$("form").submit(function(e){
if (!$("input[name='typeOfJob']:checked").val()) {
	   alert('Nothing is checked!Kindly choose type of employment');
	   e.preventDefault();
	}
});
});

</script>
</head>
<%@ include file = "employer-header.jsp" %>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">

  </script>
  
<div class="signup-form" style="margin-top: 10%">
    <form:form name="contact-form" method="post" action="${contextPath}/employer/updateJob" modelAttribute="job">
		<h2>Register</h2>
	<input type="hidden" name="id" value="${job.jobId}">
        <div>
			<form:input type="text" class="form-control" path="title" placeholder="Title" required="required" style="height : 30px" value="${job.title}"/>
			<form:errors path="title" cssClass="error"/>
		</div>
			 <div>
			<form:input type="text" class="form-control" path="companyName" placeholder="Company Name" required="required" value="${job.companyName}" style="height : 30px"/>        	
        	<form:errors path="companyName" cssClass="error"/>
        </div>
         <div>
                <p>Job Type <span class="asterisk">&#42;</span><p>
                <div>
                    <input type="radio" value="Full Time"  ${job.typeOfJob == 'Full Time' ? 'checked' : ''} name="typeOfJob"  />
                    <label for="job_type_full">Full Time</label>
                </div>

                <div>
                    <input type="radio" value="Co-op" ${job.typeOfJob == 'Co-op' ? 'checked' : ''} name="typeOfJob" id="job_type_coop"/>
                    <label for="job_type_coop">Co-op</label>
                </div>

                <div>
                    <input type="radio" value="Contract" ${job.typeOfJob == 'Contract' ? 'checked' : ''} name="typeOfJob" id="job_type_contract"/>
                    <label for="job_type_contract">Contract</label>
                </div>
            </div>
        <div >
            <div class="locationDropdown">

                    <script type= "text/javascript" src = "${contextPath}/resources/assets/js/countries.js"></script>
                    <select required="required" id="job_type_country" name ="country"></select> <br>

                    <label for="job_type_state">State</label><br>
                    <select required="required" name ="state" id ="job_type_state"></select>

                </div>
                </div>
                
              <script type="text/javascript">
                populateCountries("job_type_country", "job_type_state"); // first parameter is id of country drop-down and second parameter is id of state drop-down
            </script>
		<div >
            <form:input type="text" class="form-control" id="industry" path="industry" placeholder="industry" value="${job.industry}" required="required" style="height : 30px" onkeyup="checkPass()" />
        	<form:errors path="industry" cssClass="error"/>
        </div>
         <div>
             <script src = "${contextPath}/resources/assets/js/majorsList.js"></script>
              <select required="required" id="majorCat" name ="majCategory"></select> <br><br>
              <label for="majorList">Select the major applicable for this job  <span class="asterisk">&#42;</span></label><br>
               <select required="required" name ="major" id ="majorList"></select>
         </div>
         
         <script type="text/javascript">
                populateCategories("majorCat", "majorList"); // first parameter is id of country drop-down and second parameter is id of state drop-down
         </script>
        <div>
            <form:input type="number" class="form-control" path="minGpa" step ="0.1" min="0" max="4" placeholder="minGpa" required="required" value="${job.minGpa}" style="height : 30px" />
        	
        </div>
        <div >
            <form:input type="text" class="form-control" path="jobUrl" placeholder="job_url" required="required" value="${job.jobUrl}" style="height : 30px" />
        	
        </div>
        <div>
            <form:input type="text" class="form-control" path="description" placeholder="description" required="required" value="${job.description}" style="height : 30px" onkeyup="checkPass()" />
        	<p id="confirmMessage" ></p>
        </div>
              
       
		<div style="margin-left:0%">
            <button type="submit" class="btn btn-success btn-lg btn-block" style="height : 40px">Update a Job</button>
        </div>
    </form:form>
	
</div>
</body>

	


</body>
</html>