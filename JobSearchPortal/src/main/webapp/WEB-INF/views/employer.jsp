<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Admin Profile Page</title>
</head>
<%@ include file="employer-header.jsp"%>
<body>
	<c:set var="path" value=" ${pageContext.request.contextPath}" />
	<h1 style="color: black;">Hello,  ${userSession.firstName}</h1>

</body>
</html>
</body>
</html>
