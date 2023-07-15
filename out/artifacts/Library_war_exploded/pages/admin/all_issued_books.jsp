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
                      action="${pageContext.request.contextPath}/controller?command=search_issued_books">
                    <div id="search">
                        <div id="issue_type">
                            <label>
                                <input type="radio" name="issue_type" value="in_room"
                                       <c:if test="${requestScope.issue_type eq 'in_room'}">checked</c:if>
                                >
                                <fmt:message key="issue.type.in_room"/>
                            </label>
                            <label>
                                <input type="radio" name="issue_type" value="borrow"
                                       <c:if test="${requestScope.issue_type eq 'borrow'}">checked</c:if>
                                >
                                <fmt:message key="issue.type.borrow"/>
                            </label>

                            <input type="text" name="search_text" value="${requestScope.search_text}" placeholder=
                            <fmt:message
                                    key="user.search.placeholder"/>>
                        </div>
                        <input type="button" name="clear" value="<fmt:message key="user.clear"/>"
                               onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_issued_books'">
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
                                            <a href="${pageContext.request.contextPath}/controller?command=return_issued_book&id=${order.id}&page=${page}&issue_type=${issue_type}&search_text=${search_text}"
                                               class="return-button">Return</a>
                                        </div>
                                    </td>
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
                        <ctg:issued_books page="${page}" last="${last}"
                                   search_text="${requestScope.search_text}" issue_type="${requestScope.issue_type}"
                                   command="search_issued_books"/>
                    </c:if>
                </div>
            </div>
        </section>


    </div>
</div>
</body>
</html>
