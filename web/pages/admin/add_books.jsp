<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="title"/></title>
    <link rel="stylesheet" href="/css/main/admin.css">
    <link rel="stylesheet" href="/css/main/add_book.css">
</head>
<body>
<jsp:include page="/pages/header.jsp"/>

<aside>
    <jsp:include page="/pages/nav.jsp"/>
</aside>

<main class="main">
    <section>
        <div class="container">
            <div class="header">
                <h2><fmt:message key="book.title.add_book"/></h2>
            </div>
            <form name="reg" method="post" action="${pageContext.request.contextPath}/controller?command=add_book">
                <div class="form-group">
                    <input type="text" name="title" placeholder=
                    <fmt:message key="book.title.placeholder"/> required>
                </div>
                <div class="form-group">
                    <input type="text" name="author" placeholder=
                    <fmt:message key="book.author.placeholder"/> required>
                </div>
                <div class="form-group">
                    <select name="genre_id" required>
                        <option value="">Select Genre</option>
                        <c:forEach var="genre" items="${requestScope.genres}">
                            <option value="${genre.id}" name="${genre.id}">${genre.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <textarea name="description" placeholder="<fmt:message key='book.description.placeholder'/>"
                              required rows="4"></textarea>
                </div>
                <div class="form-group">
                    <input type="number" name="book_copies" placeholder=
                    <fmt:message key="book.book_copies.placeholder"/> required>
                </div>
                <div class="form-group">
                    <input type="text" name="image_url" placeholder=
                    <fmt:message key="book.image_url.placeholder"/> required>
                </div>

                <div class="form-actions">
                    <button type="submit"> Add</button>
                </div>
            </form>
        </div>

    </section>
</main>
</body>
</html>
