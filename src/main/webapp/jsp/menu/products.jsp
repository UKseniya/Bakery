<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
    <fmt:setLocale value="${userLocale}"/>
<%--<fmt:setLocale value="${cookie['lang'].value}"/>--%>
<fmt:bundle basename="resources">
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/column_right_home.jsp"/>
    <jsp:include page="../includes/welcome.jsp"/>
    <body>
            <table id="description">
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Black Forest.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.blackforest"/></b><br/><br/><br/> <fmt:message key="product.description.blackforest"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Red Velvet.jpg" width="200" height="200" ></td>
    <td><b><fmt:message key="product.name.redvelvet"/></b><br/><br/><br/><fmt:message key="product.description.redvelvet"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Cheesecake.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.cheesecake"/></b><br/><br/><br/><fmt:message key="product.description.cheesecake"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Banoffee Pie.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.banoffeepie"/></b><br/><br/><br/><fmt:message key="product.description.banoffeepie"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Napoleon.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.napoleon"/></b><br/><br/><br/><fmt:message key="product.description.napoleon"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Carrot Cake.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.carrotcake"/></b><br/><br/><br/><fmt:message key="product.description.carrotcake"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Kiev Cake.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.kievcake"/></b><br/><br/><br/><fmt:message key="product.description.kievcake"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Honey Cake.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.honeycake"/></b><br/><br/><br/><fmt:message key="product.description.honeycake"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Pavlova.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.pavlova"/></b><br/><br/><br/><fmt:message key="product.description.pavlova"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Macarons.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.macarons"/></b><br/><br/><br/><fmt:message key="product.description.macarons"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Brownie.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.brownie"/></b><br/><br/><br/><fmt:message key="product.description.brownie"/> </td>
    </tr>
    <tr>
    <td><img src="/Bakery/jsp/style/pictures/Eclair.jpg" width="200" height="200"></td>
    <td><b><fmt:message key="product.name.eclair"/></b><br/><br/><br/><fmt:message key="product.description.eclair"/> </td>
    </tr>
    </table>
    <br/><br/><br/><br/>
    </body>
    <jsp:include page="../includes/footer.jsp"/>
</fmt:bundle>