<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Library Management Dashboard</title>
  <link rel="stylesheet" href="/css_new/main_page/admin.css">
</head>
<body>
<header>
  <h1>Library Management Dashboard</h1>
</header>

<aside>
  <nav>
    <ul>
      <li><a href="#" class="active">Dashboard</a></li>
      <li><a href="#">Books</a></li>
      <li><a href="#">Users</a></li>
      <li><a href="#">Transactions</a></li>
      <li><a href="#">Reports</a></li>
      <li><a href="#">Settings</a></li>
    </ul>
  </nav>
</aside>

<main>
  <section>
    <h2>Welcome, Admin!</h2>
    <p>This is the main content of the library management dashboard.</p>
  </section>
</main>
</body>
</html>


