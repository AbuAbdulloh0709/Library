<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="title"/></title>
    <link rel="stylesheet" href="/css/main/admin.css">
    <link rel="stylesheet" href="/css/main/genre.css">
</head>
<body>
<jsp:include page="/pages/header.jsp"/>

<aside>
    <jsp:include page="/pages/nav.jsp"/>
</aside>

<main class="main">
    <div class="container">
        <div class="left-section">
            <div class="form-container">
                <h2>Book Genre</h2>
                <c:choose>
                    <c:when test="${empty requestScope.name}">
                        <form name="genre" method="post"
                              action="${pageContext.request.contextPath}/controller?command=add_book_genre">
                            <label>Genre Name:</label>
                            <input type="text" name="name" required>
                            <br><br>
                            <div class="form-actions">
                                <button type="submit"><fmt:message key="genre.add"/></button>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form name="genre" method="post"
                              action="${pageContext.request.contextPath}/controller?command=edit_book_genre">
                            <input type="hidden" name="id" value="<c:out value="${requestScope.id}"/>">
                            <label for="genre-name">Genre Name:</label>
                            <input type="text" id="genre-name" name="name"
                                   value="<c:out value="${requestScope.name}"/>" required>
                            <br><br>
                            <div class="form-actions">
                                <button type="submit"><fmt:message key="genre.edit"/></button>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>


            </div>
        </div>
        <div class="right-section">
            <h2>Genres</h2>
            <c:if test="${fn:length(requestScope.genres) > 0}">
                <table>
                    <thead>
                    <tr>
                        <th style="color: #2c3e50">Genre Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="genre" items="${requestScope.genres}">
                        <tr>
                            <td>${genre.name}</td>
                            <td>
                                <div>
                                    <a href="${pageContext.request.contextPath}/controller?command=go_to_add_book_genre&id=${genre.id}&name=${genre.name}"
                                       class="edit-button">Edit</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

        </div>
    </div>
</main>
</body>
</html>
