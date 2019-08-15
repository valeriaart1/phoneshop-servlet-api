<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<minicart>
    <c:if test="${cart.totalQuantity != 0}">
        <a align = "right" href="${pageContext.servletContext.contextPath}/cart" >
            <h4>${cart.totalCost} for ${cart.totalQuantity}</h4>
        </a>
    </c:if>
</minicart>