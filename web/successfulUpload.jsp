<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <fmt:setBundle basename="messages"/>
    <title><fmt:message key="site.upload.success"/></title>
</head>
<body>
    <c:url value="${servletContext.contextPath}/file/download/id${fileID}" var="downloadLink"></c:url>
    <c:url value="${servletContext.contextPath}/index.jsp" var="indexPage"></c:url>
    <c:url value="${servletContext.contextPath}/file/download/id${fileID}?op=remove&removecode=${removeCode}"
           var="removeLink"></c:url>
    <c:if test="${fileID != null}">
        <fmt:message key="file.link.download"/> <a href="${downloadLink}">http://localhost:8080${downloadLink}</a><br/>
        <fmt:message key="file.link.remove"/> <a href="${removeLink}">http://localhost:8080${removeLink}</a><br/>
    </c:if>
    <a href="${indexPage}"><fmt:message key="site.main"/></a>
</body>
</html>