<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="query" required="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>
<%@ attribute name="url" required="true" %>

<a href="<c:url value="/products?query=${param.query}&sort=${sort}&order=${order}"/>">
    <img src="${url}" width="15" height="15">
</a>