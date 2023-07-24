<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="custom_tags" %>
<html>
<head>
    <title><fmt:message key="title"></fmt:message></title>
    <link rel="stylesheet" href="/css/main/issue_details.css">
</head>
<body>
<h1><fmt:message key="issue.details"></fmt:message></h1>

<div class="section">
    <h2><fmt:message key="issue.info"></fmt:message></h2>
    <table>
        <tr>
            <th><fmt:message key="issue.id"></fmt:message></th>
            <th><fmt:message key="issue.student"></fmt:message></th>
            <th><fmt:message key="issue.book"></fmt:message></th>
            <th><fmt:message key="issue.status"></fmt:message></th>
            <th><fmt:message key="issue.issue_type"></fmt:message></th>
            <th><fmt:message key="issue.issue_date"></fmt:message></th>
            <th><fmt:message key="issue.return_date"></fmt:message></th>
        </tr>
        <tr>
            <td>${requestScope.order.id}</td>
            <td>${requestScope.order.user.fullName}</td>
            <td>${requestScope.order.book.title}</td>
            <td>${requestScope.order.lastStatus.status}</td>
            <td>${requestScope.order.orderType.orderType}</td>
            <td>${requestScope.order.issueDate}</td>
            <td>${requestScope.order.returnDate}</td>
        </tr>
    </table>
</div>

<div class="section">
    <h2><fmt:message key="issue.book.details"></fmt:message></h2>
    <table>
        <tr>
            <th><fmt:message key="issue.book.id"></fmt:message></th>
            <th><fmt:message key="issue.book.title"></fmt:message></th>
            <th><fmt:message key="issue.book.author"></fmt:message></th>
            <th><fmt:message key="issue.book.genre"></fmt:message></th>
            <th><fmt:message key="issue.book.image"></fmt:message></th>
        </tr>
        <tr>
            <td>${requestScope.order.book.id}</td>
            <td>${requestScope.order.book.title}</td>
            <td>${requestScope.order.book.author}</td>
            <td>${requestScope.order.book.genre.name}</td>
            <td><img class="book-image" src="${requestScope.order.book.images.get(0).url}" alt="Book Image"></td>
        </tr>
    </table>
</div>

<div class="section">
    <h2><fmt:message key="issue.student.details"></fmt:message></h2>
    <table>
        <tr>
            <th><fmt:message key="issue.student.id"></fmt:message></th>
            <th><fmt:message key="issue.student.fl_name"></fmt:message></th>
            <th><fmt:message key="issue.student.email"></fmt:message></th>
        </tr>
        <tr>
            <td>${requestScope.order.user.id}</td>
            <td>${requestScope.order.user.fullName}</td>
            <td>${requestScope.order.user.email}</td>
        </tr>
    </table>
</div>

<div class="section">
    <h2><fmt:message key="issue.detail"></fmt:message></h2>
    <table>
        <tr>
            <th><fmt:message key="issue.detail.user"></fmt:message></th>
            <th><fmt:message key="issue.detail.user.role"></fmt:message></th>
            <th><fmt:message key="issue.detail.status"></fmt:message></th>
            <th><fmt:message key="issue.detail.comment"></fmt:message></th>
            <th><fmt:message key="issue.detail.created_at"></fmt:message></th>
        </tr>
        <c:forEach var="order_detail" items="${requestScope.order.orderDetails}">
            <tr>
                <td>${order_detail.user.fullName}</td>
                <td>${order_detail.user.role.role}</td>
                <td>${order_detail.orderDetailStatus.status}</td>
                <td>${order_detail.comment}</td>
                <td>${order_detail.createdAt}</td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>