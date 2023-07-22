<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="/css/sign_up.css" type="text/css">
</head>
<c:set var="currentPath" value="${pageContext.request.requestURI}" />
<body>
<div class="container">
    <div class="header">
        <h1><fmt:message key="profile.title"/></h1>
    </div>
    <form name="update" method="post" action="${pageContext.request.contextPath}/controller?command=update_user_details">
        <input type="hidden" name="id" value="${user.id}">
        <div class="form-group">
            <input type="text" name="firstname" value="${user.firstName}"
                   pattern="[A-Z][a-z]{1,20}"
                   title="<fmt:message key="sign_up.name.title"/>" placeholder=
                   <fmt:message key="sign_up.first_name.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="text" name="lastname"
                   pattern="[A-Z][a-z]{1,20}" value="${user.lastName}"
                   title = "<fmt:message key="sign_up.surname.title"/>" placeholder=
                           "<fmt:message key="sign_up.last_name.placeholder"/>" required>
        </div>
        <div class="form-group">
            <input type="text" name="passport_number"
                   pattern="^[A-Z]{2}\d{7}$" value="${user.passportNumber}"
                   title="<fmt:message key="sign_up.surname.title"/>"
                   placeholder="<fmt:message key="sign_up.passport.placeholder"/>">
        </div>
        <div class="form-group">
            <input type="tel" name="number" value="${user.phoneNumber}"
                   pattern="\+998\(\d{2}\)\d{3}-\d{2}-\d{2}"
                   title="<fmt:message key="sign_up.number.placeholder"/>: +998(xx)xxx-xx-xx"
                   placeholder=
                           "<fmt:message key="sign_up.number.placeholder"/>" required>
        </div>
        <div class="form-group">
            <input type="email" name="email" value="${user.email}"
                   pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})"
                   title="<fmt:message key="sign_up.email.title"/>: *****@***.**"
                   placeholder=
                           "<fmt:message key="sign_up.email.placeholder"/>" required>
        </div>
        <div class="form-group">
            <input type="text" name="address"
                   value="${user.address}" placeholder=
                    "<fmt:message key="sign_up.address.placeholder"/>" required>
        </div>
        <div class="form-group">
            <input type="date" value="${user.birthDate}" name="birth_date" required>
        </div>
        <div class="form-actions">
            <button type="submit"><fmt:message key="update"/> </button>
        </div>
    </form>
</div>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/sign_up.js"></script>
<script src="${pageContext.request.contextPath}/script/incorrect_input.js"></script>
</body>
</html>
