<%@include file="header.jspf"%>
    <title>Pizza Repository</title>
</head>

<body>

<%@include file="navigation.jspf"%>

<div class="container">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Size</th>
            <th>Type</th>
            <th>Available</th>
            <th>Description</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pizzas}" var="pizza">
            <tr>
                <td>${pizza.name}</td>
                <td>${pizza.size}</td>
                <td>${pizza.type}</td>
                <td>${pizza.available}</td>
                <td>${pizza.description}</td>
                <td>${pizza.price}</td>
                <td>
                    <a type="button" class="btn btn-primary"
                       href="<c:url value='/admin/update-pizza?pizzaId=${pizza.id}'/>">Edit</a>
                    <a type="button" class="btn btn-warning"
                       href="<c:url value='/admin/delete-pizza?pizzaId=${pizza.id}'/>">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div>
        <a type="button" class="btn btn-success"
           href="<c:url value='/admin/add-pizza'/>">Add</a>
    </div>
</div>

<%@include file="footer.jspf"%>