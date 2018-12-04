<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User list</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
<c:set var='currentUser' value='${sessionScope.user}'/>
<c:if test='${currentUser.role != "ADMIN"}' var='notAdmin'/>
<table class="table table-condensed" style="width: 100%">
    <thead>
    <tr>
        <th>ID</th><th>Login</th><th>Name</th><th>E-mail</th><th>Creation date</th><th>Role</th>
        <th>Country</th><th>City</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var='user' items='${requestScope.users}'>
        <c:set var='id' value='${user.id}'/>
        <tr id="user${id}">
            <td><c:out value='${id}'/></td>
            <td><c:out value='${user.login}'/></td>
            <td><c:out value='${user.name}'/></td>
            <td><c:out value='${user.email}'/></td>
            <td><c:out value='${user.createDate}'/></td>
            <td><c:out value='${user.role}'/></td>
            <td><c:out value='${user.country}'/></td>
            <td><c:out value='${user.city}'/></td>
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
    </tbody>
</table>
    <form action='${pageContext.servletContext.contextPath}/create' method='get'>
        <input class="btn btn-primary" type='submit' value='Add user'/>
    </form>
<form action="${pageContext.servletContext.contextPath}/signout" method="post">
    <input class="btn btn-primary" type="submit" value="Log out"/>
</form>
</div>
</body>
</html>