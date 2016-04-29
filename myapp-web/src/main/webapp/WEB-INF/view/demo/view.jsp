<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    table{
        border-collapse: collapse;
    }
    table tr td{
        border:1px solid black;
        padding: 3px;
    }
</style>
<body>
<table >
    <tr>
        <td>ID</td>
        <td>账户</td>
    </tr>
    <c:forEach items="${users}" var="u">
        <tr>
            <td>${u.id}</td>
           <td>${u.account}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
