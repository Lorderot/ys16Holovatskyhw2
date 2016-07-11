<%@ include file="header.jspf"%>
</head>
<%@ include file="navigation.jspf"%>

<div class="container">
    <form:form method="post" commandName="pizza">
        <form:hidden path="id"/>
        <fieldset class="form-group">
            <form:label path="name">Name</form:label>
            <form:input path="name" type="text" class="form-control"
                        required="required"/>
            <form:errors path="name" cssClass="text-warning" />
        </fieldset>
        <fieldset class="form-group">
            <form:label path="price">Price</form:label>
            <form:input path="price" type="number" step="0.1" class="form-control"
                        required="required"/>
            <form:errors path="price" cssClass="text-warning"/>
        </fieldset>
        <fieldset class="form-group">
            <form:label path="size">Size</form:label>
            <form:select path="size" class="form-control"
                        required="required">
                <form:option value="S" />
                <form:option value="L" />
                <form:option value="XL" />
                <form:option value="XXL" />
            </form:select>
            <form:errors path="size" cssClass="text-warning"/>
        </fieldset>
        <fieldset class="form-group">
            <form:label path="type">Type</form:label>
            <form:select path="type" class="form-control"
                        required="required">
                <form:option value="MEAT"/>
                <form:option value="CHEESE"/>
                <form:option value="VEGETABLE"/>
                <form:option value="SEA"/>
                <form:option value="TOMATO"/>
                <form:option value="EXOTIC"/>
            </form:select>
            <form:errors path="type" cssClass="text-warning"/>
        </fieldset
        <fieldset class="form-group">
            <form:label path="available">Available</form:label>
            <form:checkbox path="available" class="form-control"/>
            <form:errors path="name" cssClass="text-warning" />
        </fieldset>
        <fieldset class="form-group">
            <form:label path="description">Description</form:label>
            <form:input path="description" type="text" class="form-control"/>
            <form:errors path="description" cssClass="text-warning" />
        </fieldset>
        <button type="submit" class="btn btn-success">Submit</button>
    </form:form>
</div>

<%@ include file="footer.jspf"%>