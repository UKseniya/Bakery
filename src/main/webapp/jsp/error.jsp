<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${cookie['lang'].value}" />
<fmt:bundle basename="resources">
    <jsp:include page="/jsp/includes/header.jsp"/>
    <jsp:include page="/jsp/includes/column_right_home.jsp"/>
    <body>
    <jsp:expression>
        (request.getAttribute("errorMessage") != null)? (String) request.getAttribute("errorMessage") : "unknown error"
    </jsp:expression>
    </body>
    <jsp:include page="/jsp/includes/footer.jsp"/>
</fmt:bundle>
