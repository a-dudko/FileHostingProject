<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <fmt:setBundle basename="messages"/>
    <title><fmt:message key="site.register"/></title>
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
            <legend><fmt:message key="user.legend"/></legend>
            <div class="main">
                <div class="field">
                    <label for="login"><fmt:message key="user.login"/></label>
                    <input type="text" id="login" name="userLogin" /><br>
                </div>
                <div class="field">
                    <label for="password"><fmt:message key="user.password"/></label>
                    <input type="password" id="password" name="userPassword"/><br>
                </div>
                <div class="field">
                    <label for="repeatPassword"><fmt:message key="user.password.repeat"/></label>
                    <input type="password" id="repeatPassword" name="userRepPassword"/><br>
                </div>
                <div class="field">
                    <label for="adminCode"><fmt:message key="user.admincode"/></label>
                    <input type="password" id="adminCode" name="adminCode"/><br>
                </div>
            </div>
        </fieldset>

        <input type="hidden" id="action" name="action" value="cancel"/>
        <button type="button" onclick="submitForm();"><fmt:message key="action.register"/></button>
        <button type="submit"><fmt:message key="action.cancel"/></button>
    </form>

    <c:url value="${servletContext.contextPath}/index.jsp" var="indexPage"></c:url>
    <a href="${indexPage}"><fmt:message key="site.main"/></a>
</body>
</html>