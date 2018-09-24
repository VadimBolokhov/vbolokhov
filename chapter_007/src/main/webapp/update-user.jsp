<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update user</title>
</head>
<body>
<%= (String) request.getAttribute("message")%>
<form action='<%=request.getContextPath()%>/list' method='get'>
    <input type='submit' name='list' value='User list'/>
</form>
</body>
</html>
