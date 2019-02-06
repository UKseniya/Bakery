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
    <%--</div>--%>
    <section class="center">
        <p><fmt:message key="order.confirm.information"/> <br/><br/>
            <fmt:message key="email"/>&nbsp;<a href="mailto:customer.support@test.com">customer.support@test.com</a><br/>
            <fmt:message key="phone"/> +7 701 777 7777. </p>
    </section>

    <br/><br/><br/>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>