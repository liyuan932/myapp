<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    table {
        border-collapse: collapse;
    }

    th, td {
        border: 1px solid black;
        padding: 3px;
    }
</style>
<body>
<table>
    <tr>
        <th>id</th>
        <th>account</th>
        <th>username</th>
        <th>sex</th>
        <th>age</th>
        <th>mobile</th>
        <th>address</th>
        <th>type</th>
        <th>status</th>
        <th>gmt_create</th>
    </tr>

    <c:forEach items="${users}" var="u">
    <tr>
        <td>${u.id}</td>
        <td>${u.account}</td>
        <td>${u.username}</td>
        <td>${u.sex}</td>
        <td>${u.age}</td>
        <td>${u.mobile}</td>
        <td>${u.address}</td>
        <td>${u.typeText}</td>
        <td>${u.statusText}</td>
        <td>${u.gmtCreateText}</td>
    </tr>
    </c:forEach>
</body>
</html>
