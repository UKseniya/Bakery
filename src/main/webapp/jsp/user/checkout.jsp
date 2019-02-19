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

    <section class="checkout">
        <p><fmt:message key="ordering.date.instructions"/></p>
        <form name="confirm_order" method="POST" action="controller">
            <input type="hidden" name="command" value="confirm_order"> <br/>
            <p><fmt:message key="order"/>: </p>
            <i><fmt:message key="product.name"/></i>&emsp;&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;
            <i><fmt:message key="quantity"/></i><br/>
            <c:forEach var="item" items="${cart.items}">
                ${item.product.name}&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;${item.quantity}<br/>

            </c:forEach>

            <br/>
            <strong><em>${dateNullMessage}</em></strong>
            <strong><em>${dateErrorMessage}</em></strong>
            <br/>
            <p><fmt:message key="order.date"/>:
                <input type="date" name="date" value=""
                       oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                       oninput="this.setCustomValidity('')"></p>
            <br/>
            <label><fmt:message key="comment"/></label><br/>
            <textarea name="comment" cols="40" rows="3"></textarea>
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
