<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart" pageClass="cart">
    <a href="${pageContext.servletContext.contextPath}/products"><b>Back to product list<b></a><br>
    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <pre>                            <b><FONT size="+3">Cart</font></b> </pre>
        <c:choose>
            <c:when test="${not empty errors}">
                <span style="color:red">Error updating cart</span>
            </c:when>
            <c:otherwise>
                <span style="color:green">${param.message}</span>
            </c:otherwise>
        </c:choose>
        <br>
        <table>
            <thead>
                <tr>
                    <td align="center"  ><b>Image</b></td>
                    <td align="center"  ><b>Description</b></td>
                    <td  align="center"  ><b>Quantity</b></td>
                    <td  align="center"  class="price"><b>Price</b></td>
                    <td  align="center"  ><b>Action</b></td>
                </tr>
            </thead>
            <c:forEach var="item" items="${cart.cartItems}">
                <tr>
                    <td align="center"  >
                        <img class="product-tile"
                        src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                    </td>
                    <td align="center"  >
                        <a href="${pageContext.servletContext.contextPath}/product/${item.product.id}">${item.product.description}</a>
                    </td>
                    <td>
                        <input name= "quantity" value="${not empty errors[item.product.id] ? paramValues.quantity[item.product.id] : item.quantity}" style="text-align: right">
                        <input type="hidden" name="productId" value="${item.product.id}">
                        <c:if test="${not empty errors[item.product.id]}"><br>
                            <span style="color:red">${errors[item.product.id]}</span>
                        </c:if>
                    </td>
                    <a href="#x" class="overlay" id="win${item.product.id}"></a>
                    <div class="popup" align="center">
                        <h4>History of changes prices ${product.description}:</h4>
                        <c:forEach var="historyOfPrices" items="${item.product.history}">
                            <p>${historyOfPrices.value.date} the price was ${historyOfPrices.key} &#36</p>
                        </c:forEach>
                        <a class="close"title="Закрыть" href="#close"></a>
                    </div>
                    <td  align = "right">
                        <a href="#win${item.product.id}" >
                            <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="&#36"/>
                        </a>
                    </td>
                    <td>
                        <button formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${item.product.id}">
                            Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
            <tr bgcolor="#7FFFD4">
                <td><b>Total</b></td>
                <td></td>
                <td  align = "right"><b>${cart.totalQuantity}</b></td>
                <td  align = "right"><b>${cart.totalCost}&#36</b></td>
                <td></td>
            </tr>
        </table><br>
    <button>Update</button>
    <a href="${pageContext.servletContext.contextPath}/checkout">Go To Checkout</a>
</form>
</tags:master>