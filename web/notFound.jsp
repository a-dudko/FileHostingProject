<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>404 error</title>
</head>
<body>
    <div id="messages" style="color: red;">
        ${message} <br/>
    </div>
    You've requested not existed url. Got 404 error
</body>
</html>