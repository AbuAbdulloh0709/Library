<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="css_new/sign_up.css" type="text/css">
</head>
<%
    String role = (String) request.getSession().getAttribute("role");
    String message = (String) request.getSession().getAttribute("message");
    HashMap<String, String> userData = (HashMap<String, String>) request.getSession().getAttribute("user");
    out.println(message);
    out.println(role);
%>
<body>
<div class="container">
    <div class="header">
        <h1><fmt:message key="sign_in.title"/></h1>
    </div>
    <form name="reg" method="post" action="${pageContext.request.contextPath}/controller?command=sign_in">
        <div class="form-group">
            <input type="text" name="login" placeholder=<fmt:message key="sign_in.login.placeholder"/> required>
        </div>
        <div class="form-group">
            <input type="password" name="password" placeholder=<fmt:message key="sign_in.password.placeholder"/> required>
        </div>
        <div class="form-actions">
            <button type="submit">Sign In</button>
        </div>
    </form>
</div>
</body>
</html>