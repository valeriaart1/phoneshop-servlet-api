<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<minicart>
    <c:if test="${cart.totalQuantity != 0}">
        <a href="${pageContext.servletContext.contextPath}/cart" >
            ${cart.totalCost} for ${cart.totalQuantity}
        </a>
    </c:if>
</minicart>