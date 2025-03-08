<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <%@include file="all_component/allCss.jsp" %>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <style>
            .card {
                width: 100%;
                max-width: 400px;
            }
            .input-group-text {
                background: transparent;
                border: none;
                cursor: pointer;
            }

            .toggle-password {
                color: #888;
                font-size: 18px;
            }

            .toggle-password:hover {
                color: #333;
            }

        </style>
    </head> 
    <body style="background-color: #f0f1f2;">
        <%@include file="all_component/navbar.jsp" %>

        <div class="container vh-100 d-flex align-items-center justify-content-center">
            <div class="card">
                <div class="card-body">
                    <h4 class="text-center">Registration Page</h4>

                    <c:if test="${not empty succMsg}">
                        <p class="text-center text-success">${succMsg}</p>
                        <c:remove var="succMsg" scope="session" />
                    </c:if>

                    <c:if test="${not empty failedMSG}">
                        <p class="text-center text-danger">${failedMSG}</p>
                        <c:remove var="failedMSG" scope="session" />
                    </c:if>    

                    <!-- Registration Form -->
                    <form action="register" method="post" onsubmit="return validateForm();">
                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" class="form-control" required name="fname" value="${fname}">
                        </div>
                        <div class="form-group">
                            <label>Email address</label>
                            <input type="email" class="form-control" required name="email" value="${email}">
                        </div>
                        <div class="form-group">
                            <label>Phone Number</label>
                            <input type="text" class="form-control" required name="phone" value="${phone}">
                        </div>
                        <div class="form-group position-relative">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" required name="password" id="password">
                            <small id="password-error" class="text-danger d-none">Passwords do not match!</small>
                        </div>

                        <div class="form-group position-relative">
                            <label for="retype-password">Retype Password</label>
                            <input type="password" class="form-control" required id="retype-password">
                        </div>


                        <p id="error-message" class="text-danger text-center"></p>

                        <button type="submit" class="btn btn-primary w-100">Submit</button>
                    </form>   
                </div>
            </div>   
        </div>

        <%@include file="all_component/footer.jsp" %>  

        <script>
            function validatePhoneNumber(number) {
                return /([\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\b/.test(number);
            }

            function validateForm() {
                let phoneInput = document.getElementById("phone");
                let phoneError = document.getElementById("phone-error");
                let password = document.getElementById("password");
                let retypePassword = document.getElementById("retype-password");
                let passwordError = document.getElementById("password-error");

                let isValid = true;

                // Validate Phone Number
                if (!validatePhoneNumber(phoneInput.value)) {
                    phoneError.classList.remove("d-none");
                    phoneInput.classList.add("is-invalid");
                    isValid = false;
                } else {
                    phoneError.classList.add("d-none");
                    phoneInput.classList.remove("is-invalid");
                }

                // Validate Password Match
                if (password.value !== retypePassword.value) {
                    passwordError.classList.remove("d-none");
                    password.classList.add("is-invalid");
                    retypePassword.classList.add("is-invalid");
                    isValid = false;
                } else {
                    passwordError.classList.add("d-none");
                    password.classList.remove("is-invalid");
                    retypePassword.classList.remove("is-invalid");
                }

                return isValid;
            }

        </script>

    </body>
</html>
