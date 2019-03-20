<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<fmt:bundle basename="resources">
    <div id="left">
        <c:if test="${!empty user}">
            <ul class="user">
                <li><a href="controller?command=select_date"><fmt:message key="make.order"/> </a></li>
                <br>
                <li><a href="controller?command=show_all_orders"><fmt:message key="review.my.orders"/> </a></li>
                <br>
                <li><a href="controller?command=review_cart"><fmt:message key="cart"/> </a> </li>
                <br/>
                <li><a href="controller?command=update_user_details"><fmt:message key="personal.details"/> </a> </li>
                <br/>
                <li><a href="controller?command=update_user_password"><fmt:message key="password.update"/> </a> </li>
                <br>
                <li><a href="controller?command=logout"><fmt:message key="logout"/></a></li>
            </ul>

        </c:if>
    </div>

</fmt:bundle>
