<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<%--<fmt:setLocale value="${cookie['lang'].value}"/>--%>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/welcome.jsp"/>

    <body>
    <div id="left">
    <c:if test="${!empty user}">
        <a href="controller?command=makeOrder"><fmt:message key="make.order"/> </a> <br>
        <a href="controller?command=reviewOrders"><fmt:message key="review.orders"/> </a> <br>
        <a href="controller?command=cart"><fmt:message key="cart"/> </a> <br>
        <a href="controller?command=logout"><fmt:message key="logout"/></a>
    </c:if>
    </div>

    <table id="cart">

        <tr>
            <th><fmt:message key="name"/> </th>
                <%--<th><fmt:message key="code"/> </th>--%>
            <th><fmt:message key="price"/> </th>
            <th>&nbsp;</th>
            <th><fmt:message key="quantity"/> </th>
            <th>&nbsp;</th>
            <th><fmt:message key="sum"/></th>
            <th>&nbsp;</th>
        </tr>
        <c:forEach var="item" items="${cart.items}">
            <form name="update" method="POST" action="controller">
                <input type="hidden" name="command" value="update_cart">
                <tr>
                    <td><fmt:message key="product.name.${item.product.formatedName}"/></td>
                    <td>${item.product.price}</td>
                    <td><input type="hidden" name="productCode" value="${item.product.code}">
                        <input type="hidden" name="quantity" value="${item.quantity}">
                        <input type="submit"  name="addButton" value="+"></td>
                    <td>${item.quantity}</td>
                    <td><input type="hidden" name="productCode" value="${item.product.code}">
                        <input type="hidden" name="quantity" value="${item.quantity}">
                        <input type="submit" name="removeButton" value="-"></td>
                    <td>${item.total}</td>
                </tr>
            </form>
        </c:forEach>
        <tr>
            <td colspan="3">
                <p><b>To change the quantity for an item</b>, enter the new quantity
                    and click on the Update button.</p>
                <p><b>To remove an item</b>, click on the Remove button.</p>
            </td>
        </tr>
    </table>
    <form name="cart" method="POST" action="controller">
        <input type="hidden" name="command" value="add_to_cart">
        <input type="submit" value="<fmt:message key="button.order"/>">
    </form>
    <br/>
    <form name="cart" method="POST" action="controller">
        <input type="hidden" name="command", value="select_products">
        <input type="submit" value="<fmt:message key="button.continue"/> ">
    </form>

    <br/><br/><br/>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>

