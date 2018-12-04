<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link href="css/styling.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script>
        function validate() {
            var login = $("#login").val();
            var password = $("#pwd").val();
            if (login == '' || password == '') {
                showError();
                return false;
            } else {
                $.ajax({
                    url: "./signin",
                    type: "POST",
                    data: {"login" : login,
                        "password" : password},
                    success: function(response) {
                        if (response.error != undefined) {
                            showError();
                            return false;
                        }
                        window.location = response.url;
                    }
                });
            }
            return true;
        }

        function showError() {
            $("#alert").show();
        }
    </script>
</head>
<body>
<div class="container">
    <form class="form-signin">
        <div id="alert" class="alert alert-danger" role="alert" style="display: none">
            Login is incorrect
        </div>

        <div class="form-group">
            <label class="control-label" for="login">Login:</label>
            <input type="text" class="form-control" id="login" name="login" placeholder="Enter login">
        </div>
        <div class="form-group">
            <label class="control-label" for="pwd">Password:</label>
            <input type="password" class="form-control" id="pwd" name="password" placeholder="Enter password">
        </div>

        <input type='button' class="btn btn-primary" value='Submit' onclick="return validate();"/>
    </form>
</div>
</body>
</html>
