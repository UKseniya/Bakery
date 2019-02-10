<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${userLocale}"/>
<%--<fmt:setLocale value="${cookie['lang'].value}" />--%>
<fmt:bundle basename="resources">

    <!DOCTYPE html>

    <html lang="${userLocale}">
        <head>
            <title>Home Bakery</title>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <link rel="stylesheet" href="/Bakery/style/bakery.css">
            <link rel="icon" type="image/x-icon" href="/Bakery/style/favicon.ico" />
        </head>
            <header>
                <h1><fmt:message key="title"/></h1>
                <ul class="menu-main" >
                    <li><a href="/Bakery/index.jsp"><fmt:message key="main.page"/></a></li>
                    <li><a href="/Bakery/jsp/menu/products.jsp"><fmt:message key="products"/></a></li>
                    <li><a href="/Bakery/jsp/menu/contacts.jsp"><fmt:message key="contacts"/></a></li>
                    <%--<jsp:useBean id="role" class="kz.epam.entities.User"/>--%>
                    <%--<c:forEach var="role" items="${role}">--%>
                        <%--<c:if test="${role == 'user'}" scope="session">--%>
                            <%--<li class="right"><a href="/Bakery/jsp/cart.jsp"><fmt:kz.epam.message key="cart"/></a></li>--%>
                            <%--<li class="right"><a href="/Bakery/jsp/userOrder.jsp"><fmt:kz.epam.message key="orders"/></a></li>--%>
                        <%--</c:if>--%>
                        <%--<c:if test="${role == 'administrator'}" scope="session">--%>
                            <%--<li class="right"><a href="/Bakery/jsp/adminOrder.jsp"><fmt:kz.epam.message key="orders"/></a></li>--%>
                        <%--</c:if>--%>
                    <%--</c:forEach>--%>
                </ul>
            </header>
        </fmt:bundle>

