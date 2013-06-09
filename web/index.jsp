<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Index Page</title>
    <script type="text/javascript" src="scripts/login-form-validation.js"></script>
    <link rel="stylesheet" href="styles/post-forms-style.css">
    <fmt:setBundle basename="messages"/>
</head>
<body>
    <h1><fmt:message key="site.greeting"/></h1>
    <div id="messages" style="color: red;">
        <c:forEach items="${messages}" var="message">
            ${message} <br/>
        </c:forEach>
    </div>
    <c:choose>
        <c:when test="${empty currentUser}">
            <form id="userLoginForm" action="${pageContext.servletContext.contextPath}/login" method="POST">
                <div class="main">
                    <div class="field">
                        <label for="login"><fmt:message key="user.login"/></label>
                        <input type="text" id="login" name="userLogin" /><br>
                    </div>
                    <div class="field">
                        <label for="password"><fmt:message key="user.password"/></label>
                        <input type="password" id="password" name="userPassword"/><br>
                    </div>
                </div> <br><br><br>
                <button type="button" onclick="submitForm();"><fmt:message key="action.signin"/></button>
            </form>
            <a href="${pageContext.servletContext.contextPath}/registration.jsp"><fmt:message key="site.registration"/></a>
        </c:when>
        <c:otherwise>
            <h3><fmt:message key="site.logged.message"/> ${currentUser}!</h3>
            <button type="button" onclick='window.location = "${pageContext.servletContext.contextPath}/logout" '>
                <fmt:message key="action.signout"/></button>
            <a href="${pageContext.servletContext.contextPath}/file/list"><fmt:message key="file.list"/></a>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.servletContext.contextPath}/addFile.jsp"><fmt:message key="site.addfile"/></a>
</body>
</html>