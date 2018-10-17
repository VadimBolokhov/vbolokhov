<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<c:set var='currentUser' value='${sessionScope.user}'/>
<c:if test='${currentUser.role != "ADMIN"}' var='notAdmin'/>
<table>
    <tr><th>ID</th><th>Login</th><th>Name</th><th>E-mail</th><th>Creation date</th><th>Role</th></tr>
    <c:forEach var='user' items='${requestScope.users}'>
        <c:set var='id' value='${user.id}'/>
        <tr>
            <td><c:out value='${id}'/></td>
            <td><c:out value='${user.login}'/></td>
            <td><c:out value='${user.name}'/></td>
            <td><c:out value='${user.email}'/></td>
            <td><c:out value='${user.createDate}'/></td>
            <td><c:out value='${user.role}'/></td>
            <td>
                <form action='${pageContext.servletContext.contextPath}/edit' method='get'>
                    <input type='hidden' name='id' value='${id}'/>
                    <input type='submit' value='Edit'
                        ${(notAdmin && user.login != currentUser.login) ? 'disabled' : ''} />
                </form>
            </td><td>
            <form action='${pageContext.servletContext.contextPath}/list' method='post'>
                <input type='hidden' name='id' value='${id}'/>
                <input type='hidden' name='action' value='delete'/>
                <input type='submit' value='X' ${notAdmin ? 'disabled' : ''}/>
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
<form action="${pageContext.servletContext.contextPath}/signout" method="post">
    <input type="submit" value="Log out"/>
</form>
</body>
</html>