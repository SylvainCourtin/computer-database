<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
    <link href="../static/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../static/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="../static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">	
			<div class="alert alert-danger">
				Error 500: An error has occured!
				<br/>
				<c:if test="${not empty result}">
	               	<div class="form-group">
              			<div class="alert alert-danger">
              				<strong><c:out value="${result}"></c:out></strong>
              			</div>
	               	</div>
	            </c:if>
			</div>
		</div>
	</section>

	<script  src="../static/js/jquery.min.js"></script>
	<script  src="../static/js/bootstrap.min.js"></script>
	<script  src="../static/js/dashboard.js"></script>

</body>
</html>