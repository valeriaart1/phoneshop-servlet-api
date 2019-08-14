<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product" pageClass="product-detail">
    <a href="${pageContext.servletContext.contextPath}/products"><b>Back to product list<b></a><br><br>
    <h4>Information about ${product.description}</h4>
    <c:choose>
        <c:when test="${not empty error}">
            <p class = "error"><font color="red">Error</font></p>
        </c:when>
        <c:otherwise>
            <p><font color="green">${param.message}</font></p>
        </c:otherwise>
    </c:choose>
    <pre>    <img width = "185" height = "200" class="product-title" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ex..${product.imageUrl}">   <b>Price: ${product.price} &#36</b></pre>
    <form method = "post">
        <pre> <input size = "26" name = "quantity" class = "price" value = "${empty param.quantity ? 1 : param.quantity}"> <button>Add to cart</button><br></pre>
        <c:choose>
            <c:when test = "${not empty error}">
                <pre> <a class = "error">${error}</a></pre>
            </c:when>
            <c:otherwise>
                <br>
            </c:otherwise>
        </c:choose>
    </form>
    <c:if test="${not empty viewedProducts}">
    <h5>Recently viewed: </h5><br>
    <table border="1">
        <tr>
            <c:forEach var="viewed" items="${viewedProducts}">
                <td align="center"  width="167">
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewed.imageUrl}" align = "center">
                    <p><br><a href="${pageContext.servletContext.contextPath}/product/${viewed.id}">${viewed.description}</a><br>
                       <a href="#x" class="overlay" id="win${viewed.id}"></a>
                          Price: <fmt:formatNumber value="${viewed.price}" type="currency" currencySymbol="&#36"/>
                     </p>
                </td>
            </c:forEach>
        </tr>
    </table>
    </c:if>
</tags:master>