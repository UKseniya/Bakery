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

    <section class="center">
        <h3><fmt:message key="ordering.instruction"/></h3><br>
        <h3><fmt:message key="order.review.instruction"/></h3><br>
        <h3><fmt:message key="order.confirm.instruction"/></h3><br>
    </section>
    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>
