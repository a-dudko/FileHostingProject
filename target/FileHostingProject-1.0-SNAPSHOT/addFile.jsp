<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Uploading File Page</title>

    <script type="text/javascript" src="scripts/upload-form-validation.js"></script>
</head>
<body>
    <div id="messages" style="color: red;">
        <c:forEach items="${messages}" var="message">
            ${message} <br/>
        </c:forEach>
    </div>

    <form id="fileUploadForm" action="${pageContext.servletContext.contextPath}/file/upload"
          enctype="multipart/form-data" method="POST">
        <fieldset>
            <legend>New file information</legend>
            <input  type="file" id="file" value="Choose the file" name="file"/><br>
            <label for="description">File description</label>
            <textarea id="description" name="fileDescription"></textarea><br>
            <label for="author">Author</label>
            <input type="text" id="author" name="fileAuthor"/><br>
            <label for="notes">Notes</label>
            <input type="text" id="notes" name="fileNotes"/>
        </fieldset>

        <input type="hidden" id="action" name="action" value="cancel"/>
        <button type="button" onclick="submitForm();">Upload</button>
        <button type="submit">Cancel</button>
    </form>
</body>
</html>