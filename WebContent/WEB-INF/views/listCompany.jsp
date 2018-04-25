<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>All companies</title>
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
        <div class="container ">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
		</div>
</header>
<c:if test="${empty companies}">
	<p>
		<i>No companies :(</i>
	</p>
</c:if>
<c:if test="${not empty companies}">
<section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${numberOfCompanies} companies found"></c:out>
            </h1>
       </div>
			<div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
            <thead>
                <th>
                    Company ID
                </th>
                <th>
                   	Company Name
                </th>
           </thead>
				<c:forEach var="company" items="${companies}">
				<tr>
					<td><c:out value="${company.companyBasicView.id}"></c:out></td>
					<td><c:out value="${company.companyBasicView.name}"></c:out></td>
				</tr>
				</c:forEach>
				
			</table>
			</div>
</section>


<footer class="navbar-fixed-bottom">
     <div class="container text-center">
           <ul class="pagination">
			<form action="companies" method="get" id="action">
				<input id="selectPage" type="number" min=1 max="${numberOfPages}" value="${page}" name="page" placeholder="${page}"/>/<c:out value="${numberOfPages}" ></c:out>
			</form>
			</ul>
	</div>
</footer>

</c:if>

<script type="text/javascript">

	document.getElementById("selectPage").autofocus = true;
	
	//L'utilisateur fait entrer
	var input = document.getElementById("selectPage");
	input.addEventListener("keyup", function(event) {
	    event.preventDefault();
	    if (event.keyCode === 13) {
	    	document.getElementById("action").submit();
	    }
	});
</script>
<script  src="static/js/jquery.min.js"></script>
<script  src="static/js/bootstrap.min.js"></script>
<script  src="static/js/dashboard.js"></script>
</body>
</html>