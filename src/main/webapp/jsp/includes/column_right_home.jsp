<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${userLocale}"/>
<%--<fmt:setLocale value="${cookie['lang'].value}" />--%>
<fmt:bundle basename="resources">
    <div id="column-right">

            <ul class="locale">
                <li>Language &nbsp;</li>
                <li class="lang"><a href="?lang=en&country=UK"><fmt:message key="en.lang" /></a></li>
                <li class="lang"><a href="?lang=ru&country=KZ"><fmt:message key="ru.lang" /></a></li>
            </ul>

        <ul class="locale"/>
        <li>Currency &nbsp;</li>
        <li class="lang"><a href="?lang=en"><fmt:message key="en.lang" /></a></li>
        <li class="lang"><a href="?lang=ru"><fmt:message key="ru.lang" /></a></li>
            </ul>

        <jsp:useBean id="now" class="java.util.Date" />
        <p>
            <fmt:formatDate value="${now}" type="both"
                            timeStyle="short"
                            dateStyle="medium" />
        </p>
        <%--<c:if test="${!empty  user}">--%>
            <%--<p><strong><fmt:kz.epam.message key="logged.in.as"/>: ${user.firstName} ${user.lastName} ID:${user.id}</strong></p>--%>
            <%--<a href="kz.epam.servletmmand=logout"><fmt:kz.epam.message key="logout"/></a>--%>
        <%--</c:if>--%>

    </div>
</fmt:bundle>
