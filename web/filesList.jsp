<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <fmt:setBundle basename="messages"/>
    <title><fmt:message key="file.list"/></title>
    <style type="text/css">
        table, th, td {
            border: 1px solid #1e90ff;
        }
    </style>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/jquery-2.0.2.min.js"></script>
    <script type="text/javascript">
        var removeFunc = function(fileID, fileRemoveCode){
            $.ajax({
                type: "GET",
                url: "${pageContext.servletContext.contextPath}/file/download/id" + fileID,
                data: {
                    "op" : "remove",
                    "removecode" : fileRemoveCode,
                    "ajax" : true
                },
                statusCode:{
                    200: function(){
                        $("#row"+fileID).remove();
                    },

                    500: function(){
                        alert("<fmt:message key="error.action.failed"/>");
                    }
                }
            });
        }
    </script>
</head>
<body>
    <c:url value="${pageContext.servletContext.contextPath}" var="sitePath"/>
    <table id="filesTable" style="border-collapse: collapse;">
        <tr>
            <th><fmt:message key="file.id"/></th>
            <th><fmt:message key="file.name"/></th>
            <th><fmt:message key="file.description"/></th>
            <th><fmt:message key="file.author"/></th>
            <th><fmt:message key="file.notes"/></th>
        </tr>

        <c:forEach items="${filesList}" var="file">
            <tr id="row${file.id}">
                <td>${file.id}</td>
                <td>${file.fileName}</td>
                <td>${file.description}</td>
                <td>${file.author}</td>
                <td>${file.notes}</td>
                <td><button id="removeButton" onclick="removeFunc(${file.id},${file.removeCode})">
                    <fmt:message key="action.delete"/></button></td>

            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.servletContext.contextPath}/index.jsp"><fmt:message key="site.main"/></a>
</body>
</html>