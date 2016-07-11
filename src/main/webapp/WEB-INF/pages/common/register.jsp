<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Registration form</title>
    <link href="<c:url value='/resources/bootstrap/3.3.6/css/bootstrap.min.css'/>"
          rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="form-group">
        <form:form action="/PizzaService/register" method="post" modelAttribute="customer">
            <fieldset class="form-group">
                <form:label path="firstName">First name</form:label>
                <form:input path="firstName" type="text" class="form-control"
                            required="required"/>
                <form:errors path="firstName" cssClass="text-warning" />
            </fieldset>
            <fieldset class="form-group">
                <form:label path="lastName">Last name</form:label>
                <form:input path="lastName" type="text" class="form-control"
                            required="required"/>
                <form:errors path="lastName" cssClass="text-warning" />
            </fieldset>
            <fieldset class="form-group">
                <form:label path="login">Username</form:label>
                <form:input path="login" type="text" class="form-control"
                            required="required"/>
                <c:if test="${error != null}">
                    <div class="alert alert-danger">
                        <p>${error}</p>
                    </div>
                </c:if>
                <form:errors path="login" cssClass="text-warning" />
            </fieldset>
            <fieldset class="form-group">
                <form:label path="password">Password</form:label>
                <form:input path="password" type="password" class="form-control"
                            required="required"/>
                <form:errors path="password" cssClass="text-warning" />
            </fieldset>
            <fieldset class="form-group">
                <form:label path="email">Email</form:label>
                <form:input path="email" type="email" class="form-control"/>
                <form:errors path="email" cssClass="text-warning" />
            </fieldset>
            <fieldset class="form-group">
                <form:label path="phoneNumber">Phone number</form:label>
                <form:input path="phoneNumber" type="tel" class="form-control"/>
                <form:errors path="phoneNumber" cssClass="text-warning" />
            </fieldset>
            <button type="submit" class="btn btn-success">Add</button>
        </form:form>
    </div>
</div>
</body>
<script src="<c:url value='/resources/jquery/1.9.1/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/bootstrap/3.3.6/js/bootstrap.min.js'/>"></script>
</html>
