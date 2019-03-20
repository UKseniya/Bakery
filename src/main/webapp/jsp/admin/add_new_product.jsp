<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<script src="javascript/show_hidden.js" async type="text/javascript"></script>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/authorization.jsp"/>
    <jsp:include page="../includes/admin_menu.jsp"/>

    <body>
    <section>

        <form name="update" method="POST" action="controller">
            <input type="hidden" name="command" value="add_new_product">
            <label><fmt:message key="product.code"/>:</label> <br/>
            <input type="text" name="productCode" placeholder="EXP01" oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required><br/>
            <label><fmt:message key="product.name.russian"/>:</label> <br/>
            <input type="text" name="russianName" value="" oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required><br/>
            <label><fmt:message key="product.name.english"/>:</label> <br/>
            <input type="text" name="englishName" value="" oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required><br/>
            <label><fmt:message key="product.description.russian"/>: </label><br/>
            <textarea name="russianDescription" cols="50" rows="4" oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                      oninput="this.setCustomValidity('')"  required></textarea><br/>
            <label><fmt:message key="product.description.english"/>: </label><br/>
            <textarea name="englishDescription" cols="50" rows="4" oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                      oninput="this.setCustomValidity('')" required></textarea><br/>
            <label><fmt:message key="price"/>:</label> <br/>
            <input type="text" name="price" placeholder="1000.0" oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')"  required><br/>
            <input type="submit" name="addButton" value="<fmt:message key="button.add"/> "> </form>

        <br/> <br/>

    </section>

    <br/><br/>
    <br/><br/><br/>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>


