<%@ page import="ru.job4j.crud.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<%
    @SuppressWarnings("unchecked")
    List<User> users = (List<User>) request.getAttribute("users");
    String contextPath = request.getContextPath();
%>
<table>
    <tr><th>ID</th><th>Login</th><th>Name</th><th>E-mail</th><th>Creation date</th></tr>
    <% for (User user : users) {%>
    <% String id = user.getId(); %>
    <tr>
        <td><%=id%></td>
        <td><%=user.getLogin()%></td>
        <td><%=user.getName()%></td>
        <td><%=user.getEmail()%></td>
        <td><%=user.getCreateDate()%></td>
        <td>
            <form action='<%=contextPath%>/edit' method='get'>
                <input type='hidden' name='id' value='<%=id%>'/>
                <input type='submit' value='Edit'/>
            </form>
        </td><td>
        <form action='<%=contextPath%>/list' method='post'>
            <input type='hidden' name='id' value='<%=id%>'/>
            <input type='hidden' name='action' value='delete'/>
            <input type='submit' value='X'/>
        </form>
    </td>
    </tr>
    <%}%>
    <tr>
        <td>
            <form action='<%=contextPath%>/create' method='get'>
                <input type='submit' value='+'/>
            </form>
        </td>
    </tr>
</table>
</body>
</html>