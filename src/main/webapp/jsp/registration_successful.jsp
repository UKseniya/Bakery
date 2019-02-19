<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<fmt:bundle basename="resources">
    <jsp:include page="includes/header.jsp"/>
    <jsp:include page="includes/column_right_home.jsp"/>
    <body>
    <ul class="login-option">
        <li><a href="controller?command=no_command"><fmt:message key="sign-in"/></a></li>
    </ul>
    <section>
        <fmt:message key="registered"></fmt:message>
    </section>
    </body>
    <jsp:include page="includes/footer.jsp"/>
</fmt:bundle>

