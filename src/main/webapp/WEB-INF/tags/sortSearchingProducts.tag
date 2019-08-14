<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>
<%@ attribute name="url" required="true" %>

<a href="<c:url value="/searchingPage?sort=${sort}&order=${order}"/>">
    <img src="${url}" width="15" height="15">
</a>