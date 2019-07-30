<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
<br>
  <form>
    <pre> <input name="query" value="${param.query}"> <button>Search</button></pre>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description
          <tags:sort query="${param.query}" sort="description" order="asc" url="https://img2.pngindir.com/20180518/lal/kisspng-angle-font-5aff94376a8d73.2354539915266990634365.jpg"></tags:sort>
          <tags:sort query="${param.query}" sort="description" order="desc" url="https://image.flaticon.com/icons/png/512/16/16748.png"></tags:sort>
        </td>
        <td class="price">Price
          <tags:sort query="${param.query}" sort="price" order="asc" url="https://img2.pngindir.com/20180518/lal/kisspng-angle-font-5aff94376a8d73.2354539915266990634365.jpg"></tags:sort>
          <tags:sort query="${param.query}" sort="price" order="desc" url="https://image.flaticon.com/icons/png/512/16/16748.png"></tags:sort>
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
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup" align="center">
           <h4>History of changes prices ${product.description}:</h4>
           <c:forEach var="historyOfPrices" items="${product.history}">
             <p>${historyOfPrices.date} the price was ${historyOfPrices.price} &#36</p>
           </c:forEach>
           <a class="close"title="Закрыть" href="#close"></a>
        </div>
        <td class="price">
          <a href="#win1" >
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="&#36"/>
          </a>
        </td>
      </tr>
    </c:forEach>
  </table>
  <br>
<main>
<h6>(c) Expert Soft</h6>
</main>
</tags:master>