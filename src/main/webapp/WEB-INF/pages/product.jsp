<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<html>
<head>
  <title>Product</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product">
<header>
  <a href="${pageContext.servletContext.contextPath}">
    <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
    PhoneShop
  </a>
</header>
<main>
  <h1>Information about ${product.description}</h1>
  <p>
    <img width="500px" height="70px" class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}" align="middle">
    Price:
    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
  </p>
  <!--
    <h3>History of changes prices:</h3>
    <table>
      <tr>
        <td>Date</td>
        <td class="price">Price</td>
      </tr>
      <tr>
        <c:forEach var="historyOfPrices" items="${product.history}">
        <tr>
          <td>
          ${historyOfPrices.date}
          </td>
          <td>
            <fmt:formatNumber value="${historyOfPrices.price}" type="currency" currencySymbol="${historyOfPrices.currency.symbol}"/>
            ${historyOfPrices.price}
          </td>
        </tr>
        </c:forEach>-->
  <br>
  (c) Expert Soft
</main>
</body>
</html>