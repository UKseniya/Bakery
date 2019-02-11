<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<script src="javascript/show_hidden.js" async type="text/javascript"></script>
<%--<fmt:setLocale value="${cookie['lang'].value}"/>--%>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/welcome.jsp"/>
    <jsp:include page="../includes/admin_menu.jsp"/>

    <body>

    <section>

        <a href="javascript:unhide('date')";/><fmt:message key="date.select"/></a> &nbsp; &nbsp; &nbsp;
        <a href="controller?command=show_all_requests"/><fmt:message key="order.list.all"/></a><br/>
        <div id="date" class="hidden">
            <form name="date" method="GET" action="controller">
                <input type="hidden" name="command" value="show_tasks">
                <input type="date" name="date" value="">
                <input type="submit" name="changeDateButton" value="<fmt:message key="button.ok"/>">
            </form>
        </div>

    <c:choose>

        <c:when test="${empty pendingOrders}">
            <br/><br/>
                <p><fmt:message key="tasks.empty"/></p>

        </c:when>
        <c:otherwise>
            <br/><br/>
            <h1><fmt:message key="tasks.todo"/> <fmt:formatDate value="${now}" type="date"/></h1>
            <c:forEach var="order" items="${pendingOrders}">
                <b><fmt:message key="order.number"/>: &nbsp; ${order.orderNumber}</b>&nbsp;&nbsp;&nbsp;&nbsp;
                <fmt:message key="order.customer"/>: &nbsp; ${order.user.firstName} &nbsp;${order.user.lastName} &nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="phone"/>&nbsp;${user.phoneNumber} <br/>
                &nbsp;&nbsp;&nbsp;&nbsp;<i><fmt:message key="product.name"/></i>&emsp;&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;<i><fmt:message key="quantity"/></i><br/>
                <c:forEach var="item" items="${order.items}">
                    &nbsp;&nbsp;&nbsp;&nbsp; <fmt:message key="product.name.${item.product.formattedName}"/>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;${item.quantity}<br/>

                </c:forEach>
                <br/>

            <form name="pendingOrders" method="GET" action="controller">
                <input type="hidden" name="command" value="show_tasks">
                <input type="hidden" name="orderNumber" value="${order.orderNumber}">
                       &nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="completeButton" value="<fmt:message key="button.complete"/> ">
            </form>
            </c:forEach>
        </c:otherwise>
  </c:choose>

            <c:choose>
        <c:when test="${empty completedOrders}">
        <p>
        </p>

        </c:when>
                <c:otherwise>
                    <h1><fmt:message key="order.completed"/> <fmt:formatDate value="${now}" type="date"/></h1>
                    <c:forEach var="order" items="${completedOrders}">
                        <b><fmt:message key="order.number"/>: &nbsp; ${order.orderNumber}</b>&nbsp;&nbsp;&nbsp;&nbsp;
                        <fmt:message key="order.customer"/>: &nbsp; ${order.user.firstName} &nbsp;${order.user.lastName} &nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="phone"/>&nbsp;${user.phoneNumber} <br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<i><fmt:message key="product.name"/></i>&emsp;&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;<i><fmt:message key="quantity"/></i><br/>
                        <c:forEach var="item" items="${order.items}">
                            &nbsp;&nbsp;&nbsp;&nbsp; <fmt:message key="product.name.${item.product.formattedName}"/>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;${item.quantity}<br/>

                        </c:forEach>
                        <br/>
                        <form name="pendingOrders" method="GET" action="controller">
                            <input type="hidden" name="command" value="show_tasks">
                            <input type="hidden" name="orderNumber" value="${order.orderNumber}">
                            &nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="closeButton" value="<fmt:message key="button.close"/> ">
                        </form>
                    </c:forEach>
                </c:otherwise>
        </c:choose>

    </section>

    <br>

    <br/>


    <br/><br/><br/>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>

