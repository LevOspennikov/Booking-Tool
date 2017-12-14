<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>Edit booking #${booking.getId()}</title>
</head>
<body>

<h2>Edit booking #${booking.getId()}</h2>

<p><b>Booking info:</b> ${booking.getDate()}, count of persons: ${booking.getPersonsCount()}</p>
<p><b>Client:</b> ${booking.getUserName()}, ${booking.getUserPhone()}</p>

<form:form modelAttribute="booking" method="POST" action="/change-booking">
    <table>
        <tr>
            <td><form:hidden path="id" value="${booking.getId()}"/></td>
        </tr>
        <tr>
            <td><form:label path="date">New time:</form:label></td>
            <td><form:input path="date"/></td>
        </tr>
        <tr>
            <td><form:label path="personsCount">New persons count:</form:label></td>
            <td><form:input path="personsCount"/></td>
        </tr>
    </table>
    <input type="submit" value="Change booking">
</form:form>

<br>
<br>
<form:form method="GET" action="/get-bookings">
    <input type="submit" value="Back to list of bookings">
</form:form>
</body>
</html>