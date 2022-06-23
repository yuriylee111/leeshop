<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.CURRENT_LANGUAGE}"/>
<fmt:setBundle basename="messages"/>

<div class="visible-xs-block xs-option-container">
    <a class="pull-right" data-toggle="collapse" href="#productCatalog">
        <fmt:message key="view.aside.product.catalog"/> <span class="caret"></span>
    </a>
</div>

<div id="productCatalog" class="panel panel-success collapse">
    <div class="panel-heading"><fmt:message key="view.aside.product.catalog"/></div>
    <div class="list-group">
        <a href="/show-products" class="list-group-item">
            <fmt:message key="view.aside.category.all"/>
        </a>
        <c:forEach var="category" items="${CATEGORY_LIST }">
            <a href="/show-products-by-category?categoryId=${category.id }" class="list-group-item">
                ${category.name}
            </a>
        </c:forEach>
    </div>
</div>