<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Index Page</title>
</head>
<body>
    <h1>Welcome to our file hosting service</h1>
    <div id="messages" style="color: red;">
        ${message} <br/>
    </div>
    <a href="${pageContext.servletContext.contextPath}/registration.jsp">Registration</a>
    <a href="${pageContext.servletContext.contextPath}/addFile.jsp">Add file</a>
</body>
</html>