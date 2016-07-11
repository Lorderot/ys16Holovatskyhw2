<%@include file="header.jspf"%>
<title>Pizza Repository</title>
</head>

<body>

<%@include file="navigation.jspf"%>

<div class="container">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>First name</th>
            <th>Last name</th>
            <th>Phone number</th>
            <th>address</th>
            <th>email</th>
            <th>Created</th>
            <th>Total price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.customer.firstName}</td>
                <td>${order.customer.lastName}</td>
                <td>${order.customer.phoneNumber}</td>
                <td>${order.customer.address}</td>
                <td>${order.customer.email}</td>
                <td>
                    <fmt:formatDate pattern="dd/MM/yyyy"
                        value="${order.creationDate}"/>
                </td>
                <td>${order.totalPrice}</td>
                <td>
                    <form name="submitForm" method="GET"
                          action="<c:url value='/admin/orderList?orderId=${order.id}'/>">
                        <button type="submit" class="btn btn-primary">Show order list</button>
                    </form>
                    <form name="submitForm" method="POST"
                          action="<c:url value='/admin/completeOrder?orderId=${order.id}'/>">
                        <button type="submit" class="btn btn-success">Done</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="footer.jspf"%>