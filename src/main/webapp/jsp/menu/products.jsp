    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <fmt:requestEncoding value="UTF-8"/>
        <fmt:setLocale value="${userLocale}"/>
        <fmt:bundle basename="resources">
            <jsp:include page="../includes/header.jsp"/>

            <input type="hidden" name="jspName" value="controller?command=show_available_products">
            <jsp:include page="../includes/column_right_home.jsp"/>

            <body>
            <c:if test="!${empty user}">
                <a href="controller?command=user_page"/><fmt:message key="page.account"/></a>
            </c:if>
            <table id="description">
            <c:forEach var="product" items="${availableProducts}">
                <tr>
                <td><img src="/Bakery/pictures/${product.formattedCode}.jpg" width="200" height="200"></td>
                <td><b>${product.name}</b><br/><br/><br/> ${product.description} </td>
                </tr>
            </c:forEach>
            <tr>
            </table>
            <br/><br/><br/><br/>
            </body>
            <jsp:include page="../includes/footer.jsp"/>
        </fmt:bundle>