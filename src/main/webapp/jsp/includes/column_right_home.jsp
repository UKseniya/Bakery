<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="UTF-8" />
<%--<fmt:formatDate value="${userLocale}"/>--%>
<fmt:setLocale value="${cookie['lang'].value}" />
<fmt:bundle basename="resources">
    <div id="column-right">
        <ul>
            <li class="lang"><a href="?cookieLocale=en"><fmt:message key="en.lang" /></a></li>
            <li class="lang"><a href="?cookieLocale=ru"><fmt:message key="ru.lang" /></a></li>
        </ul>
        <%--<form>--%>
            <%--<select name="lang" onchange='this.form.submit()'>--%>
                <%--<c:choose>--%>
                    <%--<c:when test="${userLocale.language =='ru'}">--%>
                        <%--<option value='ru' selected>Русский--%>
                        <%--<option value='en'>English--%>
                        <%--</c:when>--%>
                        <%--<c:otherwise>--%>
                        <%--<option value='ru'>Русский--%>
                        <%--<option value='en' selected>English--%>
                        <%--</c:otherwise>--%>
                    <%--</c:choose>--%>
            <%--</select>--%>
        <%--</form>--%>
        <jsp:useBean id="now" class="java.util.Date" />
        <p>
            <fmt:formatDate value="${now}" type="both"
                            timeStyle="short"
                            dateStyle="medium" />
        </p>
        <%--<c:if test="${!empty  user}">--%>
            <%--<p><strong><fmt:message key="logged.in.as"/>: ${user.firstName} ${user.lastName} ID:${user.id}</strong></p>--%>
            <%--<a href="controller?command=logout"><fmt:message key="logout"/></a>--%>
        <%--</c:if>--%>

    </div>
</fmt:bundle>
