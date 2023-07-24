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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffffff; /* White background */
            color: #333333; /* Text color (dark grey) */
        }
        .dashboard {
            padding: 20px;
        }
        .section {
            margin-bottom: 30px;
        }
        .section-title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .card {
            background-color: #f0f0f0; /* Light grey card background */
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }
        .grid-item {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            background-color: #f9f9f9; /* Lighter grey grid item background */
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            font-size: 18px;
            color: #444444; /* Text color for grid items */
        }
        .icon {
            font-size: 36px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="/pages/header.jsp"/>

<aside>
    <jsp:include page="/pages/nav.jsp"/>
</aside>

<main class="main">
    <section>
    <div class="dashboard">
        <div class="section">
            <div class="section-title">Library Overview</div>
            <div class="grid">
                <div class="grid-item">
                    <div class="card">
                        <i class="icon fas fa-book"></i>
                        <div>Total Books</div>
                        <div><c:out value="${requestScope.book_count}"/></div>
                    </div>
                </div>
                <div class="grid-item">
                    <div class="card">
                        <i class="icon fas fa-user-graduate"></i>
                        <div>Total Students</div>
                        <div><c:out value="${requestScope.student_count}"/></div>
                    </div>
                </div>
                <div class="grid-item">
                    <div class="card">
                        <i class="icon fas fa-user"></i>
                        <div>Total Librarians</div>
                        <div><c:out value="${requestScope.librarian_count}"/></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="section">
            <div class="section-title">Today's Date</div>
            <div class="card">
                <i class="icon far fa-calendar-alt"></i>
                <div>Date: July 23, 2023</div> <!-- Placeholder for the current date -->
            </div>
        </div>
        <div class="section">
            <div class="section-title">Books Status</div>
            <div class="grid">
                <div class="grid-item">
                    <div class="card">
                        <i class="icon fas fa-book-open"></i>
                        <div>Issued Books</div>
                        <div>50</div> <!-- Placeholder for the number of issued books -->
                    </div>
                </div>
                <div class="grid-item">
                    <div class="card">
                        <i class="icon fas fa-undo-alt"></i>
                        <div>Returned Books</div>
                        <div>30</div> <!-- Placeholder for the number of returned books -->
                    </div>
                </div>
                <div class="grid-item">
                    <div class="card">
                        <i class="icon fas fa-check"></i>
                        <div>Available Books</div>
                        <div>920</div> <!-- Placeholder for the number of available books -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    </section>
</main>
</body>
</html>
