<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.CURRENT_LANGUAGE}"/>
<fmt:setBundle basename="messages"/>

<div class="row">
    <div class="col-xs-12 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-body">
                <!-- https://getbootstrap.com/docs/3.3/css/#forms -->
                <form action="/user/my-account" method="post">
                    <input type="hidden" name="id" value="${DTO.id}">
                    <input type="hidden" name="email" value="${DTO.email}">
                    <div class="form-group ${ERROR_MAP['firstname'] != null ? 'has-error has-feedback' : ''}">
                        <label for="firstname"><fmt:message key="view.update.my.account.firstname"/> </label>
                        <input type="text" class="form-control" id="firstname" name="firstname" value="${DTO.firstname != null ? DTO.firstname : ''}">
                        <c:if test="${ERROR_MAP['firstname'] != null}">
                            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                            <div class="label label-danger"><fmt:message key="${ERROR_MAP['firstname']}"/></div>
                        </c:if>
                    </div>
                    <div class="form-group ${ERROR_MAP['lastname'] != null ? 'has-error has-feedback' : ''}">
                        <label for="firstname"><fmt:message key="view.update.my.account.lastname"/> </label>
                        <input type="text" class="form-control" id="lastname" name="lastname" value="${DTO.lastname != null ? DTO.lastname : ''}">
                        <c:if test="${ERROR_MAP['lastname'] != null}">
                            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                            <div class="label label-danger"><fmt:message key="${ERROR_MAP['lastname']}"/></div>
                        </c:if>
                    </div>
                    <div class="form-group ${ERROR_MAP['password'] != null ? 'has-error has-feedback' : ''}">
                        <label for="password"><fmt:message key="view.update.my.account.password"/></label>
                        <input type="password" class="form-control" id="password" name="password">
                        <c:if test="${ERROR_MAP['password'] != null}">
                            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                            <div class="label label-danger"><fmt:message key="${ERROR_MAP['password']}"/></div>
                        </c:if>
                    </div>
                    <div class="form-group ${ERROR_MAP['password2'] != null ? 'has-error has-feedback' : ''}">
                        <label for="password2"><fmt:message key="view.update.my.account.password2"/></label>
                        <input type="password" class="form-control" id="password2" name="password2">
                        <c:if test="${ERROR_MAP['password2'] != null}">
                            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                            <div class="label label-danger"><fmt:message key="${ERROR_MAP['password2']}"/></div>
                        </c:if>
                    </div>
                    <div class="form-group ${ERROR_MAP['phone'] != null ? 'has-error has-feedback' : ''}">
                        <label for="phone"><fmt:message key="view.update.my.account.phone"/></label>
                        <input type="tel" class="form-control" id="phone" name="phone" value="${DTO.phone != null ? DTO.phone : ''}">
                        <c:if test="${ERROR_MAP['phone'] != null}">
                            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                            <div class="label label-danger"><fmt:message key="${ERROR_MAP['phone']}"/></div>
                        </c:if>
                    </div>
                    <button type="submit" class="btn btn-primary">
                        <fmt:message key="view.button.submit"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>