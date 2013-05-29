<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Successful Upload Page</title>
</head>
<body>
    <c:url value="${pageContext.servletContext.contextPath}/file/download/id${fileID}" var="downloadLink"></c:url>
    <c:url value="${pageContext.servletContext.contextPath}/file/download/id${fileID}?op=remove&removecode=${removeCode}"
           var="removeLink"></c:url>
    Your link to download file: <a href="${downloadLink}">${downloadLink}</a>
    Your link to remove file: <a href="${removeLink}">${removeLink}</a>
</body>
</html>