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
        <a href="controller?command=show_tasks"/><fmt:message key="back"/></a>
        <br/>
        <c:choose>
        <c:when test="${empty allOrders}">
            <p><fmt:message key="tasks.empty"/></p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="order.number"/><span class="tab1"><fmt:message key="order.customer"/></span><span class="tab2"> <fmt:message key="order.date.selected"/></span><span class="tab3"/><fmt:message key="status"/> </span> </p>
            <c:forEach var="order" items="${allOrders}">
                <p><a href="javascript:unhide('${order.orderNumber}')";/> ${order.orderNumber} </a><span class="tab1">${order.user.firstName}&nbsp;${order.user.lastName}</span><span class="tab2">${order.requestedDate}</span><span class="tab3"/>${order.status}</span> </p>
                <div id="${order.orderNumber}" class="hidden">
                    <i>&nbsp;&nbsp;&nbsp;&nbsp; <fmt:message key="name"/></i>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;<i><fmt:message key="quantity"/></i>
                    <c:forEach var="item" items="${order.items}">
                        <br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;${item.product.name}&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;${item.quantity}
                    </c:forEach>
                </div>
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

