<%@include file="header.jsp"%>
    <title>Welcome</title>
</head>

<body>

<nav role="navigation" class="navbar navbar-default">

    <div class="navbar-collapse">
        <ul class="nav navbar-nav">
            <c:url var="welcomeUrl" value="/admin/welcome"/>
            <c:url var="pizzasUrl" value="/admin/list-pizzas"/>
            <li><a href="${welcomeUrl}">Home</a></li>
            <li><a href="${pizzasUrl}">Pizza Repository</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:url var="logoutUrl" value="/logout"/>
            <li><a href="${logoutUrl}">Logout</a></li>
        </ul>
    </div>

</nav>

    Welcome ${customer.firstName} ${customer.lastName}!

<%@include file="footer.jsp"%>