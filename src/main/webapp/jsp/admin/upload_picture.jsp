<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${userLocale}"/>
<script src="javascript/show_hidden.js" async type="text/javascript"></script>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/authorization.jsp"/>
    <jsp:include page="../includes/admin_menu.jsp"/>

    <body>

    <section>
        <form enctype="multipart/form-data" method="POST" action="upload">
            <p><fmt:message key="message.upload"/> </p>
            <label><fmt:message key="picture.select"/></label><br/> <br/>
                    <input type="file" name="file" id="file" /><br /><br/>

                    <input type="submit" name="addButton" value="<fmt:message key="button.add.picture"/>" id="upload" />

        <br/> <br/>
        </form>
    </section>

    <br>

    <br/>


    <br/><br/><br/>

    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>


