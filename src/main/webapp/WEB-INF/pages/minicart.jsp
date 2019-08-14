<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<minicart>
    <a href="${pageContext.servletContext.contextPath}/cart" >
        <c:if test="${cart.totalQuantity != 0}">
            ${cart.totalCost} for ${cart.totalQuantity}
        </c:if>
    </a>
</minicart>