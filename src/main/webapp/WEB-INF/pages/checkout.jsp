<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" class="com.es.phoneshop.model.order.Order" scope="session"/>
<tags:master pageTitle="Checkout" pageClass="checkout">
    <a href="${pageContext.servletContext.contextPath}/cart"><b>Back to cart<b></a>
    <form method="post">
        <pre>                            <b><FONT size="+3">Checkout</font></b> </pre>
        <table>
            <thead>
                <tr>
                    <td align="center"  ><b>Image</b></td>
                    <td align="center"  ><b>Description</b></td>
                    <td  align="center"  ><b>Quantity</b></td>
                    <td  align="center"  class="price"><b>Price</b></td>
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
                        <input name= "quantity" value="${item.quantity}" readonly="true" style="text-align: right">
                    </td>
                    <td  align = "right">
                        <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="&#36"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td><b>Delivery Cost</b></td>
                <td></td>
                <td></td>
                <td  align = "right">
                    <fmt:formatNumber value="${order.deliveryCost}" type="currency" currencySymbol="&#36"/>
                </td>
            </tr>
            <tr bgcolor="#7FFFD4">
                <td><b>Total</b></td>
                <td></td>
                <td  align = "right"><b>${cart.totalQuantity}</b></td>
                <td  align = "right"><b>${order.orderCost}&#36</b></td>
            </tr>
        </table><br><br>
        <a formaction="${pageContext.servletContext.contextPath}/checkout">

            <h4>Personal data</h4><hr color="black">
            <label>
            <font color="red">*</font>
            First Name<br>
            <input name="firstName" value="${order.firstName}">
                <c:if test="${not empty firstNameError}">
                    <span style="color:red">${firstNameError}</span><br>
                </c:if>
            </label><br>

            <label>
            <font color="red">*</font>
            Last Name <br>
            <input name="lastName" value="${order.lastName}">
                <c:if test="${not empty lastNameError}">
                    <span  style="color:red">${lastNameError}</span><br>
                </c:if>
            </label><br>

            <label>
            <font color="red">*</font>
            Phone number <br>
            <input name="phone" value="${order.phone}" placeholder="+375449090909">
                <c:if test="${not empty phoneError}">
                    <span style="color:red">${phoneError}</span><br>
                </c:if>
            </label><br><br>

            <h4>Delivery</h4><hr color="black">
            <label>Delivery mode<br>
                <select name="deliveryMode">
                    <option value="COURIER">Courier</option>
                    <option value="STORE_PICKUP">Store pickup</option>
                </select>
            </label><br>

            <label>Delivery date<br>
            <input name="deliveryDate" value="${order.deliveryDate}" readonly="true">
            </label><br>

            <label>
            <font color="red">*</font>
            Delivery address <br>
            <input name="deliveryAddress" value="${order.deliveryAddress}" placeholder="Minsk, Bedya street, 4">
                <c:if test="${not empty deliveryAddressError}">
                    <span style="color:red">${deliveryAddressError}</span><br>
                </c:if>
            </label><br><br>

            <h4>Payment</h4><hr color="black">
            <label>Payment method<br>
                <select name="paymentMethod">
                    <option value="CASH">Cash</option>
                    <option value="CREDIT_CARD">Credit card</option>
                </select>
            </label>
            <hr color="black">

            <font color="red">* - mandatory fields</font>

        <p align="right">Cost order with delivery: ${order.orderCost}&#36</p>
        <p align="right">For payment: ${order.orderCost}&#36</p>
        <p align="right">The nearest delivery: ${dayDelivery}</p>
        <p align="right"><button>Confirm order</button></p>
    </form>
</tags:master>