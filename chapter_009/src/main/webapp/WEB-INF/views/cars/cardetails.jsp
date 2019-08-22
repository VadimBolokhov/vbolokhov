<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Car details</title>

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

<script>
    $(function () {
            getCarDetails();
            loadImages();
        }
    );

    function getCarDetails() {
        $.ajax({
            url: "./store",
            type: "GET",
            dataType: "json",
            data: {
                action: "getCar",
                id: <c:out value="${param.carId}"/>
            },
            success: function(response) {
                if (response.error !== undefined) {
                    alert(response.error);
                } else {
                    displayCarDetails(response);
                }
            }
        });
    }

    function displayCarDetails(car) {
        $("#description").text(car.desc);
        $("#car-info > table")
            .append("<tr><th>Car make:</th><td>" + car.model.make.name + "</td></tr>")
            .append("<tr><th>Car model:</th><td>" + car.model.name + "</td></tr>")
            .append("<tr><th>Body syle:</th><td>" + car.model.body.style + "</td></tr>")
            .append("<tr><th>Engine type:</th><td>" + car.model.engine.type + "</td></tr>")
            .append("<tr><th>Transmission:</th><td>" + car.model.transmission.type + "</td></tr>")
            .append("<tr><th>Gearbox:</th><td>" + car.gearbox.type + "</td></tr>")
            .append("<tr><th>Odometer:</th><td>" + car.odometer + "</td></tr>")
            .append("<tr><th>Color:</th><td>" + car.color + "</td></tr>")
            .append("<tr><th>Status:</th><td>" + getCarStatus(car) + "</td></tr>")
            .append("<tr><th>Price:</th><td>" + car.price + "</td></tr>");
        <c:if test="${currentUser != undefined}">
        if (car.owner.id === <c:out value="${currentUser.id}"/>) {
            $("#edit-button").show();
        }
        </c:if>
        displayUserDetails(car.owner);
    }

    function getCarStatus(car) {
        return car.sold ? "Sold" : "For sale";
    }

    function displayUserDetails(user) {
        $("#user-info > table")
            .append("<tr><th>Name:</th><td>" + user.firstname + " " + user.lastname + "</td></tr>")
            .append("<tr><th>E-mail:</th><td>" + user.email + "</td></tr>");
    }

    function loadImages() {
        $.ajax({
            url: "./resource",
            type: "GET",
            data: {
                action: "getImages",
                carId: <c:out value="${param.carId}"/>
            },
            success: function (response) {
                if (response.error === undefined) {
                    displayImages(response);
                }
            }
        });
    }

    function displayImages(images) {
        images.forEach(function (image) {
            $("#photo").append(
                "<img class='img-fluid' src='./resource?action=getImage&carId="
                + <c:out value="${param.carId}"/>
                + "&image=" + image
                + "'/>"
            );
        });
    }
</script>

<jsp:include page="header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-sm">
            <div class="span" id="description">
                Car description
            </div>
            <div class="span" id="car-info">
                <table border="0" id="car-details"></table>
            </div>

            <button class="btn btn-outline-primary" id="edit-button" type="button"
                    onclick="location.href='./editcar.jsp?carId=<c:out value="${param.carId}"/>'" style="display: none">
                Edit
            </button>
        </div>
        <div class="col-sm">
            Owner:
            <div class="span" id="user-info">
                <table border="0" id="user-details"></table>
            </div>
        </div>
    </div>

    <div class="span" id="photo"></div>
</div>
</body>
</html>
