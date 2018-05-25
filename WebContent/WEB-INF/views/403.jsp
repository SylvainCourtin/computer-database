<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
			<a class="navbar-brand" href="dashboard"> <spring:message code="page.title" /> </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				Error 403: Access denied!
				<br/>
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<script  src="../static/js/jquery.min.js"></script>
	<script  src="../static/js/bootstrap.min.js"></script>
	<script  src="../static/js/dashboard.js"></script>	

</body>
</html>