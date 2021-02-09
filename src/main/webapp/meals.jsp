<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html lang="ru">
<head>
    <style>
        table {
            margin: auto;
            background-color: #eaeaea;
            font-size: 20px;
        }

        table, th, td {
            border: 2px solid black;
            border-collapse: collapse;
            padding: 10px;
        }

        .header {
            font-size: 25px;
        }
    </style>
    <title>Meals</title>
</head>

<body style="background-color: lightblue; text-align: center">
<h3 style="text-align: left"><a href="index.html">Home</a></h3>
<hr>
<h1>Meals</h1>
<br>

<table style="width:50%">

    <tr class="header">
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="mealsList" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${mealsList}">
        <c:choose>
            <c:when test="${meal.getExcess()}">
                <c:set var="textColor" value="color: red"/>
            </c:when>
            <c:otherwise>
                <c:set var="textColor" value="color: green"/>
            </c:otherwise>
        </c:choose>

        <javatime:setZoneId value="Asia/Bangkok" />
        <javatime:format value="${meal.getDateTime()}" style="MS" var="parsedDate"/>

        <tr style="${textColor}">
            <th>${parsedDate}</th>
            <th>${meal.getDescription()}</th>
            <th>${meal.getCalories()}</th>
            <th>Update</th>
            <th>Delete</th>
        </tr>

    </c:forEach>

</table>
</body>
</html>
