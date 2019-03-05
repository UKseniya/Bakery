<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>

<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/authorization.jsp"/>
    <jsp:include page="../includes/admin_menu.jsp"/>

    <body>
<section>
    <p><fmt:message key="user.number"/> &nbsp; <b><i>${numberOfUsers}</i></b></p> <br/>
    <p><fmt:message key="top.product.current"/></p>
    <c:choose>
        <c:when test="${empty currentMonthTopProducts}">
            <b> <i><fmt:message key="none"/></i></b>
        </c:when>
        <c:otherwise>
            <fmt:message key="product.name"/><span class="tab4"><fmt:message key="quantity"/></span>
            <c:forEach var="item" items="${currentMonthTopProducts}">
                <p><b><i>${item.product.name}<span class="tab4">${item.quantity}</span></i></b></p>
    </c:forEach>
        </c:otherwise>
    </c:choose>
    <br/><br/>
    <p><fmt:message key="top.product.previous"/></p>
    <c:choose>
        <c:when test="${empty previousMonthTopProducts}">
            <b><i><fmt:message key="none"/></i></b>
        </c:when>
        <c:otherwise>
            <i> <fmt:message key="product.name"/><span class="tab4"><fmt:message key="quantity"/></span></i>
            <c:forEach var="item" items="${previousMonthTopProducts}">
                <p><b><i>${item.product.name}<span class="tab4">${item.quantity}</span></i></b></p>
        </c:forEach>
        </c:otherwise>
    </c:choose>
</section>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>
