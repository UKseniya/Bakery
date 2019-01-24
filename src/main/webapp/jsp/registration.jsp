<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${cookie['lang'].value}" />
<fmt:bundle basename="resources">
    <jsp:include page="includes/header.jsp"/>
    <jsp:include page="includes/column_right_home.jsp"/>

    <div id="main">
        <form name="registrationForm" method="POST" action="controller">
            <input type="hidden" name="command" value="registration_form" />

            <fmt:message key="first.name"/>:<br/>
            <input type="text" name="firstName" value=""/>
            <br/><fmt:message key="last.name"/>:<br/>
            <input type="text" name="lastName" value=""/>
            <br/><fmt:message key="email"/>:<br/>
            <input type="email" name="email" value=""/>
            <br/><fmt:message key="login"/>:<br/>
            <input type="text" name="login" value=""/>
            <br/><fmt:message key="password"/>:<br/>
            <input type="password" name="password" value=""/>
            <br/>
            ${registrationErrorMessage}
            <br/>
            <input type="submit" value="<fmt:message key="registration.button"/>"/>
        </form>
    </div>
    <jsp:include page="includes/footer.jsp"/>
</fmt:bundle>

