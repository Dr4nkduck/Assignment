<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User:home</title>
    </head>
    <body>
        <h1>Siuuuuu</h1>
        <c:if test="${not empty userobj }">
            <h1>Name:${userobj.name }</h1>   
            <h1>Email:${userobj.email }</h1>
        </c:if>
    </body>
</html>
