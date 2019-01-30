<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<%--<fmt:setLocale value="${cookie['lang'].value}" />--%>
<fmt:bundle basename="resources">
    <jsp:include page="includes/header.jsp"/>
    <jsp:include page="includes/column_right_home.jsp"/>

    <section>
        <form name="registrationForm" method="POST" action="controller">
            <input type="hidden" name="command" value="registration_form"/>
            <fmt:message key="first.name"/>:<br/>
            <input type="text" name="firstName" value="" required/>
            <br/><fmt:message key="last.name"/>:<br/>
            <input type="text" name="lastName" value="" required/>
            <br/><fmt:message key="email"/>:<br/>
            <input type="email" name="email" value="" required/>
            <br/><fmt:message key="login"/>:<br/>
            <input type="text" name="login" value="" required/>
            <br/><fmt:message key="password"/>:<br/>
            <input type="password" name="password" value="" required/>
            <br/>
                ${registrationErrorMessage}
            <br/>
            <input type="submit" value="<fmt:message key="registration.button"/>"/>
        </form>
    </section>
    <jsp:include page="includes/footer.jsp"/>
</fmt:bundle>

