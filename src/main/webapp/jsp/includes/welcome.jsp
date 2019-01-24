<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<%--<fmt:setLocale value="${cookie['lang'].value}"/>--%>
<fmt:bundle basename="resources">

    <div id="user">
        <c:if test="${!empty user}">
                <p><fmt:message key="hello"/>, ${user.firstName} ${user.lastName}</p>
            <%--<a href="controller?command=logout"><fmt:message key="logout"/></a>--%>
        </c:if>
    </div>
</fmt:bundle>
