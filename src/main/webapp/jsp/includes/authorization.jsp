<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<fmt:bundle basename="resources">

    <div id="user">
        <c:choose>
            <c:when test="${!empty user}">
                <p><fmt:message key="hello"/>, ${user.firstName} ${user.lastName}</p>
            </c:when>
            <c:otherwise>
                <ul class="login-option">
                    <li><a href="controller?command=no_command"><fmt:message key="sign-in"/> &nbsp; <fmt:message
                            key="info.signup"/> </a></li>
                </ul>
            </c:otherwise>
        </c:choose>
    </div>
</fmt:bundle>

