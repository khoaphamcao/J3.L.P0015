<%-- 
    Document   : renting_car
    Created on : Mar 2, 2021, 8:53:21 PM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style><%@include file="css/renting_date.css"%> </style>
        <title>Renting Date Page</title>
    </head>
    <body>
        <header>
            <h1>
                <font color="white">
                Car rental
                </font>
            </h1>
            <h3>
                <c:set var="name" value="${sessionScope.ACCOUNT.name}"/>
                <font color="white">

                <h3>Welcome ${name}  </h3>
                </font></h3>
        </header>
                <h2 style="text-align: center">Choose Renting Car Date</h2>
        <form action="default" method="POST">

            <table border="1" style="text-align: center">
                <thead>
                    <tr>
                        <th>Date renting</th>
                        <th>Date return</th>
                    </tr>
                </thead>
                <tbody>

                    <tr>
                        <td>
                            <input id="rentingdate"  type="date" name="renting_date" value="${param.renting_date}" required>
                        </td>
                        <td>
                            <input id="returndate"  type="date" min="" name="return_date" value="${param.return_date}" required>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="Confirm Date"/>
                        </td>
                    </tr>


                </tbody>

            </table>
            <font color="red" style="text-align: center">
            <h3>${requestScope.ERROR}</h3>
            </font>
        </form>
        <div class="footer-dark">
            <footer>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6 col-md-3 item">
                            <h3>Services</h3>
                            <ul>
                                <li><a href="#">Web design</a></li>
                                <li><a href="#">Development</a></li>
                                <li><a href="#">Hosting</a></li>
                            </ul>
                        </div>
                        <div class="col-sm-6 col-md-3 item">
                            <h3>About</h3>
                            <ul>
                                <li><a href="#">Company</a></li>
                                <li><a href="#">Team</a></li>
                                <li><a href="#">Careers</a></li>
                            </ul>
                        </div>
                        <div class="col-md-6 item text">
                            <h3>Company Name</h3>
                            <p>Praesent sed lobortis mi. Suspendisse vel placerat ligula. Vivamus ac sem lacus. Ut vehicula rhoncus elementum. Etiam quis tristique lectus. Aliquam in arcu eget velit pulvinar dictum vel in justo.</p>
                        </div>
                        <div class="col item social"><a href="#"><i class="icon ion-social-facebook"></i></a><a href="#"><i class="icon ion-social-twitter"></i></a><a href="#"><i class="icon ion-social-snapchat"></i></a><a href="#"><i class="icon ion-social-instagram"></i></a></div>
                    </div>
                    <p class="copyright">Company Name © 2018</p>
                </div>
            </footer>
        </div>
    </body>
    <script>
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1; //January is 0!
        var yyyy = today.getFullYear();
        if (dd < 10) {
            dd = '0' + dd
        }
        if (mm < 10) {
            mm = '0' + mm
        }
        today = yyyy + '-' + mm + '-' + dd;
        document.getElementById("rentingdate").setAttribute("min", today);
        document.getElementById("returndate").setAttribute("min", today);
    </script>
</html>
