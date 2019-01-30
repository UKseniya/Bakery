<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<%--<fmt:setLocale value="${cookie['lang'].value}"/>--%>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/welcome.jsp"/>

    <body>
    <div id="left">
        <c:if test="${!empty user}">
            <a href="controller?command=select_products"><fmt:message key="make.order"/> </a> <br>
            <a href="controller?command=review_orders"><fmt:message key="review.orders"/> </a> <br>
            <a href="controller?command=review_cart"><fmt:message key="cart"/> </a> <br>
            <a href="controller?command=logout"><fmt:message key="logout"/></a>
        </c:if>
    </div>
    <section>
        <table id="productsList">
            <tr>
                <th>&nbsp;</th>
                <th><fmt:message key="name"/></th>
                    <%--<th><fmt:kz.epam.message key="code"/> </th>--%>
                <th><fmt:message key="price"/></th>
                <th>&nbsp;</th>
            </tr>
            <c:forEach var="product" items="${availableProducts}">
                <form name="productList" method="POST" action="controller">
                    <input type="hidden" name="command" value="add_to_cart">
                    <tr>
                        <td><img src="/Bakery/jsp/style/pictures/${product.name}.jpg" width="100" height="100">
                        </td>
                        <td><fmt:message key="product.name.${product.formatedName}"/></td>
                        <td>${product.price}</td>
                        <td>
                            <input type="hidden" name="productCode" value="${product.code}">
                            <input type="submit" value="<fmt:message key="button.cart.add"/>">
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>
        <form name="cart" method="POST" action="controller">
            <input type="hidden" name="command" value="review_cart">
            <input type="submit" value="<fmt:message key="button.cart.go"/> ">
        </form>
    </section>
    <br/><br/><br/>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>
