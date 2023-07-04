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
    <link rel="stylesheet" href="/css/main/students.css">
</head>
<body>
<div>
    <jsp:include page="/pages/header.jsp"/>
</div>


<aside>
    <jsp:include page="/pages/nav.jsp"/>
</aside>

<div>
    <div class="main">
        <section>
            <div>
                <form id="searchForm" method="post"
                      action="${pageContext.request.contextPath}/controller?command=search_students">
                    <div id="search">
                        <div id="user_status">
                            <label>
                                <input type="radio" name="status" value="active"
                                       <c:if test="${requestScope.status eq 'active'}">checked</c:if>
                                >
                                <fmt:message key="user.active"/>
                            </label>
                            <label>
                                <input type="radio" name="status" value="inactive"
                                       <c:if test="${requestScope.status eq 'inactive'}">checked</c:if>
                                >
                                <fmt:message key="user.inactive"/>
                            </label>
                            <label>
                                <input type="radio" name="status" value="blocked"
                                       <c:if test="${requestScope.status eq 'blocked'}">checked</c:if>
                                >
                                <fmt:message key="user.blocked"/>
                            </label>

                            <input type="text" name="search_text" value="${requestScope.search_text}" placeholder=
                            <fmt:message
                                    key="user.search.placeholder"/>>
                        </div>
                        <input type="button" name="clear" value="<fmt:message key="user.clear"/>"
                               onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_all_students'">
                        <input type="submit" value="<fmt:message key="user.search"/>">
                    </div>
                </form>
                <div>
                    <c:if test="${fn:length(requestScope.all_students) > 0}">
                        <table>
                            <thead>
                            <tr>
                                <th>Firstname</th>
                                <th>Lastname</th>
                                <th>Phone Number</th>
                                <th>Passport</th>
                                <th>Birth Of Date</th>
                                <th>Registration Time</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="student" items="${requestScope.all_students}">
                                <c:set var="color" value="#fff"/>
                                <c:choose>
                                    <c:when test="${student.status.status == 'active'}">
                                        <c:set var="color" value="#fff"/>
                                    </c:when>
                                    <c:when test="${student.status.status == 'inactive'}">
                                        <c:set var="color" value="#e79a66"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="color" value="#ee8989"/>
                                    </c:otherwise>
                                </c:choose>
                                <tr style="background-color: ${color}">
                                    <td>${student.firstName}</td>
                                    <td>${student.lastName}</td>
                                    <td>${student.phoneNumber}</td>
                                    <td>${student.passportNumber}</td>
                                    <td>${student.birthDate}</td>
                                    <td>${student.createdAt}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${student.status.status == 'active'}">
                                                <div>
                                                    <a href="${pageContext.request.contextPath}/controller?command=change_user_status&id=${student.id}&new_status=blocked&page=${page}&status=${status}&search_text=${search_text}"
                                                       class="block-button">Block</a>
                                                </div>
                                            </c:when>
                                            <c:when test="${student.status.status == 'inactive'}">
                                                <div>
                                                    <a href="${pageContext.request.contextPath}/controller?command=change_user_status&id=${student.id}&new_status=active&page=${page}&status=${status}&search_text=${search_text}"
                                                       class="approve-button">Approve</a>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div>
                                                    <a href="${pageContext.request.contextPath}/controller?command=change_user_status&id=${student.id}&new_status=active&page=${page}&status=${status}&search_text=${search_text}"
                                                       class="unblock-button">Unblock</a>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <c:choose>
                            <c:when test="${not empty requestScope.status or not empty requestScope.search_text}">
                                <ctg:pages page="${page}" last="${last}" search_text="${requestScope.search_text}"
                                           status="${requestScope.status}" command="search_students"/>
                            </c:when>
                            <c:when test="${not empty requestScope.status}">
                                <ctg:pages page="${page}" last="${last}" status="${requestScope.status}"
                                           command="search_students"/>
                            </c:when>
                            <c:when test="${not empty requestScope.search_text}">
                                <ctg:pages page="${page}" last="${last}" search_text="${requestScope.search_text}"
                                           command="search_students"/>
                            </c:when>
                            <c:otherwise>
                                <ctg:pages page="${page}" last="${last}" command="go_to_all_students"/>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </div>
        </section>


    </div>
</div>


<jsp:include page="/pages/admin/modal/add_user.jsp"/>
</body>
</html>
