<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<script src="javascript/show_hidden.js" async type="text/javascript"></script>
<fmt:bundle basename="resources">

    <!DOCTYPE html>

    <html lang="${userLocale}">
    <head>
        <title>Home Bakery</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="/Bakery/style/bakery.css">
        <link rel="icon" type="image/x-icon" href="/Bakery/style/favicon.ico"/>
    </head>
    <header>
        <h1><fmt:message key="title"/></h1>
        <ul class="menu-main">
            <li><a href="/Bakery/index.jsp"><fmt:message key="main.page"/></a></li>
            <li><a href="controller?command=show_available_products"><fmt:message key="products"/></a></li>
            <li><a href="/Bakery/jsp/menu/contacts.jsp"><fmt:message key="contacts"/></a></li>
        </ul>
    </header>
</fmt:bundle>

