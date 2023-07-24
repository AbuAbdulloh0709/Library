<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<c:if test="${not empty message}">
    <jsp:include page="/pages/modal/modal.jsp"/>
</c:if>
<html>
<head>
    <link rel="stylesheet" href="/css/sign_up.css" type="text/css">
</head>
<c:set var="currentPath" value="${pageContext.request.requestURI}"/>
<body>
<div class="container">
    <div class="header">
        <h1><fmt:message key="sign_up.title"/></h1>
    </div>
    <form name="reg" method="post" action="${pageContext.request.contextPath}/controller?command=sign_up">
        <div class="form-group">
            <input type="text" name="firstname"
                   pattern="[A-Z][a-z]{1,20}"
                   title="<fmt:message key="sign_up.name.title"/>" placeholder=
                   <fmt:message key="sign_up.first_name.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="text" name="lastname"
                   pattern="[A-Z][a-z]{1,20}"
                   title = "<fmt:message key="sign_up.surname.title"/>" placeholder=
                   "<fmt:message key="sign_up.last_name.placeholder"/>" required>
        </div>
        <div class="form-group">
            <input type="text" name="passport_number"
                   pattern="^[A-Z]{2}\d{7}$"
                   title="<fmt:message key="sign_up.surname.title"/>"
                   placeholder="<fmt:message key="sign_up.passport.placeholder"/>">
        </div>
        <div class="form-group">
            <input type="tel" name="number"
                   pattern="\+998\(\d{2}\)\d{3}-\d{2}-\d{2}"
                   title="<fmt:message key="sign_up.number.placeholder"/>: +998(xx)xxx-xx-xx"
                   placeholder=
                           "<fmt:message key="sign_up.number.placeholder"/>" required>
        </div>
        <div class="form-group">
            <input type="email" name="email"
                   pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})"
                   title="<fmt:message key="sign_up.email.title"/>: *****@***.**"
                   placeholder=
                   "<fmt:message key="sign_up.email.placeholder"/>" required>
        </div>
        <div class="form-group">
            <input type="text" name="address" placeholder=
            "<fmt:message key="sign_up.address.placeholder"/>" required>
        </div>
        <div class="form-group">
            <input type="date" name="birth_date" required>
        </div>
        <div class="form-group">
            <input type="text" name="login"
                   pattern="[a-zA-Z][A-Za-z0-9]{4,29}"
                   title="<fmt:message key="sign_in.login.title"/>" placeholder=
                   "<fmt:message key="sign_in.login.placeholder"/>" required>
        </div>
        <div class="form-group">
            <input type="password" name="password"
                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                   title="<fmt:message key="sign_in.password.title"/>" placeholder=
                   "<fmt:message key="sign_in.password.placeholder"/>" required>
        </div>
        <div class="form-group">
            <input type="password" name="repeated_password"
                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                   title="<fmt:message key="sign_in.password.title"/>" placeholder=
                   "<fmt:message key="sign_up.repeat_password.placeholder"/>" required>
        </div>
        <div class="form-actions">
            <button type="submit"><fmt:message key="sign_up.submit"></fmt:message></button>
        </div>
    </form>
</div>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/sign_up.js"></script>
<script src="${pageContext.request.contextPath}/script/incorrect_input.js"></script>
</body>
</html>
