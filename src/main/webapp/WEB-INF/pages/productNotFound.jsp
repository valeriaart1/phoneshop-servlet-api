<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="productNotFound" scope="request"/>
<html>
<head>
  <title>ProductNotFound</title>
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
  <h1>Product not found!</h1>
  <br>
  (c) Expert Soft
</main>
</body>
</html>
