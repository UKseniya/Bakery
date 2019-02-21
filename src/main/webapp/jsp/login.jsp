<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<fmt:bundle basename="resources">
    <jsp:include page="/jsp/includes/header.jsp"/>
    <jsp:include page="/jsp/includes/column_right_home.jsp"/>

    <section>
        <form name="loginForm" method="POST" action="controller">
            <input type="hidden" name="command" value="login"/>
            <label><fmt:message key="login"/>:</label><br/>
            <input type="text" name="login" value=""/>
            <br/><label><fmt:message key="password"/>:</label><br/>
            <input type="password" name="password" value=""/>
            <br/>
                ${loginErrorMessage}
            <br/>
            <input type="submit" name="loginButton" value="<fmt:message key="button.login"/>"/>
        </form>
    </section>
    <jsp:include page="/jsp/includes/footer.jsp"/>
</fmt:bundle>

