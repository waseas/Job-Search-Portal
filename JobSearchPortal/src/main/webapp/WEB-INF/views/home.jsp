<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
.w3-btn {
	width: 150px;
}
</style>
<%@ include file="header.jsp"%>
<body>
	<div class="w3-container" align="center">
		<p>
			<a href="${path}/login"><button
					class="w3-button w3-black w3-round-large">Login</button></a>
		</p>
		<p>
			<a href="${path}/register"><button
					class="w3-button w3-black w3-round-large">Register</button></a>
		</p>
	</div>
	<c:set var="path" value=" ${pageContext.request.contextPath}" />


</body>

</html>

