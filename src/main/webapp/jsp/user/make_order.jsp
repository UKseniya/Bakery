<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/authorization.jsp"/>
    <jsp:include page="../includes/user_menu.jsp"/>

    <body>

    <section class="center">
        <form name="cart" method="POST" action="controller">
            <input type="hidden" name="command" value="review_cart">
            <input type="submit" value="<fmt:message key="button.cart.go"/> ">
        </form>
        <%--<br/>--%>
        <table class="common">
            <tr>
                <th>&nbsp;</th>
                <th><fmt:message key="name"/></th>
                <th><fmt:message key="price"/></th>
                <th>&nbsp;</th>
            </tr>
            <c:forEach var="product" items="${availableProducts}">
                <form name="productList" method="POST" action="controller">
                    <input type="hidden" name="command" value="add_to_cart">
                    <tr>
                        <td><img src="pictures/${product.formattedCode}.jpg" width="100" height="100">
                        </td>
                        <td>${product.name}</td>
                        <td>${product.priceCurrencyFormat}</td>
                        <td>
                            <input type="hidden" name="productCode" value="${product.code}">
                            <input type="submit" value="<fmt:message key="button.cart.add"/>">
                            <%--TODO: add some notification that product has been added, if possible--%>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>
        <br/>
        <form name="cart" method="POST" action="controller">
            <input type="hidden" name="command" value="review_cart">
            <input type="submit" value="<fmt:message key="button.cart.go"/> ">
        </form>
    </section>
    <br/><br/><br/>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>
