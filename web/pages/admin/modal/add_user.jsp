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
                            <input type="text" name="firstname"
                                   pattern="[A-Z][a-z]{1,20}"
                                   title="<fmt:message key="sign_up.name.title"/>" placeholder=
                                   <fmt:message key="sign_up.first_name.placeholder"/> required>
                        </div>
                        <div class="form-group">
                            <input type="text" name="lastname"
                                   pattern="[A-Z][a-z]{1,20}"
                                   title = "<fmt:message key="sign_up.surname.title"/>" placeholder=
                                           "<fmt:message key="sign_up.last_name.placeholder"/>" required>
                        </div>
                        <div class="form-group">
                            <input type="text" name="passport_number"
                                   pattern="^[A-Z]{2}\d{7}$"
                                   title="<fmt:message key="sign_up.surname.title"/>"
                                   placeholder="<fmt:message key="sign_up.passport.placeholder"/>">
                        </div>
                        <div class="form-group">
                            <input type="tel" name="number"
                                   pattern="\+998\(\d{2}\)\d{3}-\d{2}-\d{2}"
                                   title="<fmt:message key="sign_up.number.placeholder"/>: +998(xx)xxx-xx-xx"
                                   placeholder=
                                           "<fmt:message key="sign_up.number.placeholder"/>" required>
                        </div>
                        <div class="form-group">
                            <input type="email" name="email"
                                   pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})"
                                   title="<fmt:message key="sign_up.email.title"/>: *****@***.**"
                                   placeholder=
                                           "<fmt:message key="sign_up.email.placeholder"/>" required>
                        </div>
                        <div class="form-group">
                            <input type="text" name="address" placeholder=
                                    "<fmt:message key="sign_up.address.placeholder"/>" required>
                        </div>
                        <div class="form-group">
                            <input type="date" name="birth_date" required>
                        </div>
                        <div class="form-group">
                            <input type="text" name="login"
                                   pattern="[a-zA-Z][A-Za-z0-9]{4,29}"
                                   title="<fmt:message key="sign_in.login.title"/>" placeholder=
                                           "<fmt:message key="sign_in.login.placeholder"/>" required>
                        </div>
                        <div class="form-group">
                            <input type="password" name="password"
                                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                                   title="<fmt:message key="sign_in.password.title"/>" placeholder=
                                           "<fmt:message key="sign_in.password.placeholder"/>" required>
                        </div>
                        <div class="form-group">
                            <input type="password" name="repeated_password"
                                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                                   title="<fmt:message key="sign_in.password.title"/>" placeholder=
                                           "<fmt:message key="sign_up.repeat_password.placeholder"/>" required>
                        </div>
                        <c:set var="currentPath" value="${pageContext.request.requestURI}"/>
                        <div class="form-actions">
                            <button type="submit">
                                <c:choose>
                                    <c:when test="${currentPath.contains('administrators.jsp')}">
                                        <fmt:message key="admin.dashboard.add_new_administrator"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="admin.dashboard.add_new_librarian"/>
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
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/sign_up.js"></script>
<script src="${pageContext.request.contextPath}/script/incorrect_input.js"></script>
</body>
</html>
