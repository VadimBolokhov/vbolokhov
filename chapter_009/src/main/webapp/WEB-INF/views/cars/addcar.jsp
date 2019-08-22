<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add a car</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>

    <c:set var='userId' value='${sessionScope.user.id}'/>

    $(function() {
        addElements("action=getMakes", appendMakes);
        addElements("action=getGearboxes", appendGears);
        addElements("action=getColors", appendColors);
    });

    function addElements(data, success) {
        $.ajax({
            url: "./store",
            type: "GET",
            dataType: "json",
            data: data,
            success: function (response) {
                success(response);
            }
        });
    }

    function appendMakes(makes) {
        makes.forEach(function(make) {
            $("#make-select").append(
                "<option value=" + make.id + ">" + make.name + "</option>"
            );
        });
    }

    function appendGears(gears) {
        gears.forEach(function (gear) {
           $("#gearbox-select").append(
               "<option value=" + gear.id + ">" + gear.type + "</option>"
           );
        });
    }

    function appendColors(colors) {
        colors.forEach(function(color) {
            $("#color-select").append(
                "<option value=" + color + ">" + color + "</option>"
            );
        });
    }

    $(function() {
        $("#make-select").change(function () {
            var makeId = $(this).val();
            var selector = $("#model-form");
            if (makeId === "") {
                selector.hide();
            } else {
                var request = "action=getModels&makeId=" + makeId;
                addElements(request, appendModels);
            }
        });
    });

    function appendModels(models) {
        $("#model-select").html("<option value=''>- Select model -</option>");
        models.forEach(function (model) {
            $("#model-select").append(
                "<option value=" + model.id + ">" + model.name + "</option>"
            );
        });
        $("#model-form").show();
    }
</script>

<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <form method="post" id="addCar" class="needs-validation" enctype="multipart/form-data" action="./store">

        <div class="form-group">
            <label for="make-select">Car make:</label>
            <select class="form-control" id="make-select" required>
                <option value=""> - Select make - </option>
            </select>
        </div>

        <div class="form-group" id="model-form" style="display: none">
            <label for="model-select">Model:</label>
            <select class="form-control" id="model-select" name="model" required>
            </select>
        </div>

        <div class="form-group">
            <label for="gearbox-select">Gearbox type:</label>
            <select class="form-control" id="gearbox-select" name="gearbox" required>
                <option value=""> - Select gearbox - </option>
            </select>
        </div>

        <div class="form-group">
            <label for="odometer">Odometer:</label>
            <input class="form-control" type="number" id="odometer" name="odometer" required/>
        </div>

        <div class="form-group">
            <label for="color-select">Color:</label>
            <select class="form-control" id="color-select" name="color" required>
                <option value="">- Select color -</option>
            </select>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <input class="form-control" type="text" id="description" name="desc"/>
        </div>

        <div class="form-group">
            <label for="price">Price:</label>
            <input class="form-control" type="number" id="price" name="price" required/>
        </div>

        <div class="form-group">
            <label for="picture">Picture:</label>
            <input type="file" id="picture" name="picture" multiple/>
        </div>

        <input type="hidden" name="userId" value=<c:out value="${userId}"/>>

        <button type="submit" class="btn btn-outline-primary">Submit</button>

    </form>
</div>
</body>
</html>
