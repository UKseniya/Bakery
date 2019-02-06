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
    <jsp:include page="../includes/user_menu.jsp"/>

    <body>
    <%--<div id="left">--%>
        <%--<c:if test="${!empty user}">--%>
            <%--<a href="controller?command=select_products"><fmt:message key="make.order"/> </a> <br>--%>
            <%--<a href="controller?command=review_orders"><fmt:message key="review.orders"/> </a> <br>--%>
            <%--<a href="controller?command=review_cart"><fmt:message key="cart"/> </a> <br>--%>
            <%--<a href="controller?command=logout"><fmt:message key="logout"/></a>--%>
        <%--</c:if>--%>
           <%----%>
    <%--</div>--%>
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
                        <td><fmt:message key="product.name.${item.product.formattedName}"/></td>
                        <td>${item.quantity}</td>
                    </tr>
                </c:forEach>
            </table>

            <br/>
                ${dateErrorMessage}
            <br/>
            <p><fmt:message key="order.date"/>: <input type="date" name="date" value="" oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                                                       oninput="this.setCustomValidity('')" ></p>
            <br/>
            <p>Total: ${cart.totalCurrencyFormat}</p>
            <input type="submit" value="<fmt:message key="order.confirm"/> ">
        </form>
    </section>
    </body>
    <br/>
    <br/>
    <br/>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>
