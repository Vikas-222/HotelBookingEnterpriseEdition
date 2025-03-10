<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" href="CSS/style.css">
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">--%>
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
</head>
<body>

<%--------------------------------------------------------------------------------%>
<h2>Sign in/up Form</h2>
<div class="container" id="container">
    <div class="form-container sign-up-container">
        <form action="" method="">
            <h1>Create Account</h1>
            <div class="social-container">
                <a href="#" class="social"><i class="fa-brands fa-facebook"></i></a>
                <a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
                <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
            </div>
            <span>or use your email for registration</span>
            <input type="text" id="Name" name="Name" placeholder="Name" autocomplete="name" required/>
            <input type="email" id="Email" name="Email" placeholder="Email" autocomplete="email" required/>
            <input type="password" id="Password" name="Password" placeholder="Password" required/>
            <button onclick="submitForm(e)">Sign Up</button>
        </form>
    </div>

<%--    --------------------------------------------------------------------------------------%>
    <div class="form-container sign-in-container">
        <form name="loginform">
            <h1>Sign in</h1>
            <div class="social-container">
                <a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                <a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
                <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
            </div>
            <span>or use your account</span>
            <input type="email" name="email" placeholder="Email" required/>
            <input type="password" name="password" placeholder="Password" required/>
            <a href="#">Forgot your password?</a>
            <button>Sign In</button>
        </form>

        <p id="message"></p>


    </div>
    <div class="overlay-container">
        <div class="overlay">
            <div class="overlay-panel overlay-left">
                <h1>Welcome Back!</h1>
                <p>To keep connected with us please login with your personal info</p>
                <button class="ghost" id="signIn">Sign In</button>
            </div>
            <div class="overlay-panel overlay-right">
                <h1>Hello, Friend!</h1>
                <p>Enter your personal details and start journey with us</p>
                <button class="ghost" id="signUp">Sign Up</button>
            </div>
        </div>
    </div>
</div>

<footer>
    <p>
        All Rights are reserved @Vikas <i class="fa fa-heart"></i>
    </p>
</footer>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
    $(document).ready(function () {
        $("#loginForm").submit(function (event) {
            event.preventDefault();

            let formData = {
                username: $("#email").val(),
                password: $("#password").val()
            };

            $.ajax({
                url: "http://localhost:8080/HotelSystemEnterprise_war_exploded/login",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(formData),
                success: function (response, status, xhr) {
                    // document.cookie = "JSESSIONID=" + xhr.getResponseHeader("JSESSIONID"); // Store session ID
                    window.location.href = "home";
                },
                error: function (xhr) {
                    $("#message").text(xhr.responseText);
                }
            });
        });
    });
</script>





<%--<script>--%>
<%--    function submitForm(e) {--%>
<%--        e.preventDefault();--%>
<%--        var formData = {--%>
<%--            name: $("#Name").val(),--%>
<%--            email: $("#Email").val(),--%>
<%--            password: $("#Password").val()--%>
<%--        };--%>

<%--        $.ajax({--%>
<%--            type: "POST",--%>
<%--            url: "http://localhost:8080/HotelSystemEnterprise_war_exploded/SignupServlet",--%>
<%--            contentType: "application/json",--%>
<%--            data: JSON.stringify(formData),--%>
<%--            success: function(response) {--%>
<%--                alert("Data submitted successfully!"+response);--%>
<%--            },--%>
<%--            error: function(error) {--%>
<%--                alert("Error submitting data: " + error);--%>
<%--            }--%>
<%--        });--%>
<%--    }--%>
<%--</script>--%>

<script src="JS/script.js"></script>
</body>
</html>