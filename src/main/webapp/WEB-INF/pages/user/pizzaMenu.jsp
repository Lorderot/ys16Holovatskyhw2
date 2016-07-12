<%@include file="../common/header.jspf"%>
    <title>Pizza Repository</title>
</head>

<body>

<%@include file="navigation.jspf"%>

<div class="container">
    <h1 align="center">Pizza Menu:</h1>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Size</th>
            <th>Type</th>
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
                <td>${pizza.description}</td>
                <td>${pizza.price}</td>
                <td>
                    <Form method="POST"
                          action="<c:url value='/user/list-pizzas'/>">
                        <input type="text" name="pizzaId" value="${pizza.id}"
                               style="display: none">
                        <button type="submit" class="btn btn-primary">
                            Add to order
                        </button>
                    </Form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <br/>
    <h1 align="center">Your Order:</h1>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Size</th>
            <th>Type</th>
            <th>Description</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orderList}" var="pizza">
            <tr>
                <td>${pizza.name}</td>
                <td>${pizza.size}</td>
                <td>${pizza.type}</td>
                <td>${pizza.description}</td>
                <td>${pizza.price}</td>
                <td>
                    <Form method="POST"
                          action="<c:url value='/user/delete-pizza'/>">
                        <input type="text" name="pizzaId" value="${pizza.id}"
                               style="display: none">
                        <button type="submit" class="btn btn-primary">
                            Delete from order
                        </button>
                    </Form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div>
        <Form method="POST"
              action="<c:url value='/user/create-order'/>">
            <input type="text" name="pizzaId" value="${pizza.id}"
                   style="display: none">
            <button type="submit" class="btn btn-success">
                Create order
            </button>
        </Form>
    </div>
    <h2>Total price: ${totalPrice}</h2>
</div>

<%@include file="../common/footer.jspf"%>