<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User creation</title>
</head>
<body>
<form action='${pageContext.servletContext.contextPath}/create' method='post'>
    <table>
        <tr>
            <td>Login:</td>
            <td><input type='text' name='login' size='20'/></td>
        </tr>
        <tr>
            <td>Name:</td>
            <td><input type='text' name='name' size='20'/></td>
        </tr>
        <tr>
            <td>E-mail:</td>
            <td><input type='text' name='email' size='20'/></td>
        </tr>
    </table>
    <input type='submit' value='Submit'/>
</form>
</body>
</html>
