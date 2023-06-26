<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<c:set var="currentPath" value="${pageContext.request.requestURI}"/>
<c:set var="add_user"/>

<c:choose>
    <c:when test="${currentPath.contains('administrators.jsp')}">
        <c:set var="add_user" value="${pageContext.request.contextPath}/controller?command=add_administrator"/>
    </c:when>
    <c:when test="${currentPath.contains('librarians.jsp')}">
        <c:set var="add_user" value="${pageContext.request.contextPath}/controller?command=add_librarian"/>
    </c:when>
    <c:when test="${currentPath.contains('all_waiting_students.jsp')}">
        <c:set var="add_user" value="${pageContext.request.contextPath}/controller?command=add_student"/>
    </c:when>
</c:choose>


<html>
<head>

    <link rel="stylesheet" href="css/sign_up.css" type="text/css">
    <link rel="stylesheet" href="/css/add_user_modal.css" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">



</head>
<body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>

<script src="https://code.jquery.com/jquery-3.6.1.min.js"
        integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>

<div class="main">
    <div class="modal fade" data-bs-backdrop="static" data-bs-keyboard="false" id="addUserModal" tabindex="-1"
         aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel"><fmt:message key="sign_in.registration"/></h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form name="reg" method="post"
                          action="${add_user}">
                        <div class="form-group">
                            <input type="text" name="firstname" placeholder=
                            <fmt:message key="sign_up.first_name.placeholder"/> required>
                        </div>
                        <div class="form-group">
                            <input type="text" name="lastname" placeholder=
                            <fmt:message key="sign_up.last_name.placeholder"/> required>
                        </div>
                        <div class="form-group">
                            <input type="text" name="passport_number" placeholder=
                            <fmt:message key="sign_up.passport.placeholder"/> required>
                        </div>
                        <div class="form-group">
                            <input type="tel" name="number" placeholder=
                            <fmt:message key="sign_up.number.placeholder"/> required>
                        </div>
                        <div class="form-group">
                            <input type="email" name="email" placeholder=
                            <fmt:message key="sign_up.email.placeholder"/> required>
                        </div>
                        <div class="form-group">
                            <input type="date" name="birth_date" required>
                        </div>
                        <div class="form-group">
                            <input type="text" name="login" placeholder=
                            <fmt:message key="sign_in.login.placeholder"/> required>
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" placeholder=
                            <fmt:message key="sign_in.password.placeholder"/> required>
                        </div>
                        <div class="form-group">
                            <input type="password" name="repeated_password" placeholder=
                            <fmt:message key="sign_up.repeat_password.placeholder"/> required>
                        </div>
                        <c:set var="currentPath" value="${pageContext.request.requestURI}"/>
                        <div class="form-actions">
                            <button type="submit">
                                <c:choose>
                                    <c:when test="${currentPath.contains('administrators.jsp')}">
                                        Add New Administrator
                                    </c:when>
                                    <c:otherwise>
                                        Add New Librarian
                                    </c:otherwise>
                                </c:choose>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
