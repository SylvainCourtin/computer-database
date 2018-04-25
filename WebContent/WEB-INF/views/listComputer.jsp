<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>All Computer</title>
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
<c:if test="${empty computers}">
	<p>
		<i>No Computer :(</i>
	</p>
</c:if>
<c:if test="${not empty computers}">
<section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${numberOfComputer} companies found"></c:out>
            </h1>
            
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                	<div class="pull-right">
                    	<a class="btn btn-success" id="addComputer" href="addComputer.html">Add Computer</a> 
                    	<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                	</div>
            	</div>
       	 	</div>

	        <form id="deleteForm" action="#" method="POST">
	            <input type="hidden" name="selection" value="">
	        </form>
       
			<div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for Computer Name -->

                <th class="editMode" style="width: 60px; height: 22px;">
                    <input type="checkbox" id="selectall" /> 
                    <span style="vertical-align: top;">
                         -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                <i class="fa fa-trash-o fa-lg"></i>
                            </a>
                    </span>
                </th>
                <th>
                    Computer ID
                </th>
                <th>
                   	Computer Name
                </th>
                <th>
                   	Date introduced
                </th>
                <th>
                   	Date discontinued
                </th>
                <th>
                   	Company Name
                </th>
                </thead>
				<c:forEach var="computer" items="${computers}">
				<tr>
					<td><c:out value="${computer.computerBasicView.id}"></c:out></td>
					<td><c:out value="${computer.computerBasicView.name}"></c:out></td>
					<td><c:out value="${computer.computerBasicView.introduced}"></c:out></td>
					<td><c:out value="${computer.computerBasicView.discontinued}"></c:out></td>
					<td><c:out value="${computer.manufacturerCompanyBasicView.name}"></c:out></td>
				</tr>
				</c:forEach>
			</table>
</section>


<footer class="navbar-fixed-bottom">
     <div class="container text-center">
           <ul class="pagination">
			<form action="computer" method="get" id="goToListComputers">
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
	    	document.getElementById("goToListComputers").submit();
	    }
	});
</script>
<script  src="static/js/jquery.min.js"></script>
<script  src="static/js/bootstrap.min.js"></script>
<script  src="static/js/dashboard.js"></script>
</body>
</html>