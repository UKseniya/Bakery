<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${userLocale}"/>
<%--<fmt:setLocale value="${cookie['lang'].value}" />--%>
<fmt:bundle basename="resources">

    <jsp:include page="jsp/includes/header.jsp"/>
    <jsp:include page="jsp/includes/column_right_home.jsp"/>
    <jsp:include page="jsp/includes/welcome.jsp"/>

<body>
<ul class="login-option">
<c:choose>
<c:when test="${empty user}">
    <li><a href="controller"><fmt:message key="sign-in"/></a></li>
    <li><a href="controller?command=registration"/><fmt:message key="info.signup"/> </a></li>
</c:when>
<c:otherwise>
    <a href="controller?command=user_page"/><fmt:message key="page.account"/> </c:otherwise>
    </c:choose>
</ul>
<section class="center">
    <br><br>
    <h3><fmt:message key="welcome"/></h3><br>
    <h3><fmt:message key="ordering.date.instructions"/> </h3><br/>
    <h3><fmt:message key="info.selection"/></h3><br>
    <h3><fmt:message key="info.ordering"/></h3><br>
</section>
</body>
    <jsp:include page="jsp/includes/footer.jsp"/>
</fmt:bundle>


<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>--%>
<%--<fmt:requestEncoding value="UTF-8" />--%>
<%--<fmt:setLocale value="${cookie['lang'].value}" />--%>
<%--<fmt:bundle basename="resources">--%>
    <%--<jsp:include page="../includes/header.jsp"/>--%>
    <%--<jsp:include page="../includes/column_right_home.jsp"/>--%>
    <%--<body>--%>

    <%--</body>--%>
    <%--<jsp:include page="../includes/footer.jsp"/>--%>
<%--</fmt:bundle>--%>
