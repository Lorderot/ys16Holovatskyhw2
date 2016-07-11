<%@include file="header.jsp"%>
    <title>Pizza Repository</title>
</head>

<body>

<nav role="navigation" class="navbar navbar-default">

    <div class="navbar-collapse">
        <ul class="nav navbar-nav">
            <li><a href="<c:url value='/admin/welcome'/>">Home</a></li>
            <li><a href="<c:url value='/admin/list-pizzas'/>">Pizza Repository</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="<c:url value='/logout'/>">Logout</a></li>
        </ul>
    </div>

</nav>

<div class="container">
    <table class="table table-striped">
        <caption>Pizza Repository</caption>
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

</body>

<%@include file="footer.jsp"%>