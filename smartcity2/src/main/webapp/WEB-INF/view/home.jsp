<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<title>Sport Services</title>
</head>

<body>
	<h2>Home Page</h2>
	<hr>

	<p>Welcome to the home page of the sport services web app</p>

	<hr>

	<p>
		User:
		<security:authentication property="principal.username" />
		<br>
		<br> Role/s:
		<security:authentication property="principal.authorities" />
	</p>

	<security:authorize access="hasRole('COACH')">
		<!-- Link visible only to coaches -->
		<p>
			<a href="${ pageContext.request.contextPath }/coaches">Coach</a>
		</p>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<!-- Link visible to admin only -->
		<p>
			<a href="${ pageContext.request.contextPath }/admins">Admin</a>
		</p>
	</security:authorize>
	<hr>

	<form:form action="${ pageContext.request.contextPath }/logout"
		method="POST">
		<input type="submit" value="Logout" />
	</form:form>
</body>
</html>