<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User creation</title>
</head>
<body>
<c:out value='${requestScope.message}'/>
<form action='${pageContext.servletContext.contextPath}/list' method='get'>
    <input type='submit' name='list' value='User list'/>
</form>
<form action='${pageContext.servletContext.contextPath}/create' method='get'>
    <input type='submit' name='new' value='New user'/>
</form>
</body>
</html>
