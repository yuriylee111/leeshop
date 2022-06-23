<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.CURRENT_LANGUAGE}"/>
<fmt:setBundle basename="messages"/>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#ishopNav" aria-expanded="false">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/show-products"><fmt:message key="view.title"/></a>
        </div>
        <div class="collapse navbar-collapse" id="ishopNav">
            <ul id="currentShoppingCart" class="nav navbar-nav navbar-right ${sessionScope.CURRENT_SHOPPING_CART == null ? 'hidden' : '' }">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <i class="fa fa-shopping-cart" aria-hidden="true"></i> 
                        <fmt:message key="view.header.shopping.cart"/> 
                        (<span class="total-count"><fmt:formatNumber value="${sessionScope.CURRENT_SHOPPING_CART.totalCount}"/></span>)
                        <span class="caret"></span>
                    </a>
                    <div class="dropdown-menu shopping-cart-desc">
                        <fmt:message key="view.header.shopping.cart.total.count"/>: 
                        <span class="total-count">
                            <fmt:formatNumber value="${sessionScope.CURRENT_SHOPPING_CART.totalCount}"/>
                        </span>
                        <br>
                        <fmt:message key="view.header.shopping.cart.total.cost"/>: 
                        <span class="total-cost">
                            <fmt:formatNumber value="${sessionScope.CURRENT_SHOPPING_CART.totalCost }" currencySymbol="$" type="CURRENCY"/>
                        </span>
                        <br>
                        <a href="/user/shopping-cart" class="btn btn-primary btn-block">
                            <fmt:message key="view.header.shopping.cart.view"/>
                        </a>
                    </div>
                </li>
            </ul>
            <c:choose>
                <c:when test="${sessionScope.CURRENT_SESSION_USER != null }">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a>
                                <fmt:message key="view.header.shopping.cart.welcome">
                                    <fmt:param value="${sessionScope.CURRENT_SESSION_USER.fullName }"/>
                                </fmt:message>
                            </a>
                        </li>
                        <c:choose>
                            <c:when test="${sessionScope.CURRENT_SESSION_USER.role == 'USER'}">
                                <li><a href="/user/my-account"><fmt:message key="view.header.shopping.cart.my.account"/></a></li>
                                <li><a href="/user/my-orders"><fmt:message key="view.header.shopping.cart.my.orders"/></a></li>
                            </c:when>
                            <c:when test="${sessionScope.CURRENT_SESSION_USER.role == 'ADMIN'}">
                                <li><a href="/admin/all-users"><fmt:message key="view.users.title"/></a></li>
                                <li><a href="/admin/all-orders"><fmt:message key="view.all.orders.title"/></a></li>
                            </c:when>
                        </c:choose>
                        <li><a href="/sign-out"><fmt:message key="view.header.shopping.cart.sign.out"/></a></li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <a href="/sign-in?backUrl=${ENCODED_CURRENT_URL}" class="btn btn-primary navbar-btn navbar-right sign-in">
                        <fmt:message key="view.header.shopping.cart.sign.in"/>
                    </a>
                    <a href="/sign-up" class="btn btn-success navbar-btn navbar-right sign-in">
                        <fmt:message key="view.header.shopping.cart.sign.up"/>
                    </a>
                </c:otherwise>
            </c:choose>
            <a href="/show-products?hl=ru" class="navbar-btn navbar-right" style="margin: 15px">ru</a>
            <a href="/show-products?hl=en" class="navbar-btn navbar-right" style="margin: 15px">en</a>
        </div>
    </div>
</nav>