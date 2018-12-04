<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit user info</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script>
        $(function() {
            var form = $(".needs-validation").first();
            form.submit(function (event) {
                event.preventDefault();
                var role = "${currentUser.role}" === "ADMIN" ? $("#role").val() : "USER";
                $.ajax({
                    url: "./edit",
                    type: "POST",
                    data: {"id": ${user.id},
                        "login": $("#login").val(),
                        "password": $("#password").val(),
                        "name": $("#name").val(),
                        "email": $("#email").val(),
                        "role": role,
                        "country": $("#country").val(),
                        "city": $("#city").val()
                    },
                    success: function (response) {
                        window.location = response.url;
                    }
                });
            })
        });

        $(function() {
            $("#country").change(function() {
                var country = $(this).val();
                $.ajax({
                    url: "./list",
                    type: "GET",
                    dataType: "json",
                    data: {
                        "action": "getCities",
                        "country": country
                    },
                    success: function (cities) {
                        var cityForm = $("#city");
                        cityForm.html("<option value=''> - Select city - </option>");
                        $.each(cities, function (i, city) {
                            cityForm.append("<option>" + city + "</option>");
                        });
                    }
                })

            });
        });
    </script>
</head>
<body>
<c:set var='currentUser' value='${sessionScope.user}'/>
<c:set var='user' value='${requestScope.user}'/>
<c:if test='${user.role == "ADMIN"}' var='isAdmin'/>
<div class="container">
    <div width="50%">
        <form class="needs-validation">
            <div class="form-group">
                <label class="control-label" for="login">Login:</label>
                <input type="text" class="form-control" id="login" name="login" value="${user.login}" disabled>
            </div>
            <div class="form-group">
                <label class="control-label" for="name">Name:</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter name" value="${user.name}" required>
            </div>
            <div class="form-group">
                <label class="control-label" for="password">Password:</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" value="${user.password}" required>
            </div>
            <div class="form-group">
                <label class="control-label" for="email">E-mail:</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" value="${user.email}">
            </div>

            <c:if test="${currentUser.role == 'ADMIN'}">
                <div class="form-group">
                    <select class="custom-select browser-default" id="role" name="role" placeholder="Select user role">
                        <option value='ADMIN' ${isAdmin ? 'selected = "selected"' : ''}>
                            Administrator
                        </option>
                        <option value='USER' ${!isAdmin ? 'selected = "selected"' : ''}>
                            User
                        </option>
                    </select>
                </div>
            </c:if>

            <div class="form-group">
                <select class="custom-select browser-default" id="country" name="country">
                    <c:forEach var='country' items='${requestScope.countries}'>
                        <option ${user.country == country ? 'selected = "selected"' : ''}>${country}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <select class="custom-select browser-default" id="city" name="city" required>
                    <c:forEach var='city' items='${requestScope.cities}'>
                        <option ${user.city == city ? 'selected = "selected"' : ''}>${city}</option>
                    </c:forEach>
                </select>
            </div>

            <button class="btn btn-primary" type="submit">Submit form</button>
        </form>
    </div>
</div>
</body>
</html>
