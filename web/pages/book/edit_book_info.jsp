<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<c:if test="${not empty message}">
    <jsp:include page="/pages/modal/modal.jsp"/>
</c:if>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="title"/></title>
    <link rel="stylesheet" href="/css/main/admin.css">
    <link rel="stylesheet" href="/css/main/add_book.css">
</head>
<body>

<main class="main">
    <section>
        <div class="container">
            <div class="header">
                <h2><fmt:message key="book.title.edit_book"/></h2>
            </div>
            <form name="reg" method="post"
                  action="${pageContext.request.contextPath}/controller?command=edit_book_info">
                <input type="hidden" name="book_id" value="${requestScope.book.id}">
                <div class="form-group">
                    <input type="text" name="title" value="${requestScope.book.title}" placeholder=
                    <fmt:message key="book.title.placeholder"/> required>
                </div>
                <div class="form-group">
                    <input type="text" name="author" value="${requestScope.book.author}" placeholder=
                    <fmt:message key="book.author.placeholder"/> required>
                </div>
                <div class="form-group">
                    <select name="genre_id" required>
                        <option value="">Select Genre</option>
                        <c:forEach var="genre" items="${requestScope.genres}">
                            <option value="${genre.id}" name="${genre.id}"
                                    <c:if test="${genre.id eq book.genre.id}">
                                        selected
                                    </c:if>
                            >${genre.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <input type="text" name="description" value="${requestScope.book.description}"
                              placeholder="<fmt:message key='book.description.placeholder'/>">
                </div>
                <div class="form-group">
                    <input type="number" name="book_copies" value="${requestScope.book.bookCopies}" placeholder=
                    <fmt:message key="book.book_copies.placeholder"/> required>
                </div>
                <div class="form-actions">
                    <button type="submit"><fmt:message key="genre.edit"></fmt:message></button>
                </div>
            </form>
        </div>

    </section>
</main>
</body>
</html>
