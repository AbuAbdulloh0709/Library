<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css_new/main_page/home.css" type="text/css">
</head>
<body>
<header>
    <div class="container">
        <div class="header-content">
            <div class="logo">My Website</div>
            <nav>
                <ul class="menu">
                    <li><a href="${pageContext.request.contextPath}/controller?command=go_to_sign_in">
                        <fmt:message key="header.sign_in"/>
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/controller?command=go_to_sign_up">
                        <fmt:message key="header.sign_up"/> </a></li>
                </ul>
            </nav>
        </div>
    </div>
</header>

<div class="main-content">
    <h1>Welcome to My Website</h1>
    <p>Explore and discover amazing books!</p>
</div>
</body>
</html>

