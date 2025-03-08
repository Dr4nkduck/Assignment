<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <%@include file="all_component/allCss.jsp" %>
        <style>
            .card {
                width: 100%;
                max-width: 400px;
            }
        </style>
        <script>
            window.onload = function () {
                if (errMsg === null || errMsg.isEmpty()) {
                    let email = getCookie("user_email");
                    let password = getCookie("user_password");

                    if (email && password) {
                        document.getElementById("email").value = email;
                        document.getElementById("password").value = password;
                        document.getElementById("rememberMe").checked = true;

                        // Auto-submit the form
                        document.getElementById("loginForm").submit();
                    }
                }
            };

            function getCookie(name) {
                let cookies = document.cookie.split(';');
                for (let i = 0; i < cookies.length; i++) {
                    let c = cookies[i].trim();
                    if (c.startsWith(name + "=")) {
                        return c.substring(name.length + 1);
                    }
                }
                return "";
            }

            function setCookie(name, value, days) {
                let d = new Date();
                d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
                let expires = "expires=" + d.toUTCString();
                document.cookie = name + "=" + value + ";" + expires + ";path=/";
            }

            function deleteCookie(name) {
                document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
            }

            function saveCredentials() {
                if (document.getElementById("rememberMe").checked) {
                    setCookie("user_email", document.getElementById("email").value, 7);
                    setCookie("user_password", document.getElementById("password").value, 7);
                } else {
                    deleteCookie("user_email");
                    deleteCookie("user_password");
                }
            }
        </script>
    </head>
    <body style="background-color: #f0f1f2">
        <%@include file="all_component/navbar.jsp" %>

        <div class="container vh-100 d-flex align-items-center justify-content-center">
            <div class="card">
                <div class="card-body">
                    <h3 class="text-center">Login</h3>

                    <c:if test="${not empty failedMsg}">
                        <p class="text-center text-danger">${failedMsg}</p>
                        <c:remove var="failedMsg" scope="session" />
                    </c:if>  

                    <form action="login" method="post" id="loginForm" onsubmit="saveCredentials()">
                        <div class="form-group">
                            <label>Email address</label>
                            <input type="email" class="form-control" required name="email" id="email" value="${email}">
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" class="form-control" required name="password" id="password">
                        </div>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="rememberMe">
                            <label class="form-check-label">Remember Me</label>
                        </div>
                        <div class="text-center mt-2">
                            <button type="submit" class="btn btn-primary w-100">Login</button><br>
                            <a href="register.jsp">Create Account</a>
                        </div>
                    </form> 
                </div>
            </div>    
        </div>
    </body>
</html>
