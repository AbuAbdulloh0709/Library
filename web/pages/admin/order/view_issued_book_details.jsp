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
    <link rel="stylesheet" href="/css/main/issue_book.css">
</head>
<body>
<div class="container">
    <h1><fmt:message key="issue.issue_book"></fmt:message></h1>

        <div class="book-info">
            <input type="hidden" name="book_id" value="${requestScope.book.id}" required>
            <input type="hidden" name="student_id" value="${requestScope.student.id}">
            <img class="book-image" src="${requestScope.book.images.get(0).url}"
                 alt="Book Image">
            <div class="book-details">
                <p class="label"><fmt:message key="issue.book_title"></fmt:message> :</p>
                <p>${requestScope.book.title}</p>
                <p class="label"><fmt:message key="issue.book_author"></fmt:message> :</p>
                <p>${requestScope.book.author}</p>
                <p class="label"><fmt:message key="issue.book_genre"></fmt:message> :</p>
                <p>${requestScope.book.genre.name}</p>
                <p class="label"><fmt:message key="issue.book_description"></fmt:message> :</p>
                <p>${requestScope.book.description}</p>
                <p class="label"><fmt:message key="issue.return_date"></fmt:message> :</p>
                <p>${requestScope.book.description}</p>
            </div>
        </div>
</div>
</body>
</html>