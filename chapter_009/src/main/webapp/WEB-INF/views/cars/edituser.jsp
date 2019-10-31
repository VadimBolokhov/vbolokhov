<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My account</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<c:set var='user' value='${sessionScope.user}'/>

<script>
    $(function () {
        $("#update-user-info").click(function (event) {
            event.preventDefault();
            var user = getUserInfo();
            $.ajax({
                url: "./user?action=update",
                type: "POST",
                contentType: "json",
                dataType: "json",
                data: JSON.stringify(user),
                success: function(response) {
                    $("#update-success").show();
                    console.log(response.success);
                }
            });
        });
    });

    function getUserInfo() {
        var user = {};
        user.login = "<c:out value="${user.login}"/>";
        user.id = <c:out value="${user.id}"/>;
        user.firstname = $("#edit-firstname").val();
        user.lastname = $("#edit-lastname").val();
        user.email = $("#edit-email").val();
        user.role = "<c:out value="${user.role}"/>";
        return user;
    }

    $(function () {
        $("#update-user-password").click(function (event) {
            event.preventDefault();
            var newPwd = $("#new-pwd1").val();
            if (newPwd !== $("#new-pwd2").val()) {
                $(".new-pwd").addClass("is-invalid");
            } else {
                $.ajax({
                    url: "./user?action=changePassword",
                    type: "POST",
                    dataType: "json",
                    data: {
                        "oldPassword": $("#old-pwd").val(),
                        "newPassword": newPwd
                    },
                    success: function(response) {
                        if (response.error !== undefined) {
                            $("#old-pwd").addClass("is-invalid");
                        } else {
                            console.log(response.success);
                            $("#change-pwd-success").show();
                            $("#change-pwd-form")[0].reset();
                        }
                    }
                });
            }
        });
    });

    $(function () {
        $("#old-pwd").focus(function () {
            if ($(this).hasClass("is-invalid")) {
                $(this).first().removeClass("is-invalid");
            }
        });
    });

    $(function () {
        $(".new-pwd").focus(function () {
            $(".new-pwd").removeClass("is-invalid");
        });
    });

</script>

<jsp:include page="header.jsp"/>

<div class="container">
    <h3>Personal info:</h3>

    <div id="update-success" class="alert alert-success" role="alert" style="display: none">
        Saved
    </div>

    <form method="post" id="edit-form" class="needs-validation">

        <div class="form-group">
            <label for="edit-login">Login:</label>
            <input type="text" class="form-control" id="edit-login" name="login"
                   value="<c:out value="${user.login}"/>" disabled>
        </div>

        <div class="form-group">
            <label for="edit-firstname">First name:</label>
            <input type="text" class="form-control" id="edit-firstname" name="firstname"
                   value="<c:out value="${user.firstname}"/>" required>
        </div>

        <div class="form-group">
            <label for="edit-lastname">Last name:</label>
            <input type="text" class="form-control" id="edit-lastname" name="lastname"
                   value="<c:out value="${user.lastname}"/>" required>
        </div>

        <div class="form-group">
            <label for="edit-email">E-mail:</label>
            <input type="text" class="form-control" id="edit-email" name="email"
                   value="<c:out value="${user.email}"/>" required>
        </div>

        <button class="btn btn-outline-primary" type="submit" id="update-user-info">Save</button>
    </form>

    <hr>

    <h3>Change Password:</h3>

    <div id="change-pwd-success" class="alert alert-success" role="alert" style="display: none">
        Saved
    </div>

    <form method="post" id="change-pwd-form" class="needs-validation">
        <div class="form-group">
            <label for="old-pwd">Old password:</label>
            <input type="password" class="form-control" id="old-pwd" required>
            <div class="invalid-feedback">
                Incorrect password.
            </div>
        </div>

        <div class="form-group">
            <label for="new-pwd1">New password:</label>
            <input type="password" class="form-control new-pwd" id="new-pwd1" required>
        </div>

        <div class="form-group">
            <label for="new-pwd2">Confirm password:</label>
            <input type="password" class="form-control new-pwd" id="new-pwd2" required>
        </div>

        <button class="btn btn-outline-primary" type="button" id="update-user-password">Submit</button>
    </form>
</div>

</body>
</html>
