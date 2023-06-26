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
<c:set var="all_books" value="inactive"/>
<c:set var="book_genre" value="inactive"/>
<c:set var="add_books" value="inactive"/>
<c:set var="issue_return_books" value="inactive"/>
<c:set var="issued_books" value="inactive"/>

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
    <c:when test="${currentPath.contains('all_books_in_library.jsp')}">
        <c:set var="all_books" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('add_book_genre.jsp')}">
        <c:set var="book_genre" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('add_books.jsp')}">
        <c:set var="add_books" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('issue_return_books.jsp')}">
        <c:set var="issue_return_books" value="active"/>
    </c:when>
    <c:when test="${currentPath.contains('all_issued_books.jsp')}">
        <c:set var="issued_books" value="active"/>
    </c:when>
</c:choose>

<nav>
    <ul id="adminList">
        <li>
            <a class="${dashboard}" href="${pageContext.request.contextPath}/controller?command=go_to_dashboard">
                <fmt:message key="admin.dashboard.dashboard"/>
            </a>
        </li>
        <li>
            <a class="${administrator}" href="${pageContext.request.contextPath}/controller?command=go_to_administrators">
                <fmt:message key="admin.dashboard.admins"/>
            </a>
        </li>
        <li>
            <a class="${librarian}" href="${pageContext.request.contextPath}/controller?command=go_to_librarians">
                <fmt:message key="admin.dashboard.librarians"/>
            </a>
        </li>
        <li>
            <a class="${waiting_students}" href="${pageContext.request.contextPath}/controller?command=go_to_all_waiting_students">
                <fmt:message key="admin.dashboard.all_waiting_students"/>
            </a>
        </li>
        <li>
            <a class="${approved_students}" href="${pageContext.request.contextPath}/controller?command=go_to_all_approved_students">
                <fmt:message key="admin.dashboard.all_approved_students"/>
            </a>
        </li>
        <li>
            <a class="${all_books}" href="${pageContext.request.contextPath}/controller?command=go_to_all_books">
                <fmt:message key="admin.dashboard.all_books"/></a>
        </li>
        <li>
            <a class="${book_genre}" href="${pageContext.request.contextPath}/controller?command=go_to_add_book_genre">
                <fmt:message key="admin.dashboard.add_book_genre"/>
            </a>
        </li>
        <li>
            <a class="${add_books}" href="${pageContext.request.contextPath}/controller?command=go_to_add_books">
                <fmt:message key="admin.dashboard.add_books"/>
            </a>
        </li>
        <li>
            <a class="${issue_return_books}" href="${pageContext.request.contextPath}/controller?command=go_to_issue_return_books">
                <fmt:message key="admin.dashboard.issue_return_books"/>
            </a>
        </li>
        <li>
            <a class="${issued_books}" href="${pageContext.request.contextPath}/controller?command=go_to_issued_books">
                <fmt:message key="admin.dashboard.view_all_issued_books"/>
            </a>
        </li>
    </ul>
</nav>




