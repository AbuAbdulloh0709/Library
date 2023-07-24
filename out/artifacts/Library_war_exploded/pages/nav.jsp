<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<c:set var="currentPath" value="${pageContext.request.requestURI}"/>
<c:set var="dashboard" value="inactive"/>
<c:set var="administrator" value="inactive"/>
<c:set var="librarian" value="inactive"/>
<c:set var="waiting_students" value="inactive"/>
<c:set var="approved_students" value="inactive"/>
<c:set var="all_students" value="inactive"/>
<c:set var="all_books" value="inactive"/>
<c:set var="book_genre" value="inactive"/>
<c:set var="add_books" value="inactive"/>
<c:set var="requested_books" value="inactive"/>
<c:set var="issued_books" value="inactive"/>
<c:set var="books_to_issue" value="inactive"/>
<c:set var="history" value="inactive"/>
<c:set var="history_for_student" value="inactive"/>

<c:choose>
    <c:when test="${currentPath.contains('dashboard.jsp')}">
        <c:set var="dashboard" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('administrators.jsp')}">
        <c:set var="administrator" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('librarians.jsp')}">
        <c:set var="librarian" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('all_waiting_students.jsp')}">
        <c:set var="waiting_students" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('all_approved_students.jsp')}">
        <c:set var="approved_students" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('all_students.jsp')}">
        <c:set var="all_students" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('all_books_in_library.jsp')}">
        <c:set var="all_books" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('add_book_genre.jsp')}">
        <c:set var="book_genre" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('add_books.jsp')}">
        <c:set var="add_books" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('requested_books.jsp')}">
        <c:set var="requested_books" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('all_issued_books.jsp')}">
        <c:set var="issued_books" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('books_to_issue.jsp')}">
        <c:set var="books_to_issue" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('history.jsp')}">
        <c:set var="history" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('history_for_student.jsp')}">
        <c:set var="history_for_student" value="active"/>
    </c:when>
</c:choose>

<nav>
    <ul id="adminList">
        <c:if test="${sessionScope.role eq 'admin' or sessionScope.role eq 'librarian'}">
            <li>
                <a class="${dashboard}" href="${pageContext.request.contextPath}/controller?command=go_to_dashboard">
                    <fmt:message key="admin.dashboard.dashboard"/>
                </a>
            </li>
        </c:if>

        <c:if test="${sessionScope.role eq 'admin'}">
            <li>
                <a class="${administrator}"
                   href="${pageContext.request.contextPath}/controller?command=go_to_administrators">
                    <fmt:message key="admin.dashboard.admins"/>
                </a>
            </li>
            <li>
                <a class="${librarian}" href="${pageContext.request.contextPath}/controller?command=go_to_librarians">
                    <fmt:message key="admin.dashboard.librarians"/>
                </a>
            </li>
        </c:if>

        <c:if test="${sessionScope.role eq 'admin' or sessionScope.role eq 'librarian'}">
            <li>
                <a class="${all_students}"
                   href="${pageContext.request.contextPath}/controller?command=go_to_all_students">
                    <fmt:message key="admin.dashboard.all_students"/>
                </a>
            </li>
            <li>
                <a class="${all_books}" href="${pageContext.request.contextPath}/controller?command=go_to_all_books">
                    <fmt:message key="admin.dashboard.all_books"/></a>
            </li>
            <li>
                <a class="${book_genre}"
                   href="${pageContext.request.contextPath}/controller?command=go_to_add_book_genre">
                    <fmt:message key="admin.dashboard.add_book_genre"/>
                </a>
            </li>
            <li>
                <a class="${add_books}" href="${pageContext.request.contextPath}/controller?command=go_to_add_books">
                    <fmt:message key="admin.dashboard.add_books"/>
                </a>
            </li>
            <li>
                <a class="${requested_books}"
                   href="${pageContext.request.contextPath}/controller?command=go_to_requests">
                    <fmt:message key="admin.dashboard.requested_books"/>
                </a>
            </li>
            <li>
                <a class="${issued_books}"
                   href="${pageContext.request.contextPath}/controller?command=go_to_issued_books">
                    <fmt:message key="admin.dashboard.view_all_issued_books"/>
                </a>
            </li>
        </c:if>

        <li>
            <a class="${books_to_issue}"
               href="${pageContext.request.contextPath}/controller?command=go_to_books_to_issue">
                <fmt:message key="admin.dashboard.books_to_issue"/>
            </a>
        </li>

        <c:if test="${sessionScope.role eq 'admin' or sessionScope.role eq 'librarian'}">
            <li>
                <a class="${history}" href="${pageContext.request.contextPath}/controller?command=go_to_history">
                    <fmt:message key="admin.dashboard.history"/>
                </a>
            </li>
        </c:if>

        <c:if test="${sessionScope.role eq 'student'}">
            <li>
                <a class="${history_for_student}"
                   href="${pageContext.request.contextPath}/controller?command=go_to_history_for_student">
                    <fmt:message key="admin.dashboard.history_for_student"/>
                </a>
            </li>
        </c:if>

    </ul>
</nav>




