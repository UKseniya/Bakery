<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>

<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/authorization.jsp"/>
    <jsp:include page="../includes/admin_menu.jsp"/>

    <body>
    <section class="center">
        <a href="controller?command=show_income"><fmt:message key="back"/> </a><br/> <br/>
        <p><fmt:message key="income.month.current"/> </p><br/>
            ${currentMonthIncome.month} ${currentMonthIncome.year} &nbsp; &nbsp; &nbsp; &nbsp;
            ${currentMonthIncome.sumCurrencyFormat}<br/><br/>
        <p><fmt:message key="income.month.previous"/> </p><br/>
            ${previousMonthIncome.month} ${previousMonthIncome.year} &nbsp; &nbsp; &nbsp; &nbsp;
            ${previousMonthIncome.sumCurrencyFormat}<br/>

    </section>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>
