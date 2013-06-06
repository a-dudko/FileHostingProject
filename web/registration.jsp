<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
    There will be registration page here soon.
    <c:url value="${pageContext.servletContext.contextPath}/index.jsp" var="indexPage"></c:url>
    <a href="${indexPage}">Main Page</a>
</body>
</html>