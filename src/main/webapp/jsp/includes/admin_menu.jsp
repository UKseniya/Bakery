<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<fmt:bundle basename="resources">
    <div id="left">
        <c:if test="${!empty user}">
            <ul class="user">
                <li><a href="controller?command=show_tasks"><fmt:message key="order.review"/> </a></li>
                <br>
                <li><a href="controller?command=show_income"><fmt:message key="review.income"/> </a></li>
                <br>
                <li><a href="controller?command=update_product_list"><fmt:message key="product.list"/> </a> </li>
                <br/>
                <li><a href="controller?command=update_user_details"><fmt:message key="personal.details"/> </a> </li>
                <br/>
                <li><a href="controller?command=update_user_password"><fmt:message key="password.update"/> </a> </li>
                <br>
                <li><a href="controller?command=logout"><fmt:message key="logout"/></a></li>
            </ul>

        </c:if>
    </div>

    </div>
</fmt:bundle>