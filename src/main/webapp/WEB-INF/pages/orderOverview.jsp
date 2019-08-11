<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" class="com.es.phoneshop.model.order.Order" scope="session"/>
<tags:master pageTitle="Checkout" pageClass="checkout">
    <a href="${pageContext.servletContext.contextPath}/products"><b>Back to product list<b></a>
    <form method="post">
        <pre>                            <b><FONT size="+3">Overview</font></b> </pre>
        <table>
            <thead>
                <tr>
                    <td align="center"  ><b>Image</b></td>
                    <td align="center"  ><b>Description</b></td>
                    <td  align="center"  ><b>Quantity</b></td>
                    <td  align="center"  class="price"><b>Price</b></td>
                </tr>
            </thead>
            <c:forEach var="item" items="${order.cartItems}">
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
                <td  align = "right"><b>${order.totalQuantity}</b></td>
                <td  align = "right"><b>${order.orderCost}&#36</b></td>
            </tr>
        </table><br><br>
        <a formaction="${pageContext.servletContext.contextPath}/orderOverview">
            <h4>Personal data</h4><hr color="black">
            <label>
            First Name<br>
            <input  name="firstName" value="${order.firstName}" readonly="true">
            </label><br>

            <label>
            Last Name <br>
            <input name="lastName" value="${order.lastName}" readonly="true">
            </label><br>

            <label>
            Phone number <br>
            <input name="phone" value="${order.phone}" readonly="true">
            </label><br><br>

            <h4>Delivery</h4><hr color="black">
            <label>Delivery mode<br>
            <input name="deliveryMode" value="${order.deliveryMode}" readonly="true">
            </label><br>

            <label>Delivery date<br>
            <input name="deliveryDate" value="${order.deliveryDate}" readonly="true">
            </label><br>

            <label>
            Delivery address <br>
            <input name="deliveryAddress" value="${order.deliveryAddress}" readonly="true">
            </label><br><br>

            <h4>Payment</h4><hr color="black">
            <label>Payment method<br>
            <input name="paymentMethod" value="${order.paymentMethod}" readonly="true">
            </label>
            <hr color="black">

            <p align="right">2019 phoneshop.by</p>
            <p align="right">+375 44 7-806-835</p>
        </a>
    </form>
</tags:master>