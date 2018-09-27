<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit user info</title>
</head>
<body>
<c:set var='user' value='${requestScope.user}'/>
<form action='${pageContext.servletContext.contextPath}/edit?id=${user.id}' method='post'>
    <table>
        <tr>
            <td>Login:</td>
            <td>
                <input type='text' name='login' size='20' value='${user.login}' disabled/>
            </td></tr>
        <tr><td>Name:</td>
            <td>
                <input type='text' name='name' size='20' value='${user.name}'/>
            </td></tr>
        <tr><td>E-mail:</td>
            <td>
                <input type='text' name='email' size='20' value='${user.email}'/>
            </td></tr>
    </table>
    <input type='submit' value='Submit'/>
</form>
</body>
</html>
