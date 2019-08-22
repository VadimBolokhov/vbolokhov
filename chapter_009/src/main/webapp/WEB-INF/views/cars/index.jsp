<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Online car store</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>

    $(function () {
        createTableHeader();
        createTableBody();
    });

    function createTableHeader() {
        $("#cars > thead").append(
            "<tr><th>#</th>"
            + "<th scope='col'>Car model</th>"
            + "<th scope='col'>Post date</th>"
            + "<th scope='col'>Price</th></tr>"
        );
    }

    function createTableBody() {
        $.ajax({
            url: "./store",
            type: "GET",
            dataType: "json",
            data: {"action" : "getUnsoldCars"},
            success: function(cars) {
                generateTableContent(cars);
            }
        });
    }

    function generateTableContent(cars) {
        for (var i = 0; i < cars.length; i++) {
            addRow(cars[i], i + 1);
        }
    }

    function addRow(car, i) {
        $("#cars > tbody").append("<tr><td>"
            + i + "</td><td>"
            + "<a href='./cardetails.jsp?carId=" + car.id + "'>"
            + car.model.make.name.toUpperCase() + " "
            + car.model.name + "</a></td><td>"
            + parseDate(car.postDate) + "</td><td>"
            + car.price
            + "</td></tr>"
        );
    }

    function parseDate(date) {
        return new Date(date.year, date.monthValue, date.dayOfMonth, date.hour, date.minute)
            .toLocaleString();
    }
</script>

<c:set var='currentUser' value='${sessionScope.user}'/>

<jsp:include page="header.jsp"/>

<div class="container">
    <div class="d-flex justify-content-center">
        <h3>Car list</h3>
    </div>

    <div id="table">
        <table class="table" id="cars">
            <thead></thead><tbody></tbody>
        </table>
    </div>
</div>
</body>
</html>
