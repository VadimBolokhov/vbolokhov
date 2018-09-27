<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update user</title>
</head>
<body>
<c:out value='${requestScope.message}'/>
<form action='${pageContext.servletContext.contextPath}/list' method='get'>
    <input type='submit' name='list' value='User list'/>
</form>
</body>
</html>
