<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<fmt:bundle basename="resources">
    <div id="column-right">
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
        <ul class="locale">
            <%--<li>Language &nbsp;</li>--%>
            <li class="lang"><a href="?lang=en"><fmt:message key="en.lang"/></a></li>
            <li class="lang"><a href="?lang=ru"><fmt:message key="ru.lang"/></a></li>
        </ul>

        <%--<ul class="locale"/>--%>
        <%--<li>Currency &nbsp;</li>--%>
        <%--<li class="lang"><a href="?lang=en"><fmt:message key="en.lang"/></a></li>--%>
        <%--<li class="lang"><a href="?lang=ru"><fmt:message key="ru.lang"/></a></li>--%>
        <%--</ul>--%>

        <jsp:useBean id="now" class="java.util.Date"/>
        <p>
            <fmt:formatDate value="${now}" type="both"
                            timeStyle="short"
                            dateStyle="medium"/>
        </p>

    </div>
</fmt:bundle>
