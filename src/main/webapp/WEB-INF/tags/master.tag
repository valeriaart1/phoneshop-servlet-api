<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ attribute name="pageClass" type="java.lang.String" required="false" %>

<html>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
  <header>
  <p>
    <a href="${pageContext.servletContext.contextPath}">
      <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
      PhoneShop
    </a>
    <a href="${pageContext.servletContext.contextPath}/cart">
      <img hspace="40" vspace="15" align="right" src="https://cdn.onlinewebfonts.com/svg/img_121831.png"/>
    </a>
  </p>
  </header>
  <jsp:include page="/WEB-INF/pages/minicart.jsp"/>
  <body class=${pageClass}>
  <main>
    <jsp:doBody/>
    <h6>(c) Expert Soft</h6>
  </main>
</body>
</html>