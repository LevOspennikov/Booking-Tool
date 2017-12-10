<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>Bookings</title>
</head>
<body>

<h3>All bookings</h3>

<p>Total count of bookings: ${bookings.size()}</p>

<table>
    <c:forEach var="booking" items="${bookings}">
        <tr>
            <td><b>Booking Id: ${booking.getId()}</b></td>
        </tr>
        <tr>
            <td>${booking.getUserName()}, ${booking.getUserPhone()}</td>
        </tr>
        <tr>
            <td>Time: ${booking.getDate()}</td>
        </tr>
        <tr>
            <td>Count of persons: ${booking.getPersonsCount()}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>