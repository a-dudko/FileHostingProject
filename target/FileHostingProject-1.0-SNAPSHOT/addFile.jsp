<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Uploading File Page</title>
    <link rel="stylesheet" href="styles/post-forms-style.css">
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
            <div class="main">
                <input  type="file" id="file" value="Choose the file" name="file"/><br>
                <div class="field">
                    <label for="description">File description</label>
                    <textarea id="description" name="fileDescription"></textarea><br>
                </div>
                <div class="field">
                    <label for="author">Author</label>
                    <input type="text" id="author" name="fileAuthor"/><br>
                </div>
                <div class="field">
                    <label for="notes">Notes</label>
                    <input type="text" id="notes" name="fileNotes"/>
                </div>
            </div>
        </fieldset>

        <input type="hidden" id="action" name="action" value="cancel"/>
        <button type="button" onclick="submitForm();">Upload</button>
        <button type="submit">Cancel</button>
    </form>
    <a href="index.jsp">Main Page</a>
</body>
</html>