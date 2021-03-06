<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tagdate" uri="tagdate" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="computer.title" /></title>
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
                <c:out value="${numberOfComputer} "></c:out><spring:message code="computer.found" />
            </h1>
            
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="computer" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search" value="${search}" class="form-control" placeholder="<spring:message code="computer.search" />" />
                        <input type="submit" id="searchsubmit" value="<spring:message code="computer.filter" />"
                        class="btn btn-primary" />
                    </form>
                    <c:if test="${not empty result}">
	               	<div class="form-group">
	               	<c:choose>
	               		<c:when test="${fn:contains(result, 'Fail')}">
	               			<div class="alert alert-danger">
	               				<strong><c:out value="${result}"></c:out></strong>
	               			</div>
	               		</c:when>
	               		<c:otherwise>
	               			<div class="alert alert-success">
	               				<strong><c:out value="${result}"></c:out></strong>
	               			</div>
	               		</c:otherwise>
	               	</c:choose>
	               		
	               	</div>
	               </c:if>
                </div>
                	<div class="pull-right">
                    	<a class="btn btn-success" id="add" href="computer/add"><spring:message code="button.add.computer" /></a> 
                    	<a class="btn btn-default" id="edit" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="button.edit" /></a>
                	</div>
            	</div>
       	 	</div>

	        <form id="deleteForm" action="computer" method="GET">
	            <input type="hidden" name="act" value="delete">
	            <input type="hidden" name="selection" value="">
	        </form>
       		<c:if test="${not empty computers}">
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
	                <th><spring:message code="computer.name" /></th>
	                <th><spring:message code="computer.introduced" /></th>
	                <th><spring:message code="computer.discontinued" /></th>
	                <th><spring:message code="company.name" /></th>
	            </tr>
	            </thead>
	            <tbody id="results">
					<c:forEach var="computer" items="${computers}">
					<tr>
						<td class="editMode">
						<form action="computer" method="POST" id="action_computer">
							<input type="hidden" name="act" id="act_computer" value="">
	                        <input type="checkbox" name="cb" class="cb" value="${computer.computerBasicView.id}">
	                        <input type="hidden" id="actIdComputer" name="computer" value="">
	                    </form>
	                    </td>
						<td><a href="computer/edit?id=${computer.computerBasicView.id}"><c:out value="${computer.computerBasicView.name}"></c:out></a></td>
						<td><tagdate:display localDate="${computer.computerBasicView.introduced}"></tagdate:display></td>
						<td><tagdate:display localDate="${computer.computerBasicView.discontinued}"></tagdate:display></td>
						<td><c:out value="${computer.companyBasicView.name}"></c:out></td>
					</tr>
					</c:forEach>
				</tbody>
				</table>
				</div>
			</c:if>
			<c:if test="${empty computers}">
			<div class="container" style="margin-top: 10px;">
				<i><spring:message code="computer.notFound" /> <c:out value="${search}"></c:out></i>
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
           <form action="computer" method="get" id="goToListComputers">
				<c:if test="${not empty computers}">
					<input id="selectPage" type="number" min=1 max="${numberOfPages}" value="${page}" name="page" placeholder="${page}"/>/<c:out value="${numberOfPages}" ></c:out>
				</c:if>
				<input type="hidden" name="search" value="${search}"/>
			</form>
       	</div>
	</div>
</footer>

<form action="computer" method="GET" id="action">
	<input type="hidden" name="act" id="act" value="">
</form>

<script type="text/javascript">

	function previous(numPage)
	{
		var page=document.getElementById("selectPage");
		page.value=--numPage;
		document.getElementById("goToListComputers").submit()
		
	}
	function next(numPage)
	{
		var page=document.getElementById("selectPage");
		page.value=++numPage;
		document.getElementById("goToListComputers").submit()
	}
	
	function active(name_action)
	{
        document.getElementById("act").value = name_action;
        document.getElementById("action").submit();
	}
	function goTo(name_action,id)
	{
		document.getElementById("actIdComputer").value=id;
		document.getElementById("act_computer").value = name_action;
		document.getElementById("action_computer").submit();
	}
	var strings = new Array();
	strings['computer.confirmation.delete'] = "<spring:message code='computer.confirmation.delete' javaScriptEscape='true'/>";
</script>
<script  src="static/js/jquery.min.js"></script>
<script  src="static/js/bootstrap.min.js"></script>
<script  src="static/js/dashboard.js">
</script>
</body>
</html>