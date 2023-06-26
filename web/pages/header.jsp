<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<c:if test="${not empty message}">
    <jsp:include page="/pages/admin/modal/modal.jsp"/>
</c:if>
<header>
    <h1>Library Management Dashboard</h1>
</header>