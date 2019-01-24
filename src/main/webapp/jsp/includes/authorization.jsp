<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<%--<fmt:setLocale value="${cookie['lang'].value}"/>--%>
<fmt:bundle basename="resources">

    <div id="user">
        <c:if test="${!empty user}">
            <p><fmt:message key="hello"/>, ${user.firstName}</p>
        </c:if>
        <c:otherwise>
            <ul class="login-option">
                <li><a href="controller"><fmt:message key="sign-in"/> &nbsp; <fmt:message key="info.signup"/> </a></li>
            </ul>
        </c:otherwise>
    </div>
</fmt:bundle>

