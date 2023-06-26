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
</head>
<body>

<jsp:include page="/pages/header.jsp"/>

<aside>
    <jsp:include page="/pages/nav.jsp"/>
</aside>

<div>
    <div class="main">
        <section>
            <div>
                <div>
                    <button class="add-button" type="button" data-bs-toggle="modal"
                            data-bs-target="#addUserModal" data-bs-whatever="@mdo"><fmt:message key="admin.dashboard.add_new_administrator"/> </button>
<%--    <button type="button" class="btn btn-primary" >Edit--%>
<%--    </button>--%>
                </div>
                <div>
                    <table>
                        <thead>
                        <tr>
                            <th>Firstname</th>
                            <th>Lastname</th>
                            <th>Phone Number</th>
                            <th>Passport</th>
                            <th>Birth Date</th>
                            <th>Address</th>
                            <%--                    <th>Edit</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="admin" items="${requestScope.administrators}">
                            <tr>
                                <td>${admin.firstName}</td>
                                <td>${admin.lastName}</td>
                                <td>${admin.phoneNumber}</td>
                                <td>${admin.passportNumber}</td>
                                <td>${admin.birthDate}</td>
                                <td>${admin.address}</td>
                                    <%--                        <td>--%>
                                    <%--                            <a href="#" class="edit-button">Edit</a>--%>
                                    <%--                        </td>--%>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </section>
    </div>
</div>
<jsp:include page="/pages/admin/modal/add_user.jsp"/>
</body>
</html>
