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
                <form action="/sign-in" method="post">
                    <div class="form-group ${ERROR_MAP['email'] != null ? 'has-error has-feedback' : ''}">
                        <label for="email"><fmt:message key="view.sign.in.email"/> </label>
                        <input type="text" class="form-control" id="email" name="email">
                        <c:if test="${ERROR_MAP['email'] != null}">
                            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                            <div class="label label-danger"><fmt:message key="${ERROR_MAP['email']}"/></div>
                        </c:if>
                    </div>
                    <div class="form-group ${ERROR_MAP['password'] != null ? 'has-error has-feedback' : ''}">
                        <label for="password"><fmt:message key="view.sign.in.password"/></label>
                        <input type="password" class="form-control" id="password" name="password">
                        <c:if test="${ERROR_MAP['password'] != null}">
                            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                            <div class="label label-danger"><fmt:message key="${ERROR_MAP['password']}"/></div>
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