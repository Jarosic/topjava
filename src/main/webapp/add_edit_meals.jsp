<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <style>
        .div-form {
            background-color: #eaeaea;
            text-align: center;
            padding: 5px;
            margin: auto;
            border: 2px solid black;
            width: 500px;
            font-size: 20px;
        }
        input {
            border: 2px solid black;
            width: 300px;
            margin: 5px;
            height: 30px;
            padding: 5px;
            font-size: 15px;
        }

        form {
            margin: auto;
        }

        #button, button {
            border: 2px solid #8888bb;
            height: 30px;
            width: 80px;
            margin-top: 10px;
            font-size: 15px;
            background-color: #669966;
        }

        a {
            text-decoration: none;
            color: black;
        }

        label {
            float: bottom;
        }

        .field {
            clear:both;
            text-align: end;

        }

    </style>
    <title>Title</title>
</head>
<body style="background-color: lightblue; text-align: center">

<h3 style="text-align: left"><a href="index.html">Home</a></h3>
<hr>

<c:choose>
    <c:when test="${meal.getId() != null}">
        <c:set var="text" value="Edit Meal"/>
        <c:set var="tag" value=""/>
    </c:when>
    <c:otherwise>
        <c:set var="text" value="Add Meal"/>
    </c:otherwise>
</c:choose>
<h1>${text}</h1>

<div class="div-form">
    <form method="post" action="${pageContext.request.contextPath}/meals" name="frmAddMeal">
        <div class="field">
            <label for="date">DateTime:</label>
            <input required type="datetime-local" id="date" name="date" value="${meal.getDateTime()}"><br>
        </div>
        <div class="field">
            <label for="description">Description:</label>
            <input required type="text" id="description" name="description" value="${meal.getDescription()}"><br>
        </div>
        <div class="field">
            <label for="calories">Calories:</label>
            <input required type="text" id="calories" name="calories" value="${meal.getCalories()}"><br>
        </div>

        <c:if test="${meal.getId() != null}">
            <div class="field"> <label for="mealsId">Id:</label>
                <input  readonly type="text" id="mealsId" name="mealsId" value="${meal.getId()}"><br>
            </div>
        </c:if>
        <input id="button" type="Submit" value="Submit">
        <button><a href="${pageContext.request.contextPath}/meals">Cancel</a></button>
    </form>
</div>


</body>
</html>
