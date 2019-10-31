<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit car details</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<c:set var='currentUser' value='${sessionScope.user}'/>
<c:set var='car' value='${requestScope.car}'/>

<script>
    $(function () {
            setCarProperties();
            addImages();
        }
    );

    function setCarProperties() {
        addElements("action=getGearboxes", appendGears);
        addElements("action=getColors", appendColors);
    }

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

    function appendGears(gears) {
        gears.forEach(function (gear) {
            $("#gearbox-select").append(
                "<option value=" + gear.id + ">" + gear.type + "</option>"
            );
        });
        $("#gearbox-select > option[value=<c:out value='${car.gearbox.id}'/>]")
            .attr('selected', 'selected');
    }

    function appendColors(colors) {
        colors.forEach(function(color) {
            $("#color-select").append(
                "<option value=" + color + ">" + color + "</option>"
            );
        });
        $("#color-select > option[value=<c:out value='${car.color}'/>]")
            .attr('selected', 'selected');
    }

    function addImages() {
        $.ajax({
            url: "./resource",
            type: "GET",
            data: {
                action: "getImages",
                carId: <c:out value="${car.id}"/>
            },
            success: function (response) {
                appendImages(response);
            }
        });
    }

    function appendImages(images) {
        images.forEach(function (image) {
            $("#images").append(
                "<tr><td>"
                + "<a href='./resource?action=getImage&carId=" + <c:out value="${car.id}"/>
                + "&image=" + image + "'>"
                + image + "</a>"
                + "</td><td>"
                + "<input type='button' class='btn btn-danger' value='X'"
                + " onclick='deleteImage(\"" + image + "\")'>"
                + "</td></tr>"
            );
        })
    }

    function deleteImage(image) {
        $.ajax({
            url: "./resource",
            type: "POST",
            data: {
                action: "delete",
                carId: <c:out value="${car.id}"/>,
                image: image
            },
            success: function (response) {
                $("#images").empty();
                appendImages(response);
            }
        });
    }

    $(function () {
        $("#submit-button").click(function (event) {
            event.preventDefault();
            var form = $("#edit-form")[0];
            var data = new FormData(form);
            data.append("sold", $("#sold").prop('checked'));
            $.ajax({
                url: "./store?action=update",
                type: "POST",
                enctype: "multipart/form-data",
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                success: function (response) {
                    location.href=response.url;
                }
            });
        });
    });

</script>

<jsp:include page="header.jsp"/>

<div class="container">
    <form method="post" id="edit-form" class="needs-validation" enctype="multipart/form-data" action="./store?action=update">

        <div class="form-group" id="car-model">
            <h3><c:out value='${car.model.make.name}'/> <c:out value='${car.model.name}'/></h3>
        </div>
        <div class="form-group">
            <label for="gearbox-select">Gearbox type:</label>
            <select class="form-control" id="gearbox-select" name="gearbox">
            </select>
        </div>

        <div class="form-group">
            <label for="odometer">Odometer:</label>
            <input type="number" class="form-control" id="odometer"
                   name="odometer" value="<c:out value='${car.odometer}'/>" required>
        </div>

        <div class="form-group">
            <label for="color-select">Color:</label>
            <select class="form-control" id="color-select" name="color">
            </select>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <input type="text" class="form-control" id="description" name="desc" value="<c:out value='${car.desc}'/>">
        </div>

        <div class="form-group">
            <label for="price">Price:</label>
            <input class="form-control" type="number" id="price"
                   name="price" value="<c:out value='${car.price}'/>" required/>
        </div>

        <div class="form-group">
            <label for="sold">Sold:</label>
            <input type="checkbox" id="sold"
                   <c:if test="${car.sold == true}">checked</c:if>>
        </div>

        <input type="hidden" name="userId" value=<c:out value="${currentUser.id}"/>>
        <input type="hidden" name="carId" value=<c:out value="${car.id}"/>>

        <div class="form-group">
            <label for="picture">Picture:</label>
            <input type="file" id="picture" name="picture" multiple/>
        </div>

        <button class="btn btn-outline-primary" type="submit" id="submit-button" >Submit</button>

        <hr>

        <table class="table" id="images"></table>

    </form>
</div>
</body>
</html>

