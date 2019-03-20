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
            <input type="hidden" name="command" value="update_user_password"/>
            <b><i> ${incorrectPasswordMessage}</i></b>
            <br/><label><fmt:message key="password.current"/>:</label><br/>
            <input type="password" name="currentPassword" value=""
                   oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required/>
            <br/>
            <br/><label><fmt:message key="password.new"/>:</label>
            <b><i> ${samePasswordMessage} </i></b>
            <br/><input type="password" name="newPassword" value=""
                   oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required/>
            <br/>

            <br/>
            <input type="submit" name="updateButton" value="<fmt:message key="button.update"/>"/>
        </form>
    </section>
    <jsp:include page="includes/footer.jsp"/>
</fmt:bundle>


