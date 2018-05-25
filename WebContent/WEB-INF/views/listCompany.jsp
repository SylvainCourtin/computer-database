<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
            <a class="navbar-brand" href="dashboard"> <spring:message code="page.title" /> </a>
		</div>
</header>

<section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${numberOfCompanies} companies found"></c:out>
            </h1>
       </div>
       <c:if test="${not empty companies}">
			<div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
            <thead>
           	<tr>
                <th>
                    Company ID
                </th>
                <th>
                   	Company Name
                </th>
           </tr>
           </thead>
				<c:forEach var="company" items="${companies}">
				<tr>
					<td><c:out value="${company.companyBasicView.id}"></c:out></td>
					<td><c:out value="${company.companyBasicView.name}"></c:out></td>
				</tr>
				</c:forEach>
				
			</table>
			</div>
		</c:if>
</section>


<footer class="navbar-fixed-bottom">
     <div class="container text-center">
           <ul class="pagination">
			<li>
                 <a href="#" aria-label="Previous" onclick='previous("${page}")' >
                    <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              <li>
                <a href="#" aria-label="Next" onclick='next("${page}")'>
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
			
			</ul>
		 <div class="btn-group btn-group-sm pull-right" role="group" >
           <form action="companies" method="get" id="action">
				<c:if test="${not empty companies}">
					<input id="selectPage" type="number" min=1 max="${numberOfPages}" value="${page}" name="page" placeholder="${page}"/>/<c:out value="${numberOfPages}" ></c:out>
				</c:if>
			</form>
       	</div>
	</div>
</footer>



<script type="text/javascript">

	document.getElementById("selectPage").autofocus = true;
	function previous(numPage)
	{
		var page=document.getElementById("selectPage");
		page.value=--numPage;
		document.getElementById("action").submit()
		
	}
	function next(numPage)
	{
		var page=document.getElementById("selectPage");
		page.value=++numPage;
		document.getElementById("action").submit()
	}
</script>
<script  src="static/js/jquery.min.js"></script>
<script  src="static/js/bootstrap.min.js"></script>
<script  src="static/js/dashboard.js"></script>
</body>
</html>