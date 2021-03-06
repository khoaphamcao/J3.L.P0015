<%-- 
    Document   : login
    Created on : Feb 24, 2021, 9:17:00 PM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
        
        <title>Login Page</title>
        <style> <%@include file="css/login.css" %></style>




    </head>
    <body>
        <div id="logreg-forms">
            <form class="form-signin" action="login" method="POST">
                <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> Sign in</h1>
                <div class="social-login">
                </div>
                <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required="" autofocus="" name="txtEmail">
                <input type="password" id="inputPassword" class="form-control" placeholder="Password" required="" name="txtPassword">


                <div class="g-recaptcha" data-sitekey="6Lc5rnMaAAAAAAVmWQvlMiH9XjRWFWZzRu2cY6UV"></div>
                <button class="btn btn-success btn-block" type="submit" name="btAction" value="SignIn"><i class="fas fa-sign-in-alt"></i> Sign in</button>
                <font color="red">
                ${requestScope.ERROR}
                </font>
                <hr>
                <!-- <p>Don't have an account!</p>  -->
            </form>
            <form action="signUpPage" method="POST">
                <button class="btn btn-primary btn-block" type="submit" id="btn-signup" name="btAction" value="SignUp"><i class="fas fa-user-plus"></i> Sign up New Account</button>
            </form>
            <form action="default" method="POST">
                <button class="btn btn-primary btn-block" type="submit" id="btn-signup" name="btAction" value="Home Page"> Home Page </button>
            </form>    
    </body>
</html>

