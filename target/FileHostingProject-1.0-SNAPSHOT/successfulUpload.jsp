<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Successful Upload Page</title>
</head>
<body>
    <c:url value="${servletContext.contextPath}/file/download/id${fileID}" var="downloadLink"></c:url>
    <c:url value="${servletContext.contextPath}/index.jsp" var="indexPage"></c:url>
    <c:url value="${servletContext.contextPath}/file/download/id${fileID}?op=remove&removecode=${removeCode}"
           var="removeLink"></c:url>
    Your link to download file: <a href="${downloadLink}">http://localhost:8080${downloadLink}</a><br/>
    Your link to remove file: <a href="${removeLink}">http://localhost:8080${removeLink}</a><br/>
    <a href="${indexPage}">Main Page</a>
</body>
</html>