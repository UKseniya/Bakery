<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<script src="jsp/javascript/show_hidden.js" async type="text/javascript"></script>
<%--<fmt:setLocale value="${cookie['lang'].value}"/>--%>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/welcome.jsp"/>
    <jsp:include page="../includes/user_menu.jsp"/>

    <body>
    <section class="orders">

    <c:choose>
        <c:when test="${empty pendingOrders}">
                <p><fmt:message key="order.empty"/></p>

        </c:when>
        <c:otherwise>
            <p><fmt:message key="order.list.current"/></p>
            <p><fmt:message key="order.number"/><span class="tab1"><fmt:message key="order.date.selected"/></span><span class="tab2"> <fmt:message key="total"/></span></p>
            <c:forEach var="order" items="${pendingOrders}">
                <p><a href="javascript:unhide('${order.orderNumber}')";/> ${order.orderNumber} </a><span class="tab1">${order.requestedDate}</span><span class="tab2">${order.orderTotalCurrencyFormat}</span></p>
                <div id="${order.orderNumber}" class="hidden">
                    <i>&nbsp;&nbsp;&nbsp;&nbsp; <fmt:message key="name"/></i>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;<i><fmt:message key="quantity"/></i>
                    <c:forEach var="item" items="${order.items}">
                        <br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;${item.product.name}&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;${item.quantity}
                        </c:forEach>
                </div>
            </c:forEach>

        <br/>
        <p><a href="javascript:unhide('completedOrders')";/> <fmt:message key="order.list.all"/> </a></p>
            <div id="completedOrders" class="hidden">
            <c:choose>
        <c:when test="${empty closedOrders}">
        <p><fmt:message key="order.completed.empty"/></p>

        </c:when>
        <c:otherwise>
        <p><fmt:message key="order.list.completed"/></p>

            <p><fmt:message key="order.number"/><span class="tab1"><fmt:message key="order.date.selected"/></span><span class="tab2"> <fmt:message key="total"/></span></p>
            <c:forEach var="order" items="${completedOrders}">
                <p><a href="javascript:unhide('details')";/> ${order.orderNumber} </a><span class="tab1">${order.requestedDate}</span><span class="tab2">${order.orderTotalCurrencyFormat}</span></p>
                <div id="details" class="hidden">
                    <i>&nbsp;&nbsp;&nbsp;&nbsp; <fmt:message key="name"/>&emsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="quantity"/></i>
                    <c:forEach var="item" items="${order.items}">
                        <br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;${item.product.name}&emsp;&nbsp;&nbsp;&nbsp;&nbsp;${item.quantity}
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
        </c:otherwise>
        </c:choose>
            <%--<table id="pending_orders">--%>
                <%--<tr>--%>
                    <%--<th><fmt:message key="order.number"/> </th>--%>
                    <%--<th><fmt:message key="order.date.selected"/> </th>--%>
                    <%--<th><fmt:message key="total"/> </th>--%>
                <%--</tr>--%>
                <%--<c:forEach var="order" items="${orders}">--%>
                        <%--<tr>--%>
                            <%--<td>--%>
                                <%--<a href="javascript:unhide('details')";/><p> ${order.orderNumber} </p></a>--%>
                                <%--<div id="details" class="hidden">--%>
                                    <%--<fmt:message key="name"/><tab><fmt:message key="quantity"/>--%>
                                    <%--<c:forEach var="item" items="${order.items}">--%>
                                       <%--<br/> ${item.product.name}<tab>${item.quantity}--%>
                                    <%--</c:forEach>--%>
                                <%--</div>--%>
                            <%--</td>--%>
                            <%--<td>${order.requestedDate}</td>--%>
                            <%--<td>${order.orderTotalCurrencyFormat}</td>--%>
                        <%--</tr>--%>
                    <%--<div div id="details" class="hidden">--%>
                        <%--<tr><th> <fmt:message key="name"/> </th>--%>
                                <%--&lt;%&ndash;<th><fmt:message key="price" /></th>&ndash;%&gt;--%>
                            <%--<th><fmt:message key="quantity"/> </th>--%>
                        <%--</tr>--%>
                        <%--<c:forEach var="item" items="${order.items}">--%>

                                <%--<tr>--%>
                                    <%--<td >${item.product.name}</td>--%>
                                    <%--&lt;%&ndash;<td>${item.product.price}</td>&ndash;%&gt;--%>
                                    <%--<td>${item.quantity}</td>--%>
                                <%--</tr>--%>

                        <%--</c:forEach>--%>
                    <%--</div>--%>
                <%--</c:forEach>--%>
            <%--</table>--%>

        </c:otherwise>
    </c:choose>

        <%--<p>--%>
            <%--<a href="controller?command=show_all_orders"/><fmt:message key="order.list.all"/>--%>
        <%--</p>--%>
    </section>

    <br>

    <br/>


    <br/><br/><br/>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>

