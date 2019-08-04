<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Exception" pageClass="exception">
<a href="${pageContext.servletContext.contextPath}/products"><b>Back to product list<b></a>
<br>
    <h4>
        Product with id = ${idProductNotFound} not found!
    </h4>
</tags:master>