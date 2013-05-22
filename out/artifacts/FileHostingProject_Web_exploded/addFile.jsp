<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Uploading File Page</title>

    <script type="text/javascript">
        function submitForm() {

            if (!validateFileUploadForm()) {
                return;
            }
            document.getElementById("action").value = 'save';
            document.forms["fileUploadForm"].submit();
        }
        function validateFileUploadForm() {
            var errorMessages = [];
            var fileElement = document.getElementById("file");
            fileElement.style.backgroundColor = "white";
            if (fileElement.value == '') {
                errorMessages.push("File should be chosen");
                fileElement.style.backgroundColor = "red";
            }
            var descriptionElement = document.getElementById("description");
            descriptionElement.style.backgroundColor = "white";
            if (descriptionElement.value == '') {
                errorMessages.push("Description field should not be empty");
                descriptionElement.style.backgroundColor = "red";
            }
            var authorElement = document.getElementById("author");
            authorElement.style.backgroundColor = "white";
            if (authorElement.value == '') {
                errorMessages.push("Author field should not be empty");
                authorElement.style.backgroundColor = "red";
            }
            var notesElement = document.getElementById("notes");
            notesElement.style.backgroundColor = "white";
            if (notesElement.value == '') {
                errorMessages.push("Notes field should not be empty");
                notesElement.style.backgroundColor = "red";
            }
            document.getElementById("messages").innerHTML = errorMessages;
            return errorMessages.length == 0;
        }
    </script>
</head>
<body>
    <div id="messages" style="color: red;">
        <c:forEach items="${messages}" var="message">
            ${message}<br>
        </c:forEach>
    </div>

    <form id="fileUploadForm" action="${pageContext.servletContext.contextPath}/file/save" method="POST">
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
        <button type="button" onclick="submitForm('save');">Upload</button>
        <button type="submit">Cancel</button>
    </form>
</body>
</html>