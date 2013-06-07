<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Index Page</title>
    <fmt:setBundle basename="messages"/>
</head>
<body>
    <h1><fmt:message key="site.greeting"/></h1>
    <div id="messages" style="color: red;">
        ${message} <br/>
    </div>
    <a href="${pageContext.servletContext.contextPath}/registration.jsp"><fmt:message key="site.registration"/></a>
    <a href="${pageContext.servletContext.contextPath}/addFile.jsp"><fmt:message key="site.addfile"/></a>
</body>
</html>