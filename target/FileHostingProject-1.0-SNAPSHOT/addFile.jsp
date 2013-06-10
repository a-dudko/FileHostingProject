<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <fmt:setBundle basename="messages"/>
    <title><fmt:message key="site.upload"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/post-forms-style.css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/upload-form-validation.js">

    </script>
</head>
<body>
    <div id="messages" style="color: red;">
        <c:forEach items="${messages}" var="message">
            ${message} <br/>
        </c:forEach>
    </div>

    <form id="fileUploadForm" action="${pageContext.servletContext.contextPath}/file/upload/"
          enctype="multipart/form-data" method="POST">
        <fieldset>
            <legend><fmt:message key="file.legend"/></legend>
            <div class="main">
                <input  type="file" id="file" value="Choose the file" name="file"/><br>
                <div class="field">
                    <label for="description"><fmt:message key="file.description"/></label>
                    <textarea id="description" name="fileDescription"></textarea><br>
                </div>
                <div class="field">
                    <label for="author"><fmt:message key="file.author"/></label>
                    <input type="text" id="author" name="fileAuthor"/><br>
                </div>
                <div class="field">
                    <label for="notes"><fmt:message key="file.notes"/></label>
                    <input type="text" id="notes" name="fileNotes"/>
                </div>
            </div>
        </fieldset>

        <input type="hidden" id="action" name="action" value="cancel"/>
        <button type="button" onclick="submitForm();"><fmt:message key="action.upload"/></button>
        <button type="submit"><fmt:message key="action.cancel"/></button>
    </form>
    <a href="${pageContext.servletContext.contextPath}/index.jsp"><fmt:message key="site.main"/></a>
</body>
</html>