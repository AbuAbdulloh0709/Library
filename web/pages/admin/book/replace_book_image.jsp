<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<c:if test="${not empty message}">
    <jsp:include page="/pages/admin/modal/modal.jsp"/>
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
            <form name="reg" method="post" enctype="multipart/form-data"
                  action="${pageContext.request.contextPath}/controller?command=replace_book_image">
                <input type="hidden" name="book_id" value="${requestScope.book.id}">
                <input type="hidden" name="image_path" value="${requestScope.book.images.get(0).url}">
                <div class="form-group">
                    <p type="text" name="title" value="${requestScope.book.title}" placeholder=
                    <fmt:message key="book.title.placeholder"/> required></p>
                </div>
                <div class="form-group">
                    <input type="file" name="book_image" placeholder=
                    <fmt:message key="book.image_url.placeholder"/> required>
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
