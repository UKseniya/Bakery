<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/authorization.jsp"/>
    <jsp:include page="../includes/user_menu.jsp"/>

    <body>
    <section class="center">
        <b><i>${orderErrorMessage}</i></b> <br/>
        <c:choose>
            <c:when test="${empty cart.items}">
                <p><fmt:message key="cart.empty"/></p>
            </c:when>
            <c:otherwise>
                <table class="productsList">
                    <tr>
                        <th style="width: 200px"><fmt:message key="name"/></th>
                        <th><fmt:message key="price"/></th>
                        <th colspan="3"><fmt:message key="quantity"/></th>
                        <th><fmt:message key="sum"/></th>
                    </tr>
                    <c:forEach var="item" items="${cart.items}">
                        <form name="update" method="POST" action="controller">
                            <input type="hidden" name="command" value="update_cart">
                            <tr>
                                <td style="width: 200px">${item.product.name}</td>
                                <td>${item.product.priceCurrencyFormat}</td>
                                <td><input type="hidden" name="productCode" value="${item.product.code}">
                                    <input type="hidden" name="quantity" value="${item.quantity}">
                                    <input type="submit" name="addButton" value="+"></td>
                                <td>${item.quantity}</td>
                                <td><input type="hidden" name="productCode" value="${item.product.code}">
                                    <input type="hidden" name="quantity" value="${item.quantity}">
                                    <input type="submit" name="removeButton" value="-"></td>
                                <td>${item.totalCurrencyFormat}</td>
                            </tr>
                        </form>
                    </c:forEach>
                    <tr>
                        <td colspan="5" style="text-align: right"><fmt:message key="total"/></td>
                        <td>${cart.totalCurrencyFormat}</td>
                    </tr>
                    <tr>
                        <td colspan="6">
                            <fmt:message key="cart.information.quantity.increase"/><br/>
                            <fmt:message key="cart.information.decrease"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form name="cart" method="POST" action="controller">
                                <input type="hidden" name="command" value="checkout">
                                <input type="submit" value="<fmt:message key="button.order"/>">
                            </form>
                        </td>
                        <td colspan="4">
                            <form name="cart" method="POST" action="controller">
                                <input type="hidden" name="command" value="select_products">
                                <input type="submit" value="<fmt:message key="button.continue"/> ">
                            </form>
                        </td>

                    </tr>
                </table>
            </c:otherwise>
        </c:choose>

        <br>

        <br/>


        <br/><br/><br/>
    </section>
    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>

