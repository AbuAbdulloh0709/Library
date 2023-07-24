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

    <form method="post" action="${pageContext.request.contextPath}/controller?command=issue_book_by_student">
        <div class="book-info">
            <input type="hidden" name="book_id" value="${requestScope.book.id}">
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
            </div>
        </div>

        <div class="issue-return">
            <div class="issue-date">
                <p class="date-label"><fmt:message key="issue.issue_date"></fmt:message>:</p>
                <input class="date-input" type="date" name="issue_date" pattern="\d{4}-\d{2}-\d{2}"
                       title="Please enter a date in the format YYYY-MM-DD">
            </div>

            <div class="return-date">
                <p class="date-label"><fmt:message key="issue.return_date"></fmt:message>:</p>
                <input class="date-input" type="date" name="return_date" pattern="\d{4}-\d{2}-\d{2}"
                       title="Please enter a date in the format YYYY-MM-DD">
            </div>
        </div>

        <div class="button-container">
            <input class="button" type="submit" value="<fmt:message key="issue.issue_book"></fmt:message>:"></input>
        </div>
    </form>

</div>
</body>
</html>

