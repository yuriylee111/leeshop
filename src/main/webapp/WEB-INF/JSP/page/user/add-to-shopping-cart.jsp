<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.CURRENT_LANGUAGE}"/>
<fmt:setBundle basename="messages"/>

<h4 class="text-center"><fmt:message key="view.add.to.shopping.cart.title"/></h4>
<hr/>
<form action="/user/add-to-shopping-cart" method="post">
    <input type="hidden" name="productId" value="${DTO.productId}">
    <input type="hidden" name="backUrl" value="${DTO.backUrl}">
    <div class="row">
        <div class="col-xs-12 col-sm-6 col-sm-offset-3">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="thumbnail">
                        <img src="${PRODUCT.image }" alt="${PRODUCT.name }">
                    </div>
                    <h4 class="name text-center">${PRODUCT.name}</h4>
                    <div class="list-group hidden-xs adv-chars">
                        <span class="list-group-item">
                            <small><fmt:message key="view.product.item.category"/>: </small> 
                            <span class="category">${PRODUCT.category.name }</span>
                        </span>
                        <span class="list-group-item">
                            <small><fmt:message key="view.product.item.country"/>: </small> 
                            <span class="producer">${PRODUCT.country.name }</span>
                        </span>
                    </div>
                    <div class="list-group">
                        <span class="list-group-item">
                            <small><fmt:message key="view.product.item.price"/>: </small>
                            <span class="price">
                                <fmt:formatNumber value="${PRODUCT.price }" currencySymbol="$" type="CURRENCY"/>
                            </span>
                        </span>
                        <span class="list-group-item">
                            <small><fmt:message key="view.product.item.count"/>: </small>
                            <input name="count" type="number" class="count" value="${DTO.count}" style="width: 80px">
                            <fmt:message key="view.add.to.shopping.cart.count.hint">
                                <fmt:param value="${PRODUCT.count}"/>
                            </fmt:message>
                            <c:if test="${ERROR_MAP['count'] != null}">
                                <br><span class="label label-danger"><fmt:message key="${ERROR_MAP['count']}"/></span>
                            </c:if>
                        </span>
                    </div>
                    <hr>
                    <button type="submit" class="btn btn-primary">
                        <fmt:message key="view.button.add.to.shopping.cart"/>
                    </button>
                    <a href="${DTO.backUrl}" type="button" class="btn btn-default">
                        <fmt:message key="view.button.cancel"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</form>