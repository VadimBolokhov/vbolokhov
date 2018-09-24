<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User creation</title>
</head>
<body>
<%
    String contextPath = request.getContextPath();
    String message = (String) request.getAttribute("message");%>
<%=message%>
<form action='<%=contextPath%>/list' method='get'>
    <input type='submit' name='list' value='User list'/>
</form>
<form action='<%=contextPath%>/create' method='get'>
    <input type='submit' name='new' value='New user'/>
</form>
</body>
</html>
