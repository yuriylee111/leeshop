<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.CURRENT_LANGUAGE}"/>
<fmt:setBundle basename="messages"/>

<h4 class="text-center"><fmt:message key="view.order.title"/></h4>
<hr/>
<table class="table table-bordered">
    <thead>
    <tr>
        <th><fmt:message key="view.order.item.product"/></th>
        <th><fmt:message key="view.order.item.price"/></th>
        <th><fmt:message key="view.order.item.count"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${ORDER.items }">
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
        </tr>
    </c:forEach>
    <tr>
        <td colspan="2" class="text-right">
            <strong><fmt:message key="view.order.item.total"/>:</strong>
        </td>
        <td colspan="2" class="total">
            <fmt:formatNumber value="${ORDER.totalCost }" currencySymbol="$" type="CURRENCY"/>
        </td>
    </tr>
    </tbody>
</table>
