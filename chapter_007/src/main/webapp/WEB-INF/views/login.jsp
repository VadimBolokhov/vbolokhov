<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<c:if test="${error != ''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>
<form action="${pageContext.servletContext.contextPath}/signin" method="post">
    <table>
        <tr>
            <td>Login:</td>
            <td><input type='text' name='login' size='20'/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' size='20'/></td>
        </tr>
    </table>
    <input type='submit' value='Submit'/>
</form>
</body>
</html>
