<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.CURRENT_LANGUAGE}"/>
<fmt:setBundle basename="messages"/>

<h4 class="text-center"><fmt:message key="view.users.title"/></h4>
<hr/>
<table class="table table-bordered">
    <thead>
    <tr>
        <th><fmt:message key="view.sign.in.email"/></th>
        <th><fmt:message key="view.update.my.account.firstname"/></th>
        <th><fmt:message key="view.update.my.account.lastname"/></th>
        <th><fmt:message key="view.update.my.account.phone"/></th>
        <th><fmt:message key="view.update.my.account.role"/></th>
        <th class="hidden-print"><fmt:message key="view.order.item.action"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${USERS }">
        <tr class="item">
            <td><a href="mailto:${user.email}">${user.email}</a></td>
            <td>${user.firstname}</td>
            <td>${user.lastname}</td>
            <td>${user.phone}</td>
            <td>${user.role}</td>
            <td class="hidden-print">
                <c:choose>
                    <c:when test="${user.role == 'USER'}">
                        <a href="/admin/lock?id=${user.id }" class="btn btn-danger"><fmt:message key="view.users.action.lock"/></a>
                    </c:when>
                    <c:when test="${user.role == 'BLOCKED'}">
                        <a href="/admin/un-lock?id=${user.id }" class="btn btn-success"><fmt:message key="view.users.action.un.lock"/></a>
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>