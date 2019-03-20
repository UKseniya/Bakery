<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<fmt:bundle basename="resources">
    <jsp:include page="includes/header.jsp"/>
    <jsp:include page="includes/column_right_home.jsp"/>

    <section>
        <form name="registrationForm" method="POST" action="controller">
            <input type="hidden" name="command" value="update_user_details"/>
            <label><fmt:message key="first.name"/>:</label><br/>
            <input type="text" name="firstName" value="${user.firstName}"
                   oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required/>
            <br/><label><fmt:message key="last.name"/>:</label><br/>
            <input type="text" name="lastName" value="${user.lastName}"
                   oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required/>
            <br/><label><fmt:message key="email"/>:</label><br/>
            <input type="email" name="email" value="${user.email}"/>
            <br/><label><fmt:message key="phone"/>:</label><br/>
            <i> ${phoneNumberError} </i>
            <input type="text" id="phone" name="phone" pattern= "[0-9]{11}" value="${user.phoneNumber}"
                   oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required/>
            <br/>
            <br/>
            <input type="submit" name="updateButton" value="<fmt:message key="button.update"/>"/>
        </form>
    </section>
    <jsp:include page="includes/footer.jsp"/>
</fmt:bundle>

