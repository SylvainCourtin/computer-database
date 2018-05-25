<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<h2 class="navbar-brand"><spring:message code="page.title" /> </h2>
		</div>
	</header>
	<section id="main">
	<div class="container">
        <h1 id="homeTitle"> Action : </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
				<a class="btn btn-default" href="#" onclick='active("companies")'>Show companies</a>
				<a class="btn btn-default" href="#" onclick='active("computers")'>Show computers</a>
				<a class="btn btn-success" href="#" onclick='active("add")'>Add Computer</a> 
            </div>
        </div>
    </div>
		
	</section>
<form action="dashboard" method="POST" id="action">
    <input type="hidden" name="act" id="act" value=""/>
</form>

<footer class="navbar-fixed-bottom"> </footer>
<script type="text/javascript">
function active(name_action)
	{
        document.getElementById("act").value = name_action;
        document.getElementById("action").submit();
	}
</script>
<script  src="static/js/jquery.min.js"></script>
<script  src="static/js/bootstrap.min.js"></script>
<script  src="static/js/dashboard.js"></script>
</body>
</html>