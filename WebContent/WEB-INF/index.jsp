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
			<h2 class="navbar-brand">Application - Computer Database</h2>
		</div>
	</header>
	<section id="main">
	<div class="container">
        <h1 id="homeTitle"> Action : </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form action="companies" method="post" class="form-inline">
					<input type="submit" class="btn btn-default" name="goToListCompanies" value="Get list companies"/>
				</form>
				<form action="computer" method="post" class="form-inline">
					<input type="submit" class="btn btn-default" name="goToListComputers" value="List computer"/>
				</form>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" id="addComputer" href="">Add Computer</a> 
                <a class="btn btn-default" id="editComputer" href="">Edit</a>
            </div>
        </div>
    </div>
		
	</section>
<footer class="navbar-fixed-bottom"> </footer>


<script  src="static/js/jquery.min.js"></script>
<script  src="static/js/bootstrap.min.js"></script>
<script  src="static/js/dashboard.js"></script>
</body>
</html>