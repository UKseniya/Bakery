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
            <%----%>
            <%--<jsp:include page="../includes/column_left.jsp"/>--%>
    </div>
    <section class="checkout">
        <form name="confirm_order" method="POST" action="controller">
            <input type="hidden" name="command" value="confirm_order"> <br/>
            <p><fmt:message key="customer"/>: ${user.firstName} ${user.lastName}</p>
            <p><fmt:message key="order"/>: </p>

            <table>
                <tr>
                    <th><fmt:message key="products"/></th>
                    <th><fmt:message key="quantity"/></th>
                </tr>
                <c:forEach var="item" items="${cart.items}">
                    <tr>
                        <td>${item.product.name}</td>
                        <td>${item.quantity}</td>
                    </tr>
                </c:forEach>
            </table>

            <br/>
                ${dateErrorMessage}
            <br/>
            <p><fmt:message key="order.date"/>: <input type="date" name="date" value="" required></p>
            <br/>
            <p>Total: ${cart.total}</p>
            <input type="submit" value="<fmt:message key="order.confirm"/> ">
        </form>
    </section>
    </body>
    <br/>
    <br/>
    <br/>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>
