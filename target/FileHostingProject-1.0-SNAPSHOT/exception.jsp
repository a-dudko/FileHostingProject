<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Exceptional Event Occurred!</title>
    <style type="text/css">
        body, p {
            font-family: Tahoma;
            font-size: 10pt;
            padding-left: 30;
        }

        pre {
            font-size: 8pt;
        }
    </style>
</head>
<body>
<div style="color:red">
    <%= exception.getMessage() %><br/><br/>
    <% for (StackTraceElement stackTraceElement : exception.getStackTrace()) { %>
    <%= stackTraceElement%> <br/>
    <% } %>
</div>
</body>
</html>