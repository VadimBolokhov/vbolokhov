<%@ page import="ru.job4j.crud.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit user info</title>
</head>
<body>
<%User user = (User) request.getAttribute("user");%>
<form action='<%=request.getContextPath()%>/edit?id=<%=user.getId()%>' method='post'>
    <table>
        <tr>
            <td>Login:</td>
            <td>
                <input type='text' name='login' size='20' value='<%=user.getLogin()%>' disabled/>
            </td></tr>
        <tr><td>Name:</td>
            <td>
                <input type='text' name='name' size='20' value='<%=user.getName()%>'/>
            </td></tr>
        <tr><td>E-mail:</td>
            <td>
                <input type='text' name='email' size='20' value='<%=user.getEmail()%>'/>
            </td></tr>
    </table>
    <input type='submit' value='Submit'/>
</form>
</body>
</html>
