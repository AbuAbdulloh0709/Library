<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="custom_tags" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="title"/></title>
    <link rel="stylesheet" href="/css/main/admin.css">
    <link rel="stylesheet" href="/css/main/issued_books.css">
</head>
<body>
<div>
    <jsp:include page="/pages/header.jsp"/>
</div>


<aside>
    <jsp:include page="/pages/nav.jsp"/>
</aside>

<div>
    <div class="main">
        <section>
            <div>
                <form id="searchForm" method="post"
                      action="${pageContext.request.contextPath}/controller?command=search_history_for_student">
                    <div id="search">
                        <div id="issue_type">

                            <div class="date">
                                <input class="date-input" type="date" name="from_date" value="${requestScope.from_date}"
                                       pattern="\d{4}-\d{2}-\d{2}"
                                       title="Please enter a date in the format YYYY-MM-DD" required>
                            </div>

                            <div class="date">
                                <input class="date-input" type="date" name="to_date" value="${requestScope.to_date}"
                                       pattern="\d{4}-\d{2}-\d{2}"
                                       title="Please enter a date in the format YYYY-MM-DD" required>
                            </div>
                            <style>
                                .date{
                                    margin: 5px;
                                }
                                .date-input {
                                    width: 200px;
                                    padding: 5px;
                                    border-radius: 4px;
                                    border: 1px solid #ccc;
                                }
                            </style>
                            <input type="text" name="search_text" value="${requestScope.search_text}" placeholder=
                            <fmt:message
                                    key="user.search.placeholder"/>>
                        </div>
                        <input type="button" name="clear" value="<fmt:message key="user.clear"/>"
                               onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_history_for_student'">
                        <input type="submit" value="<fmt:message key="user.search"/>">
                    </div>
                </form>
                <div>
                    <c:if test="${fn:length(requestScope.orders) > 0}">
                        <table>
                            <thead>
                            <tr>
                                <th><fmt:message key="issue.book"></fmt:message></th>
                                <th><fmt:message key="issue.student"></fmt:message></th>
                                <th><fmt:message key="issue.issue_type"></fmt:message></th>
                                <th><fmt:message key="issue.issue_date"></fmt:message></th>
                                <th><fmt:message key="issue.return_date"></fmt:message></th>
                                <th></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${requestScope.orders}">
                                <tr style="background-color: ${color}">
                                    <td>${order.book.title}</td>
                                    <td>${order.user.fullName}</td>
                                    <td>${order.orderType.orderType}</td>
                                    <td>${order.issueDate}</td>
                                    <td>${order.returnDate}</td>
                                    <td>
                                        <div>
                                            <a href="${pageContext.request.contextPath}/controller?command=view_issued_book_details&order_id=${order.id}"
                                               class="view-button">View</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
        </section>
    </div>
</div>
</body>
</html>
