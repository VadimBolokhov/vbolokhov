<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit user info</title>
</head>
<body>
<c:set var='currentUser' value='${sessionScope.user}'/>
<c:set var='user' value='${requestScope.user}'/>
<c:if test='${user.role == "ADMIN"}' var='isAdmin'/>
<form action='${pageContext.servletContext.contextPath}/edit?id=${user.id}' method='post'>
    <table>
        <tr>
            <td>Login:</td>
            <td>
                <input type='text' name='login' size='20' value='${user.login}' disabled/>
            </td></tr>
        <tr>
            <td>Password:</td>
            <td>
                <input type='password' name='password' size='20' value='${user.password}'/>
            </td></tr>
        <tr><td>Name:</td>
            <td>
                <input type='text' name='name' size='20' value='${user.name}'/>
            </td></tr>
        <tr><td>E-mail:</td>
            <td>
                <input type='text' name='email' size='20' value='${user.email}'/>
            </td>
        </tr>
        <c:if test="${currentUser.role == 'ADMIN'}">
            <tr>
                <td>Role:</td>
                <td>
                    <select name='role'>
                        <option value='ADMIN' ${isAdmin ? 'selected = "selected"' : ''}>
                            Administrator
                        </option>
                        <option value='USER' ${!isAdmin ? 'selected = "selected"' : ''}>
                            User
                        </option>
                    </select>
                </td>
            </tr>
        </c:if>
    </table>
    <c:if test="${currentUser.role != 'ADMIN'}">
        <input type="hidden" name="role" value="USER"/>
    </c:if>
    <input type='submit' value='Submit'/>
</form>
</body>
</html>
