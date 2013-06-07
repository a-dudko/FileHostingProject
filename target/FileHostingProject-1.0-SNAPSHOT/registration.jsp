<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
    <link rel="stylesheet" href="styles/post-forms-style.css">
    <script type="text/javascript" src="scripts/registration-form-validation.js"></script>
</head>
<body>
    <div id="messages" style="color: red;">
        <c:forEach items="${messages}" var="message">
            ${message} <br/>
        </c:forEach>
    </div>

    <form id="userRegistrationForm" action="${pageContext.servletContext.contextPath}/registration" method="POST">
        <fieldset>
            <legend>New user</legend>
            <div class="main">
                <div class="field">
                    <label for="login">Login</label>
                    <input type="text" id="login" name="userLogin" /><br>
                </div>
                <div class="field">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="userPassword"/><br>
                </div>
                <div class="field">
                    <label for="repeatPassword">Repeat password</label>
                    <input type="password" id="repeatPassword" name="userRepPassword"/><br>
                </div>
                <div class="field">
                    <label for="adminCode">Admin code</label>
                    <input type="password" id="adminCode" name="adminCode"/><br>
                </div>
            </div>
        </fieldset>

        <input type="hidden" id="action" name="action" value="cancel"/>
        <button type="button" onclick="submitForm();">Register</button>
        <button type="submit">Cancel</button>
    </form>

    <c:url value="${pageContext.servletContext.contextPath}/index.jsp" var="indexPage"></c:url>
    <a href="${indexPage}">Main Page</a>
</body>
</html>