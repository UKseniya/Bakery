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
    <jsp:include page="../includes/user_menu.jsp"/>

    <body>
    <section>
<c:choose>
        <c:when test="${empty allOrders}">
            <p><fmt:message key="order.empty" /></p>
        </c:when>
    <c:otherwise>
        <c:choose>

        <c:when test="${empty pendingOrders}">
            <p><fmt:message key="order.pending.empty"/></p>
        </c:when>

        <c:otherwise>
        <p><fmt:message key="order.list.current"/></p>
        <p><fmt:message key="order.number"/><span class="tab1"><fmt:message key="order.date.selected"/></span>
            <span class="tab2"> <fmt:message key="total"/></span></p>
        <c:forEach var="order" items="${pendingOrders}">
            <form action="controller" method="POST">
                <input type="hidden" name="action" value="cancel_order">
            <p><a href="javascript:unhide('${order.orderNumber}')" ;/> ${order.orderNumber} </a>
                <span class="tab1">${order.requestedDate}</span>
                <span class="tab2">${order.orderTotalCurrencyFormat}</span></p>
                <span class="tab3"><input type="hidden" name="orderID" value="${order.id}">
                    <input type="submit" value="<fmt:message key="cancel"/> "></span>
            </form>
            <div id="${order.orderNumber}" class="hidden">
                <i>&nbsp;&nbsp;&nbsp;&nbsp; <fmt:message key="name"/></i>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;
                <i><fmt:message key="quantity"/></i>
                <c:forEach var="item" items="${order.items}">
                    <br/>
                    &nbsp;&nbsp;&nbsp;&nbsp;${item.product.name}&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;${item.quantity}
                </c:forEach>
            </div>
        </c:forEach>
        </c:otherwise>

        </c:choose>

        <br/>
        <p><a href="javascript:unhide('completedOrders')" ;/> <fmt:message key="order.review.completed"/> </a></p>
        <div id="completedOrders" class="hidden">

            <c:choose>

            <c:when test="${empty completedOrders}">
            <p><fmt:message key="order.completed.empty"/></p>
            </c:when>

            <c:otherwise>
            <p><fmt:message key="order.list.completed"/></p>
            <p><fmt:message key="order.number"/><span class="tab1"><fmt:message key="order.date.selected"/></span>
                <span class="tab2"> <fmt:message key="total"/></span></p>
            <c:forEach var="order" items="${completedOrders}">
            <p><a href="javascript:unhide('${order.orderNumber}')" ;/> ${order.orderNumber} </a>
                <span class="tab1">${order.requestedDate}</span>
                <span class="tab2">${order.orderTotalCurrencyFormat}</span></p>
            <div id="${order.orderNumber}" class="hidden">
                <i>&nbsp;&nbsp;&nbsp;&nbsp; <fmt:message key="name"/></i>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;
                <i><fmt:message key="quantity"/></i>
                <c:forEach var="item" items="${order.items}">
                    <br/>
                    &nbsp;&nbsp;&nbsp;&nbsp;${item.product.name}&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;${item.quantity}
                </c:forEach>
            </div>
            </c:forEach>


            </c:otherwise>

            </c:choose>
                </c:otherwise>
                </c:choose>

    </section>

    <br>

    <br/>


    <br/><br/><br/>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>

