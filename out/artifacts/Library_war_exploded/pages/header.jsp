<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<c:if test="${not empty message}">
    <jsp:include page="/pages/modal/modal.jsp"/>
</c:if>
<header class="header-style">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <h1>Library Management</h1>
    <div class="header-buttons">
        <a href="${pageContext.request.contextPath}/controller?command=sign_out" class="logout-button">Logout</a>
        <a href="${pageContext.request.contextPath}/controller?command=go_to_profile" class="profile-button">
            <i class="fa fa-user-circle"></i>${sessionScope.user.fullName}
        </a>
    </div>
</header>
<style>
    .header-style {
        background-color: #35495e;
        color: #fff;
        text-align: center;
        display: flex;
        justify-content: space-between;
        align-items: center;
        /*position: fixed;*/
        /*padding: 20px;!important;*/
        /*width: 100%;*/
    }

    .header-buttons {
        display: flex;
        align-items: center;
    }

    .logout-button, .profile-button {
        background-color: transparent;
        color: #fff;
        border: none;
        cursor: pointer;
        font-size: 16px;
        display: flex;
        align-items: center;
        padding-right: 20px;
        text-decoration: none; /* Remove underlines */
    }

    .logout-button {
        margin-right: 10px;
    }

    .profile-button i {
        margin-right: 5px;
    }
</style>