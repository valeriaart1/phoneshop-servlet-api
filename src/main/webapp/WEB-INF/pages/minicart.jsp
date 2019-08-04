<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<minicart>
    <a>
        <a href="${pageContext.servletContext.contextPath}/cart" >
        <c:choose>
          <c:when test="${cart.totalQuantity != 0}">
            ${cart.totalCost} for ${cart.totalQuantity}</a>
          </c:when>
        </c:choose>
    </a>
</minicart>