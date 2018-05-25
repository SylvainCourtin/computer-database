<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tagdate" uri="tagdate" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="../static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="../dashboard"> <spring:message code="page.title" /> </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                       <c:out value="${computer.computerBasicView.id}"></c:out>
                    </div>
                    <h1>Edit Computer</h1>

                    <form:form method="POST" modelAttribute="computer">
                        <input type="hidden" value="${computer.computerBasicView.id}" name="id" id="id"/>
                        <fieldset>
                            <div class="form-group">
                                <form:label path="computerBasicView.name" for="computerName">Computer name*</form:label>
                                <form:input path="computerBasicView.name" type="text" maxlength="30" required="required" class="form-control" id="computerName" name="computerName" value="${computer.computerBasicView.name}" placeholder="Computer name"/>
                            </div>
                            <div class="form-group">
                                <form:label path="computerBasicView.introduced" for="introduced">Introduced date : <tagdate:display localDate="${computer.computerBasicView.introduced}"></tagdate:display></form:label>
                                <form:input path="computerBasicView.introduced" type="date" class="form-control" id="introduced" name="introduced" value="${computer.computerBasicView.introduced}" onchange="compare();" placeholder="Introduced date"/>
                            </div>
                            <div class="form-group">
                                <form:label path="computerBasicView.discontinued" for="discontinued">Discontinued date : <tagdate:display localDate="${computer.computerBasicView.discontinued}"></tagdate:display></form:label>
                                <form:input path="computerBasicView.discontinued" type="date" class="form-control" id="discontinued" name="discontinued" value="${computer.computerBasicView.discontinued}" onchange="compare();" placeholder="Discontinued date"/>
                            </div>
                            <div class="form-group">
                               <form:label path="companyBasicView.id" for="companyId">Company</form:label>
                                <form:select class="form-control" name="companyId" id="companyId" path="companyBasicView.id" >
                                    <form:option value="0">--</form:option>
                                    <c:forEach var="company" items="${companies}">
                                    	<c:if test="${company.companyBasicView.id == computer.companyBasicView.id }">
                                    		<form:option selected="true" value="${company.companyBasicView.id}">*<c:out value="${company.companyBasicView.name}"></c:out></form:option>
                                    	</c:if>
                                    	<!-- Else -->
                                    	<c:if test="${company.companyBasicView.id != computer.companyBasicView.id }">
                                    		<form:option value="${company.companyBasicView.id}"><c:out value="${company.companyBasicView.name}"></c:out></form:option>
                                    	</c:if>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <c:if test="${not empty result}">
                            	<div class="form-group">
                            		<div class="alert alert-warning">
                            			<strong><c:out value="${result}"></c:out></strong>
                            		</div>
                            	</div>
                            </c:if> 
                            <div style="display:none;" id="wrongOrderDate" class="form-group">
                           		<div class="alert alert-warning">
                           			<strong>The date introduced must be before discontinued</strong>
                           		</div>
                           	</div>            
                        </fieldset>
                        <div class="actions pull-right">
                           <button id="act" type="submit" class="btn btn-primary">Edit</button>
                            or
                            <a href="../computer" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
<script src="../static/js/jquery.min.js"></script>
<script src="../static/js/bootstrap.min.js"></script>
<script src="../static/js/checkDate.js"></script>
</html>