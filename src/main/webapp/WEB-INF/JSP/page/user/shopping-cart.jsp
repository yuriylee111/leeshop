<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.CURRENT_LANGUAGE}"/>
<fmt:setBundle basename="messages"/>

<h4 class="text-center"><fmt:message key="view.shopping.cart.title"/></h4>
<hr/>
<table class="table table-bordered">
    <thead>
    <tr>
        <th><fmt:message key="view.order.item.product"/></th>
        <th><fmt:message key="view.order.item.price"/></th>
        <th><fmt:message key="view.order.item.count"/></th>
        <th class="hidden-print"><fmt:message key="view.order.item.action"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${CURRENT_SHOPPING_CART.items }">
        <tr id="product${item.product.id }" class="item">
            <td class="text-center">
                <img class="small" src="${item.product.image }" alt="${item.product.name }"><br>${item.product.name }
            </td>
            <td class="price">
                <fmt:formatNumber value="${item.product.price }" currencySymbol="$" type="CURRENCY"/>
            </td>
            <td class="count">
                <fmt:formatNumber value="${item.count }"/>
            </td>
            <td class="hidden-print">
                <c:choose>
                    <c:when test="${item.count > 1 }">
                        <a href="/user/remove-from-shopping-cart?productId=${item.product.id }&count=1"
                           class="btn btn-danger remove-product"><fmt:message key="view.shopping.cart.remove.one"/></a><br><br>
                        <a href="/user/remove-from-shopping-cart?productId=${item.product.id }&count=${item.count }"
                           class="btn btn-danger remove-product remove-all"><fmt:message key="view.shopping.cart.remove.all"/></a>
                    </c:when>
                    <c:otherwise>
                        <a href="/user/remove-from-shopping-cart?productId=${item.product.id }&count=1"
                           class="btn btn-danger remove-product"><fmt:message key="view.shopping.cart.remove.one"/></a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="2" class="text-right">
            <strong><fmt:message key="view.order.item.total"/>:</strong>
        </td>
        <td colspan="2" class="total">
            <fmt:formatNumber value="${CURRENT_SHOPPING_CART.totalCost }" currencySymbol="$" type="CURRENCY"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="row hidden-print">
    <div class="col-md-4 col-md-offset-4 col-lg-2 col-lg-offset-5">
        <form action="/user/make-order" method="post">
            <button type="submit" class="btn btn-primary btn-block">
                <fmt:message key="view.shopping.cart.make.order"/>
            </button>
        </form>
    </div>
</div>