<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" class="com.es.phoneshop.model.product.Product" scope="session"/>
<tags:master pageTitle="Searching Page" pageClass="advancedSearchPage">
    <a href="${pageContext.servletContext.contextPath}/products"><b>Back to product list<b></a><br><br>
    <h4>Fields for searching</h4><hr color="black">
    <form method="post">
        <table>
            <tr>
                <td>
                    <table>
                        <thead>
                            <tr>
                                <td>Image</td>
                                <td>Description
                                    <tags:sortSearchingProducts sort="description" order="asc" url="https://img2.pngindir.com/20180518/lal/kisspng-angle-font-5aff94376a8d73.2354539915266990634365.jpg"></tags:sortSearchingProducts>
                                    <tags:sortSearchingProducts sort="description" order="desc" url="https://image.flaticon.com/icons/png/512/16/16748.png"></tags:sortSearchingProducts>
                                </td>
                                <td class="price">Price
                                    <tags:sortSearchingProducts sort="price" order="asc" url="https://img2.pngindir.com/20180518/lal/kisspng-angle-font-5aff94376a8d73.2354539915266990634365.jpg"></tags:sortSearchingProducts>
                                    <tags:sortSearchingProducts sort="price" order="desc" url="https://image.flaticon.com/icons/png/512/16/16748.png"></tags:sortSearchingProducts>
                                </td>
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
                                <a href="#x" class="overlay" id="win${product.id}"></a>
                                <div class="popup" align="center">
                                    <h4>History of changes prices ${product.description}:</h4>
                                    <c:forEach var="historyOfPrices" items="${product.history}">
                                        <p>${historyOfPrices.value.date} the price was <fmt:formatNumber value="${historyOfPrices.key}" type="currency" currencySymbol="&#36"/></p>
                                    </c:forEach>
                                    <a class="close"title="Закрыть" href="#close"></a>
                                </div>
                                <td  align = "center">
                                    <a href="#win${product.id}" >
                                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="&#36"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table><br>
                </td>
                <td><label>
                    Description<br>
                    <input name="description" value="${param.description}">
                    </label><br>

                    <label>
                    Min price <br>
                    <input name="minPrice" value="${param.minPrice}">
                        <c:if test="${not empty minPriceError}">
                            <span  style="color:red">${minPriceError}</span><br>
                        </c:if>
                    </label><br>

                    <label>
                    Max price <br>
                    <input name="maxPrice" value="${param.maxPrice}">
                        <c:if test="${not empty maxPriceError}">
                            <span  style="color:red">${maxPriceError}</span><br>
                        </c:if>
                    </label><br>

                    <label>
                    Min stock <br>
                    <input name="minStock" value="${param.minStock}">
                        <c:if test="${not empty minStockError}">
                            <span  style="color:red">${minStockError}</span><br>
                        </c:if>
                    </label><br>

                    <label>
                    Max stock <br>
                    <input name="maxStock" value="${param.maxStock}">
                        <c:if test="${not empty maxStockError}">
                            <span  style="color:red">${maxStockError}</span><br>
                        </c:if>
                    </label><br>
                    <p><button>Searching</button></p>
                </td>
            </tr>
        </table>
    </form>
</tags:master>