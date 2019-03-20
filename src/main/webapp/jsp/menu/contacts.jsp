    <%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
        <fmt:requestEncoding value="UTF-8"/>
        <fmt:setLocale value="${userLocale}"/>
        <fmt:bundle basename="resources">
            <jsp:include page="../includes/header.jsp"/>
            <jsp:include page="../includes/column_right_home.jsp"/>
            <jsp:include page="../includes/welcome.jsp"/>
            <jsp:include page="../includes/user_menu.jsp"/>

            <body>
            <section class="contacts">
            <p><img src="/Bakery/style/icon/house.png" width="30" height="30">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="address"/><br/>
            <p><img src="/Bakery/style/icon/phone.png" width="30" height="30">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="phone"/>: &nbsp; +7 (701) 607 6307  </p>
            <p><img src="/Bakery/style/icon/email.png" width="30" height="30">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="email"/>: &nbsp; customer.support@test.com</p><br/>

            <img src="/Bakery/style/icon/instagram.png" width="30" height="30">&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://www.instagram.com/homeastbakery">Home Bakery</a>

            <p><img src="/Bakery/style/icon/placeholder.png" width="30" height="30">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="map"/> </p>
            <script type="text/javascript" charset="utf-8" async src="https://api-maps.yandex.ru/services/constructor/1.0/js/?um=constructor%3A236717a6200fef567e927f683413fd0b47b9c031da1f67d42439763ff796a7c4&amp;width=501&amp;height=389&amp;lang=en_FR&amp;scroll=true"></script>

            </section>
            <br/><br/><br/>
            </body>
            <jsp:include page="../includes/footer.jsp"/>
        </fmt:bundle>
