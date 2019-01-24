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
            <a href="controller?command=select_products"><fmt:message key="make.order"/> </a> <br>
            <a href="controller?command=reviewOrders"><fmt:message key="review.orders"/> </a> <br>
            <a href="controller?command=cart"><fmt:message key="cart"/> </a> <br>
            <a href="controller?command=logout"><fmt:message key="logout"/></a>
        </c:if>
            <%----%>
            <%--<jsp:include page="../includes/column_left.jsp"/>--%>
    </div>

    <section>
        <h3><fmt:message key="ordering.instruction"/></h3><br>
        <h3><fmt:message key="order.review.instruction"/></h3><br>
        <h3><fmt:message key="order.confirm.instruction"/></h3><br>
    </section>
    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>
