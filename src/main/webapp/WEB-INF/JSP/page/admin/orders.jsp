<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.CURRENT_LANGUAGE}"/>
<fmt:setBundle basename="messages"/>

<h4 class="text-center"><fmt:message key="view.my.orders.title"/></h4>
<hr/>
<table class="table table-bordered">
    <thead>
    <tr>
        <th><fmt:message key="view.my.orders.order.id"/></th>
        <th><fmt:message key="view.my.orders.date"/></th>
        <th><fmt:message key="view.my.orders.status"/></th>
        <th class="hidden-print"><fmt:message key="view.order.item.action"/></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty ORDERS }">
        <tr>
            <td colspan="4" class="text-center">
                <fmt:message key="view.my.orders.no.orders"/>
            </td>
        </tr>
    </c:if>
    <c:forEach var="order" items="${ORDERS }">
        <tr class="item">
            <td>
                <a href="/admin/get-order?id=${order.id }">
                    <fmt:message key="view.my.orders.order.template">
                        <fmt:param value="${order.id }"/>
                    </fmt:message>
                </a>
            </td>
            <td><fmt:formatDate type="BOTH" value="${order.created }" dateStyle="FULL" timeStyle="MEDIUM"/></td>
            <td><fmt:message key="view.my.orders.status.${order.status}"/></td>
            <td class="hidden-print">
                <c:choose>
                    <c:when test="${order.status == 'CREATED'}">
                        <a href="/admin/set-order-status?id=${order.id }&status=ACCEPTED" class="btn btn-success">
                            <fmt:message key="view.my.orders.status.ACCEPTED"/>
                        </a>
                        <a href="/admin/set-order-status?id=${order.id }&status=CANCELLED" class="btn btn-danger">
                            <fmt:message key="view.my.orders.status.CANCELLED"/>
                        </a>
                    </c:when>
                    <c:when test="${order.status == 'ACCEPTED'}">
                        <a href="/admin/set-order-status?id=${order.id }&status=TRANSFERRED" class="btn btn-primary">
                            <fmt:message key="view.my.orders.status.TRANSFERRED"/>
                        </a>
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>