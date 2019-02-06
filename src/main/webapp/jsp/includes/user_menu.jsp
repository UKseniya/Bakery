<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${userLocale}"/>
<%--<fmt:setLocale value="${cookie['lang'].value}" />--%>
<fmt:bundle basename="resources">
    <div id="left">
        <c:if test="${!empty user}">
            <ul class="user">
                <li><a href="controller?command=select_products"><fmt:message key="make.order"/> </a></li> <br>
                <li><a href="controller?command=show_all_orders"><fmt:message key="review.orders"/> </a></li> <br>
                <li><a href="controller?command=review_cart"><fmt:message key="cart"/> </a><li></li> <br>
                <li><a href="controller?command=logout"><fmt:message key="logout"/></a></li>
            </ul>

        </c:if>
    </div>
            <%--<c:if test="${!empty  user}">--%>
            <%--<p><strong><fmt:kz.epam.message key="logged.in.as"/>: ${user.firstName} ${user.lastName} ID:${user.id}</strong></p>--%>
            <%--<a href="kz.epam.servletmmand=logout"><fmt:kz.epam.message key="logout"/></a>--%>
            <%--</c:if>--%>

    </div>
</fmt:bundle>
