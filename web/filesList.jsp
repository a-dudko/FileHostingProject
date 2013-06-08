<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Downloading File Information</title>
</head>
<body>
    <table style="border-collapse: collapse;">
        <tr>
            <th>File Name</th>
            <th>Author</th>
            <th>Description</th>
            <th>Notes</th>
        </tr>

        <tr>
            <td>${file.fileName}</td>
            <td>${file.author}</td>
            <td>${file.description}</td>
            <td>${file.notes}</td>
        </tr>
    </table>
</body>
</html>