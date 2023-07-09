<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<c:if test="${not empty message}">
    <jsp:include page="/pages/admin/modal/modal.jsp"/>
</c:if>
<html>
<head>
    <link rel="stylesheet" href="css/sign_up.css" type="text/css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1><fmt:message key="sign_in.title"/></h1>
    </div>
    <form name="reg" method="post" action="${pageContext.request.contextPath}/controller?command=sign_in">
        <div class="form-group">
            <input value="${requestScope.user_login}" type="text" name="login" placeholder=
            <fmt:message key="sign_in.login.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="password" name="password" placeholder=
            <fmt:message key="sign_in.password.placeholder"/> required>
        </div>
        <div class="form-actions">
            <button type="submit">Sign In</button>
        </div>
    </form>
</div>
</body>
</html>
