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
    <link rel="stylesheet" href="/css/main/show_book.css">
</head>
<body>
<div class="header">
    <h2 style="font-size: 22px">Here You Can Browse All Book In The Library</h2>
    <script>
        function handleGenreChange() {
            var genreSelect = document.getElementById("genre");
            var selectedGenre = genreSelect.value;

            if (selectedGenre !== "") {
                window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_show_books_for_guest&genre_id=" + selectedGenre;
            } else {
                window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_show_books_for_guest";
            }
        }
    </script>
    <div class="dropdown">
        <select id="genre" onchange="handleGenreChange()">
            <option value="">All Genres</option>
            <c:forEach var="genre" items="${requestScope.genres}">
                <option value="${genre.id}" name="${genre.id}"
                        <c:if test="${genre.id eq requestScope.genre_id}">
                            selected
                        </c:if>
                >${genre.name}</option>
            </c:forEach>
        </select>
    </div>
</div>

<div class="container">
    <c:if test="${fn:length(requestScope.books) > 0}">
        <c:forEach var="book" items="${requestScope.books}">
            <div class="book">
                <img src="${book.images.get(0).url}" alt="${book.title}"/>
                <h3>${book.title}
                </h3>
                <p>Author: ${book.author}
                </p>
                <p>Genre: ${book.genre.name}
                </p>
                <a href="${pageContext.request.contextPath}/controller?command=go_to_sign_in" class="borrow-button">Borrow</a>
            </div>
        </c:forEach>
    </c:if>
</div>
<div class="container">
    <ctg:book_pages page="${page}" last="${last}" command="go_to_show_books_for_guest"/>
</div>
</body>
</html>


