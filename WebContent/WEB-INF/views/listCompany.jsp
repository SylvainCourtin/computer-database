<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>All companies</title>
</head>
<body>
<c:if test="${empty companies}">
	<p>
		<i>No companies :(</i>
	</p>
</c:if>
<c:if test="${not empty companies}">
<table>
	<c:forEach var="company" items="${companies}">
	<tr>
		<td><c:out value="${company.companyBasicView.id}"></c:out></td>
		<td><c:out value="${company.companyBasicView.name}"></c:out></td>
	</tr>
	</c:forEach>
</table>
<div>
	<form action="Controleur" method="post" id="action">
	<input id="selectPage" type="number" min=1 max="${numberOfPages}" value="${page}" name="page" placeholder="${page}"/>
		/<c:out value="${numberOfPages}"></c:out>
	</form>
		
</div>
</c:if>

<script type="text/javascript">
	
	//L'utilisateur fait entrer
	var input = document.getElementById("selectPage");
	input.addEventListener("keyup", function(event) {
	    event.preventDefault();
	    if (event.keyCode === 13) {
	    	document.getElementById("action").submit();
	    }
	});
</script>
</body>
</html>