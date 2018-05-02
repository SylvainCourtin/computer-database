<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tagdate" uri="tagdate" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
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

                    <form action="computer" method="POST">
                        <input type="hidden" value="${computer.computerBasicView.id}" name="id" id="id"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name*</label>
                                <input type="text" required="required" class="form-control" id="computerName" name="computerName" value="${computer.computerBasicView.name}" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date : <tagdate:display localDate="${computer.computerBasicView.introduced}"></tagdate:display></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" value="${computer.computerBasicView.introduced}" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date : <tagdate:display localDate="${computer.computerBasicView.discontinued}"></tagdate:display></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" value="${computer.computerBasicView.discontinued}" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0">--</option>
                                    <c:forEach var="company" items="${companies}">
                                    	<c:if test="${company.companyBasicView.id == computer.companyBasicView.id }">
                                    		<option selected value="${company.companyBasicView.id}">*<c:out value="${company.companyBasicView.name}"></c:out></option>
                                    	</c:if>
                                    	<!-- Else -->
                                    	<c:if test="${company.companyBasicView.id != computer.companyBasicView.id }">
                                    		<option value="${company.companyBasicView.id}"><c:out value="${company.companyBasicView.name}"></c:out></option>
                                    	</c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <c:if test="${not empty result}">
                            	<div class="form-group">
                            		<div class="alert alert-warning">
                            			<strong><c:out value="${result}"></c:out></strong>
                            		</div>
                            	</div>
                            </c:if>            
                        </fieldset>
                        <div class="actions pull-right">
                           <button type="submit" name="act" value="validEdit" class="btn btn-primary">Edit</button>
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>