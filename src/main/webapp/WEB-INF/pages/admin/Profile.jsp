<%@include file="../common/header.jspf"%>
<title>Welcome</title>
</head>

<body>

<%@include file="navigation.jspf"%>
<br/>
<br/>
<h3 align="left">First name: ${customer.firstName}</h3>
<h3 align="left">Last name: ${customer.lastName}</h3>
<h3 align="left">email: ${customer.email}</h3>
<h3 align="left">Phone number: ${customer.phoneNumber}</h3>
<h3 align="left">Username: ${customer.login}</h3>
<h3 align="left">Role: ADMIN</h3>
<Form method="GET"
      action="<c:url value='/admin/changePassword'/>">
    <button type="submit" class="btn btn-primary">
        Change password
    </button>
</Form>


<%@include file="../common/footer.jspf"%>