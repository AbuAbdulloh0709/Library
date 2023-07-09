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
    <link rel="stylesheet" href="/css/main/show_book.css">
    <link rel="stylesheet" href="/css/main/students.css">

</head>
<body style="padding: 0px !important;">
<jsp:include page="/pages/header.jsp"/>

<aside>
    <jsp:include page="/pages/nav.jsp"/>
</aside>

<main class="main">
    <section>
        <script>
            function handleGenreChange() {
                var genreSelect = document.getElementById("genre");
                var selectedGenre = genreSelect.value;

                if (selectedGenre !== "") {
                    window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_books_to_issue&genre_id=" + selectedGenre;
                } else {
                    window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_books_to_issue";
                }
            }

            function handleBookClick(book_id) {
                if (${(sessionScope.role eq "admin") or (sessionScope.role eq "librarian")})
                    window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_issue_book&book_id=" + book_id;
                else
                    window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_issue_book_by_student&book_id=" + book_id;
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
            <input type="text" name="search_text" value="${requestScope.search_text}" placeholder=
            <fmt:message
                    key="user.search.placeholder"/>>
            <input type="submit" value="<fmt:message key="user.search"/>">
        </div>
        </div>

        <c:if test="${fn:length(requestScope.books) > 0}">
            <div class="container">

                <c:forEach var="book" items="${requestScope.books}">
                    <div class="book" id="book" onclick="handleBookClick(${book.id})">
                        <img src="${book.images.get(0).url}" alt="${book.title}"/>
                        <h3>${book.title}
                        </h3>
                        <p>Author: ${book.author}
                        </p>
                        <p>Genre: ${book.genre.name}
                        </p>
                    </div>
                </c:forEach>

            </div>
            <div class="container">
                <ctg:book_pages page="${page}" last="${last}" command="go_to_books_to_issue"/>
            </div>
        </c:if>
    </section>
</main>
</body>
</html>
