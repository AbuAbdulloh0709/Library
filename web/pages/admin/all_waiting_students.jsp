<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="custom_tags" %>
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
                            data-bs-target="#addUserModal" data-bs-whatever="@mdo"><fmt:message
                            key="admin.dashboard.add_new_librarian"/></button>
                    <%--    <button type="button" class="btn btn-primary" >Edit--%>
                    <%--    </button>--%>
                </div>
                <div>
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Firstname</th>
                            <th>Lastname</th>
                            <th>Phone Number</th>
                            <th>Passport</th>
                            <th>Birth Date</th>
                            <%--                            <th>Address</th>--%>
                            <%--                    <th>Edit</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="student" items="${requestScope.waiting_students}">
                            <tr>
                                <td>${student.id}</td>
                                <td>${student.firstName}</td>
                                <td>${student.lastName}</td>
                                <td>${student.phoneNumber}</td>
                                <td>${student.passportNumber}</td>
                                <td>${student.birthDate}</td>
                                    <%--                                <td>${student.address}</td>--%>
                                    <%--                        <td>--%>
                                    <%--                            <a href="#" class="edit-button">Edit</a>--%>
                                    <%--                        </td>--%>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <ctg:pages page="${page}" last="${last}" command="go_to_all_waiting_students"/>
                </div>
            </div>
        </section>


    </div>
    </div>
</div>
<jsp:include page="/pages/admin/modal/add_user.jsp"/>
</body>
</html>
