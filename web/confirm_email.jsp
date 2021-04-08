<%-- 
    Document   : testfb
    Created on : Mar 2, 2021, 5:18:11 PM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
        <title>Confirm Page</title>
        <style> <%@include file="css/confirm_email.css" %></style>
    </head>
    <body>
         <div id="logreg-forms">
        <form class="form-signin" action="confirm" method="POST">
            
                <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> Confirm your Account</h1>
                <div class="social-login">
                </div>

                <input type="hidden" name="email" value="${param.email}" />
                <div class="g-recaptcha" data-sitekey="6LfSQm8aAAAAANzh0511ARCK_QYZix8o6s77fM15"></div>
                <button class="btn btn-success btn-block" type="submit" name="btAction" value="SignIn"><i class="fas fa-sign-in-alt"></i> Confirm </button>
                <!-- <p>Don't have an account!</p>  -->
            </form>
         </div>
    </body>
</html>
