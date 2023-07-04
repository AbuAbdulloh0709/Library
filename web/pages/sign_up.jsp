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
        <h1><fmt:message key="sign_up.title"/></h1>
    </div>
    <form name="reg" method="post" action="${pageContext.request.contextPath}/controller?command=sign_up">
        <div class="form-group">
            <input type="text" name="firstname" placeholder=
            <fmt:message key="sign_up.first_name.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="text" name="lastname" placeholder=
            <fmt:message key="sign_up.last_name.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="text" name="passport_number" placeholder=
            <fmt:message key="sign_up.passport.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="tel" name="number" placeholder=
            <fmt:message key="sign_up.number.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="email" name="email" placeholder=
            <fmt:message key="sign_up.email.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="date" name="birth_date" required>
        </div>
        <div class="form-group">
            <input type="text" name="login" placeholder=
            <fmt:message key="sign_in.login.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="password" name="password" placeholder=
            <fmt:message key="sign_in.password.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="password" name="repeated_password" placeholder=
            <fmt:message key="sign_up.repeat_password.placeholder"/> required>
        </div>
        <div class="form-actions">
            <button type="submit">Register</button>
        </div>
    </form>
</div>
</body>
</html>
