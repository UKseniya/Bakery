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

    <section>
        <p><fmt:message key="ordering.date.instructions"/></p>
        <form method="POST" action="controller">
            <input type="hidden" name="command" value="select_date">
            <strong><em>${dateNullMessage}</em></strong>
            <strong><em>${dateFullMessage}</em></strong>
            <strong><em>${dateErrorMessage}</em></strong>
            <p><fmt:message key="order.date"/>:
                <input type="date" name="date" value=""
                       oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                       oninput="this.setCustomValidity('')"></p>
            <input type="submit" name="selectDateButton" value="<fmt:message key="button.ok"/> ">
        </form>
    </section>
    </body>
    <br/>
    <br/>
    <br/>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>
