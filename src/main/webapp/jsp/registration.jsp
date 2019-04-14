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
            <input type="hidden" name="command" value="registration_form"/>
            <label><fmt:message key="first.name"/>:</label><br/>
            <input type="text" name="firstName" placeholder="Ivan" value=""
                   oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required/>
            <br/><label><fmt:message key="last.name"/>:</label><br/>
            <input type="text" name="lastName" placeholder="Ivanov" value=""
                   oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required/>
            <br/><label><fmt:message key="email"/>:</label> &nbsp; <i>${emailError}</i><br/>
            <input type="email" name="email" placeholder="ivan@test.com"
                   pattern="^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$" value=""/>
            <br/><label><fmt:message key="phone"/>:</label> &nbsp; <i>${phoneNumberError}</i><br/>
            <input type="text" id="phone" name="phone" placeholder="87017776655" pattern= "[0-9]{11}" value=""
                   oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required/>
            <br/><label><fmt:message key="login"/>:</label><br/>
            <input type="text" name="login" value=""
                   oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required/>
            <br/><label><fmt:message key="password"/>:</label><br/>
            <input type="password" name="password" value=""
                   oninvalid="this.setCustomValidity(<fmt:message key="input.message"/>)"
                   oninput="this.setCustomValidity('')" required/>
            <br/>
            <i> ${registrationErrorMessage} </i>
            <br/>
            <input type="submit" value="<fmt:message key="registration.button"/>"/>
        </form>
    </section>
    <jsp:include page="includes/footer.jsp"/>
</fmt:bundle>

