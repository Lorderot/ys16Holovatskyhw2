<%@include file="../common/header.jspf"%>
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
            <th>Finished</th>
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
                    <fmt:formatDate  pattern="dd/MM/yyyy hh:mm"
                                    value="${order.creationDate}"/>
                </td>
                <td>
                    <fmt:formatDate pattern="dd/MM/yyyy hh:mm"
                                    value="${order.finishDate}"/>
                </td>
                <td>${order.totalPrice}</td>
                <td>
                    <a type="button" class="btn btn-primary"
                       href="<c:url value='/user/orderList?orderId=${order.id}'/>">Show order list</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="../common/footer.jspf"%>