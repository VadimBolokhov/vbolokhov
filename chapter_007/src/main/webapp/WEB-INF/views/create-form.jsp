<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User creation</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script>
        $(function() {
           $("#country").change(function() {
              var country = $(this).val();
              var cityForm = $("#city");
              if (country === "") {
                  cityForm.hide();
              } else {
                  $.ajax({
                      url: "./list",
                      type: "GET",
                      dataType: "json",
                      data: {
                          "action": "getCities",
                          "country": country
                      },
                      success: function (cities) {
                          cityForm.show();
                          cityForm.html("<option value=''> - Select city - </option>");
                          $.each(cities, function (i, city) {
                              cityForm.append("<option>" + city + "</option>");
                          });
                      }
                  })
              }
           });
        });

        $(function() {
            var form = $(".needs-validation").first();
            var loginField = $("#login");
            var loginError = loginField.parent().find("div");
            form.submit(function (event) {
                event.preventDefault();
                $.ajax({
                    url: "./create",
                    type: "POST",
                    data: {"login": loginField.val(),
                        "password": $("#password").val(),
                        "name": $("#name").val(),
                        "email": $("#email").val(),
                        "role": $("#role").val(),
                        "country": $("#country").val(),
                        "city": $("#city").val()
                    },
                    success: function (response) {
                        if (response.error != undefined) {
                            loginField.first().addClass("is-invalid");
                            loginError.text(response.error);
                            return false;
                        } else {
                            window.location = response.url;
                        }
                    }
                });
            });

            loginField.focus(function() {
                $(this).first().removeClass("is-invalid");
            })
        });
    </script>

</head>
<body>
<div class="container">
    <div width="50%">
        <form class="needs-validation">
            <div class="form-group">
                <label class="control-label" for="login">Login:</label>
                <input type="text" class="form-control" id="login" name="login" placeholder="Enter login" required>
                <div class="invalid-feedback">
                    Incorrect login.
                </div>
            </div>
            <div class="form-group">
                <label class="control-label" for="name">Name:</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter name" required>
            </div>
            <div class="form-group">
                <label class="control-label" for="password">Password:</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required>
            </div>
            <div class="form-group">
                <label class="control-label" for="email">E-mail:</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" required>
            </div>

            <div class="form-group">
                <select class="custom-select browser-default" id="country" name="country" required>
                    <option value="">
                        - Select country -
                    </option>
                    <c:forEach var='country' items='${requestScope.countries}'>
                        <option>${country}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <select class="custom-select browser-default" id="city" name="city" style="display: none" required>
                    <option value="">
                        - Select city -
                    </option>
                </select>
            </div>
            <input type='hidden' id="role" name='role' value='USER'/>
            <button class="btn btn-primary" type="submit">Submit form</button>
        </form>
    </div>
</div>
</body>
</html>
