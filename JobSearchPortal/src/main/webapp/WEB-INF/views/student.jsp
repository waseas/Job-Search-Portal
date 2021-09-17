<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<html>    
<head>    
<title>Student Profile Page</title>   
<%@ include file = "student-header.jsp" %> 
</head>    
    
<body>
<c:set var="path" 	value=" ${pageContext.request.contextPath}"/>
<h1 style="color: black;">Hello,  ${userSession.firstName}</h1>

    
</body>    
</html> 