<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<html>
<head>
  <title>Product List</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<header>
  <a href="${pageContext.servletContext.contextPath}">
    <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
    PhoneShop
  </a>
</header>
<main>
<br>
  <form>
    <input name="query" value="${param.query}">
    <button>Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description
          <a href="${pageContext.servletContext.contextPath}/products?sort=description&order=asc&query=${param.query}">asc</a><text> </text>
          <a href="${pageContext.servletContext.contextPath}/products?sort=description&order=desc&query=${param.query}">desc</a>
        </td>
        <td class="price">Price
          <a href="${pageContext.servletContext.contextPath}/products?sort=price&order=asc&query=${param.query}">asc</a><text> </text>
          <a href="${pageContext.servletContext.contextPath}/products?query=&{param.query}&sort=price&order=desc">desc</a></td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td>
          <a href="${pageContext.servletContext.contextPath}/product/${product.id}">${product.description}</a>
        </td>
        <td class="price">
          <a>
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </a>
        </td>
      </tr>
    </c:forEach>
  </table>
<br>
(c) Expert Soft
</main>
</body>
</html>
