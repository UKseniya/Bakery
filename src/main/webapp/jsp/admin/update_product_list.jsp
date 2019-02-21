<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<script src="javascript/show_hidden.js" async type="text/javascript"></script>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/authorization.jsp"/>
    <jsp:include page="../includes/admin_menu.jsp"/>

    <section>
        <p><fmt:message key="product.active"/></p>
        <a href="controller?command=add_new_product"><fmt:message key="product.new"/></a><br/><br/>
        <table class="list">
            <tr>
                <th><fmt:message key="product.code"/></th>
                <th><fmt:message key="product.name"/></th>
                <th><fmt:message key="price"/></th>
            </tr>
            <c:forEach var="product" items="${availableProducts}">
                <tr>
                    <td>${product.code}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>
                        <form name="update" method="POST" action="controller">
                            <input type="hidden" name="command" value="update_product_list">
                            <input type="hidden" name="productCode" value="${product.code}">
                            <input type="submit" name="removeButton" value="<fmt:message key="button.remove"/> "></form>
                    </td>
                    <td>
                        <form name="update" method="POST" action="controller">
                            <input type="hidden" name="command" value="show_product_info">
                            <input type="hidden" name="productCode" value="${product.code}">
                            <input type="submit" name="changeButton" value="<fmt:message key="button.change"/> "></form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br/>
        <p><fmt:message key="product.inactive"/></p>
        <table class="list">
            <tr>
                <th><fmt:message key="product.code"/></th>
                <th><fmt:message key="product.name"/></th>
                <th><fmt:message key="price"/></th>
            </tr>
            <c:forEach var="product" items="${cancelledProducts}">
                <tr>
                    <td>${product.code}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>
                        <form name="update" method="POST" action="controller">
                            <input type="hidden" name="command" value="update_product_list">
                            <input type="hidden" name="productCode" value="${product.code}">
                            <input type="submit" name="addButton" value="<fmt:message key="button.add"/> "></form>
                    </td>
                    <td>
                        <form name="update" method="POST" action="controller">
                            <input type="hidden" name="command" value="show_product_info">
                            <input type="hidden" name="productCode" value="${product.code}">
                            <input type="submit" name="changeButton" value="<fmt:message key="button.change"/> "></form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </section>

    <br/>

    <br/>


    <br/><br/><br/>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>


