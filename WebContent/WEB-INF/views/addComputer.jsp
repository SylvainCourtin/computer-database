<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <a class="navbar-brand" href="dashboard"> <spring:message code="page.title" /> </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="computer.add.title" /></h1>
                    <form:form method="POST" modelAttribute="computer">
                        <fieldset>
                            <div class="form-group">
                                <form:label path="computerBasicView.name" for="computerName"><spring:message code="computer.name" />*</form:label>
                                <spring:message code="computer.name" var="placeHolder_nameComputer"/>
                                <form:input path="computerBasicView.name" type="text" required="required" class="form-control" id="computerName" name="computerName" onchange="compare();" placeholder="${placeHolder_nameComputer}"/>
                                <div style="display:none;" id="noName" class="form-group">
	                           		<div class="alert alert-warning">
	                           			<strong><spring:message code="warning.name" /></strong>
	                           		</div>
                           		</div> 
                            </div>
                            <div class="form-group">
                                <form:label path="computerBasicView.introduced" for="introduced"><spring:message code="computer.introduced" /></form:label>
                                <form:input path="computerBasicView.introduced" type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" onchange="compare();"/>
                            </div>
                            <div class="form-group">
                                <form:label path="computerBasicView.discontinued" for="discontinued"><spring:message code="computer.discontinued" /></form:label>
                                <form:input path="computerBasicView.discontinued" type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" onchange="compare();"/>
                            </div>
                            <div class="form-group">
                                <form:label path="companyBasicView.id" for="companyId"><spring:message code="company" /></form:label>
                                <form:select class="form-control" name="companyId" id="companyId" path="companyBasicView.id" >
                                    <form:option value="0">--</form:option>
                                    <c:forEach var="company" items="${companies}">
                                    	<form:option value="${company.companyBasicView.id}"><c:out value="${company.companyBasicView.name}"></c:out></form:option>
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
                           			<strong><spring:message code="warning.date" />!</strong>
                           		</div>
                           	</div>               
                        </fieldset>
                        <div class="actions pull-right">
                            <button id="act" type="submit" class="btn btn-primary"><spring:message code="button.add" /></button>
                            <spring:message code="or" />
                            <a href="../computer" class="btn btn-default"><spring:message code="button.cancel" /></a>
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