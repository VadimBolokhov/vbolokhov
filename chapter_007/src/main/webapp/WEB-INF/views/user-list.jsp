<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<table>
    <tr><th>ID</th><th>Login</th><th>Name</th><th>E-mail</th><th>Creation date</th></tr>
    <c:forEach var='user' items='${requestScope.users}'>
        <c:set var='id' value='${user.id}'/>
        <tr>
            <td><c:out value='${id}'/></td>
            <td><c:out value='${user.login}'/></td>
            <td><c:out value='${user.name}'/></td>
            <td><c:out value='${user.email}'/></td>
            <td><c:out value='${user.createDate}'/></td>
            <td>
                <form action='${pageContext.servletContext.contextPath}/edit' method='get'>
                    <input type='hidden' name='id' value='${id}'/>
                    <input type='submit' value='Edit'/>
                </form>
            </td><td>
            <form action='${pageContext.servletContext.contextPath}/list' method='post'>
                <input type='hidden' name='id' value='${id}'/>
                <input type='hidden' name='action' value='delete'/>
                <input type='submit' value='X'/>
            </form>
        </td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <form action='${pageContext.servletContext.contextPath}/create' method='get'>
                <input type='submit' value='+'/>
            </form>
        </td>
    </tr>
</table>
</body>
</html>