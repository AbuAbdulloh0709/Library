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
    <link rel="stylesheet" href="/css/main/all_books.css">

</head>
<body style="padding: 0px !important;">
<jsp:include page="/pages/header.jsp"/>

<aside>
    <jsp:include page="/pages/nav.jsp"/>
</aside>

<main class="main">
    <section>
        <div>
            <script>
                function handleGenreChange() {
                    var genreSelect = document.getElementById("genre");
                    var selectedGenre = genreSelect.value;

                    if (selectedGenre !== "") {
                        window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_all_books&genre_id=" + selectedGenre;
                    } else {
                        window.location.href = "${pageContext.request.contextPath}/controller?command=go_to_all_books";
                    }
                }
            </script>
            <div id="search">
                <form id="searchForm" method="post"
                      action="${pageContext.request.contextPath}/controller?command=search_books">

                    <select class="select" id="genre" onchange="handleGenreChange()">
                        <option value="">All Genres</option>
                        <c:forEach var="genre" items="${requestScope.genres}">
                            <option value="${genre.id}" name="genre_id"
                                    <c:if test="${genre.id eq requestScope.genre_id}">
                                        selected
                                    </c:if>
                            >${genre.name}</option>
                        </c:forEach>
                    </select>

                    <input type="text" name="search_text" value="${requestScope.search_text}" placeholder=
                    <fmt:message
                            key="user.search.placeholder"/>>
                    <input type="button" name="clear" value="<fmt:message key="user.clear"/>"
                           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_all_books'">
                    <input type="submit" value="<fmt:message key="user.search"/>">
                </form>
            </div>
            <div>
                <c:if test="${fn:length(requestScope.books) > 0}">
                    <table>
                        <thead>
                        <tr>
                            <th><fmt:message key="book.title"></fmt:message></th>
                            <th><fmt:message key="book.author"></fmt:message></th>
                            <th><fmt:message key="book.genre"></fmt:message></th>
                            <th><fmt:message key="book.book_copies"></fmt:message></th>
                            <th><fmt:message key="book.available_book_copies"></fmt:message></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="book" items="${requestScope.books}">
                            <tr style="background-color: ${color}">
                                <td>${book.title}</td>
                                <td>${book.author}</td>
                                <td>${book.genre.name}</td>
                                <td>${book.bookCopies}</td>
                                <td>${book.bookAvailableCopies.availableCopies}</td>
                                <td>
                                    <div>
                                        <a href="${pageContext.request.contextPath}/controller?command=go_to_edit_book_info&book_id=${book.id}"
                                           class="edit-info-button"><fmt:message
                                                key="book.edit_book_details"></fmt:message></a>
                                    </div>
                                </td>
                                <td>
                                    <div>
                                        <a href="${pageContext.request.contextPath}/controller?command=go_to_replace_book_image&book_id=${book.id}"
                                           class="replace-book-image"><fmt:message
                                                key="book.replace_book_image"></fmt:message></a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="container">
                        <ctg:book_pages page="${page}" last="${last}" search_text="${search_text}" command="search_books"/>
                    </div>
                </c:if>
            </div>
        </div>
    </section>
    <style>
        .edit-info-button {
            padding: 8px 12px;
            background-color: #4caf50;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .edit-info-button:hover {
            background-color: #45a049;
        }

        .edit-info-button:focus {
            outline: none;
        }

        .replace-book-image {
            padding: 8px 12px;
            background-color: #d26821;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .replace-book-image:hover {
            background-color: #d26818;
        }

        .replace-book-image:focus {
            outline: none;
        }
    </style>
</main>
</body>
</html>
