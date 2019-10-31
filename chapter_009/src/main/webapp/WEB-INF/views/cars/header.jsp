<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var='currentUser' value='${sessionScope.user}'/>

<script>
    function resetModal(form) {
        $(form)[0].reset();
        $("#alert").hide();
    }

    function logOut() {
        $.ajax({
            url: "./signout",
            type: "POST",
            data: {
                "action": "signout"
            },
            success: function() {
                location.reload();
            }
        });
    }

    $(function() {
            $("#submit").click(
                function() {
                    validate();
                }
            )
        }
    );

    function validate() {
        var login = $("#login").val();
        var pwd = $("#pwd").val();
        if (login === '' || pwd === '') {
            showError();
        } else {
            $.ajax({
                url: "./signin",
                type: "POST",
                dataType: "json",
                data: {
                    "login": login,
                    "password": pwd
                },
                success: function(response) {
                    if (response.error !== undefined) {
                        showError();
                    } else {
                        location.reload();
                        $("#login-modal").modal("hide");
                    }
                }
            });
        }
    }

    function showError() {
        $("#alert").show();
    }

    $(function() {
        var submitform = $("#signupform");
        submitform.submit(function(event) {
            event.preventDefault();
            var user = createUser();
            $.ajax({
                url: "./user?action=signup",
                type: "POST",
                contentType: "json",
                dataType: "json",
                data: JSON.stringify(user),
                success: function (response) {
                    if (response.error !== undefined) {
                        alert(response.error);
                    } else {
                        location.reload();
                        $("#signup-modal").modal("hide");
                    }
                }
            });
        })
    });

    function createUser() {
        var user = {};
        user.login = $("#login0").val();
        user.password = $("#password0").val();
        user.firstname = $("#firstname0").val();
        user.lastname = $("#lastname0").val();
        user.email = $("#email0").val();
        return user;
    }
</script>

<header class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="./">Car Store</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="./">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="./addcar.jsp">Add car</a>
            </li>
            <c:if test="${currentUser != undefined}">
                <li class="nav-item">
                    <a class="nav-link" href="./usercars.jsp?userId=<c:out value='${currentUser.id}'/>">My cars</a>
                </li>
            </c:if>
        </ul>

        <c:choose>
            <c:when test="${currentUser != undefined}">
                <span class="navbar-text">
                    Hello, ${currentUser.firstname}!
                </span>
                <a class="nav-link" href="./edituser.jsp">My account</a>
                <a class="nav-link" href="#" onclick="logOut()">Log out</a>
            </c:when>
            <c:otherwise>
                <a class="nav-link" href="#login-modal" data-toggle="modal" onclick="resetModal('#loginform')">Log in</a>
                <a class="nav-link" href="#signup-modal" data-toggle="modal" onclick="resetModal('#signupform')">Sign up</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>

<div class="modal fade formatting-help" id="login-modal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="loginform">
                <div class="modal-header">
                    <h3>Log in</h3>
                </div>
                <div class="modal-body">
                    <form class="signin_form">
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
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success" id="submit" type="submit">Log in</button>
                    <a class="btn btn-default" data-dismiss="modal">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="signup-modal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <form class="needs-validation" id="signupform">
                <div class="modal-header">
                    <h3>Register</h3>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="control-label" for="login0">Login:</label>
                        <input type="text" class="form-control" id="login0" name="login" placeholder="Enter login" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="password0">Password:</label>
                        <input type="password" class="form-control" id="password0" name="password" placeholder="Enter password" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="login0">First name:</label>
                        <input type="text" class="form-control" id="firstname0" name="firstname" placeholder="Enter your first name" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="login0">Last name:</label>
                        <input type="text" class="form-control" id="lastname0" name="lastname" placeholder="Enter your last name" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="email0">E-mail:</label>
                        <input type="email" class="form-control" id="email0" name="email" placeholder="Enter email" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success" id="signup" type="submit">Sign up</button>
                    <a class="btn btn-default" data-dismiss="modal">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>
