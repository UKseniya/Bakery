<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/welcome.jsp"/>

    <body>
    <div id="left">
        <c:if test="${!empty user}">
            <a href="controller?command=makeOrder"><fmt:message key="make.order"/> </a> <br>
            <a href="controller?command=reviewOrders"><fmt:message key="review.orders"/> </a> <br>
            <a href="controller?command=cart"><fmt:message key="cart"/> </a> <br>
            <a href="controller?command=logout"><fmt:message key="logout"/></a>
        </c:if>
    </div>
<section>
    <table id="products">
        <tr>
        <th><fmt:message key="name"/> </th>
        <th><fmt:message key="code"/> </th>
        <th><fmt:message key="unit"/> </th>
        <th><fmt:message key="price"/> </th>
        <th>&nbsp;</th>
        </tr>
        <c:forEach var="product" items="${availableProducts}">
            <tr>
        <td>${product.name}</td>
        <td>${product.code}</td>
        <td>${product.unit}</td>
        <td>${product.priceCurrencyFormat}</td>
        <td>
            <form name="productSelection" method="POST" action="controller">
            <input type="hidden" name="command" value="addToCart">
            <input type="submit" value="<fmt:message key="button.add"/> ">
        </form>
        </td>
            </tr>
</c:forEach>

    </table>
</section>
    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>
