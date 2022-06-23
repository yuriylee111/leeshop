<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.CURRENT_LANGUAGE}"/>
<fmt:setBundle basename="messages"/>

<div class="row">
    <c:forEach var="p" items="${PRODUCTS }">
        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-xlg-2">
            <div id="product${p.id }" class="panel panel-default product">
                <div class="panel-body">
                    <div class="thumbnail">
                        <img src="${p.image }" alt="${p.name }">
                        <div class="desc">
                            <div class="cell">
                                <p>
                                    <span class="title"><fmt:message key="view.product.item.details"/></span>
                                    ${p.description }
                                </p>
                            </div>
                        </div>
                    </div>
                    <h4 class="name">${p.name }</h4>
                    <div class="code">
                        ID: ${p.id }
                    </div>
                    <div class="price">
                        <fmt:formatNumber value="${p.price }" currencySymbol="$" type="CURRENCY"/>
                    </div>
                    <c:if test="${sessionScope.CURRENT_SESSION_USER.role != 'ADMIN'}">
                    <a href="/user/add-to-shopping-cart?productId=${p.id }&backUrl=${ENCODED_CURRENT_URL}" 
                       class="btn btn-primary pull-right buy-btn">
                        <fmt:message key="view.product.item.buy"/>
                    </a>
                    </c:if>
                    <div class="list-group">
                        <span class="list-group-item">
                            <small><fmt:message key="view.product.item.category"/>: </small>
                            <span class="category">${p.category.name }</span>
                        </span>
                        <span class="list-group-item">
                            <small><fmt:message key="view.product.item.country"/>: </small> 
                            <span class="producer">${p.country.name }</span>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div> 