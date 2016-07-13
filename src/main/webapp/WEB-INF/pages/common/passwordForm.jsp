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
    <div class="form-group" id="passwordForm" align="center">
        <Form method="POST"
              action="<c:url value='${url}'/>">
            <h5>Old password:
                <input type="password" name="oldPassword" required="required">
            </h5>
            <h5>New password:
                <input type="password" name="newPassword" required="required"></h5>
            <h5>Confirm password:
                <input type="password" name="confirmPassword" required="required">
            </h5>
            <c:if test="${error != null}">
                <div class="alert alert-danger">
                    <p>${error}.</p>
                </div>
            </c:if>
            <button type="submit" class="btn btn-primary">
                Change password
            </button>
        </Form>
    </div>
</div>
</body>
<script src="<c:url value='/resources/jquery/1.9.1/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/bootstrap/3.3.6/js/bootstrap.min.js'/>"></script>
</html>
