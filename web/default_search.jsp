<%-- 
    Document   : search
    Created on : Feb 22, 2021, 9:50:14 AM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style><%@include file="css/search.css" %> </style>
        <title>Home Page</title>
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


        <form action="loginPage" method="POST">
            <c:if test="${name == null}">
                <input type="submit" value="Login" name="btAction" />
            </c:if>
        </form>
        <form action="logout" method="POST">
            <c:if test="${name != null}">
                <input type="submit" value="Log Out" name="btAction" />
            </c:if>
        </form>





        <h3> Search Car </h3>
        <form action="search" method="POST">
            <div id="id-form" style="text-align: center; margin: 0">
                Car Name: <input type="text" name="txtCarName" value="${param.txtCarName}" />&nbsp;&nbsp;&nbsp;&nbsp;
                Car Category: <select name="categoryOption">&nbsp;&nbsp;
                    <c:set var="option" value="${param.categoryOption}"/>
                    <option>All</option>
                    <c:forEach var="category" items="${requestScope.LIST_CATEGORY}">
                        <option <c:if test="${option == category.categoryName}">selected</c:if>>${category.categoryName}</option>
                    </c:forEach>
                </select>&nbsp;&nbsp;&nbsp;&nbsp;
                Amount Of Car: <input type="number" name="txtAmount" min="0" value="${param.txtAmount}" max="100" /> 
                <input type="submit" value="Search" name="btAction"/>    
            </div>
        </form><br/>

        <c:set value="${param.categoryOption}" var="cateOption"/>
        <c:set value="${param.txtCarName}" var="txtSearchCar"/>
        <c:set value="${param.txtAmount}" var="amount"/>
        <c:set value="${requestScope.LIST_CAR}" var="list"/>
        <table border="1" class="table table-dark table-hover" style="width: 100%">
            <thead>
                <tr>
                    <th>CarID</th>
                    <th>CarName</th>
                    <th>Color</th>
                    <th>Year</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Renting</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="car" items="${list}">
                <form action="addToCart" method="POST">
                    <tr>
                        <td>${car.carID}
                            <input type="hidden" name="carID" value="${car.carID}" />
                        </td>
                        <td>${car.carName}
                            <input type="hidden" name="carName" value="${car.carName}" />
                        </td>
                        <td>${car.color}
                            <input type="hidden" name="color" value="${car.color}" />
                        </td>
                        <td>${car.year}
                            <input type="hidden" name="year" value="${car.year}" />
                        </td>
                        <td>${car.category}
                            <input type="hidden" name="category" value="${car.category}" />
                        </td>
                        <td>${car.price} $
                            <input type="hidden" name="price" value="${car.price}" />
                        </td>
                        <td>${car.quantity} 
                            <input type="hidden" name="quantity" value="${car.quantity}" />
                        </td>
                        <td> 
                            <input type="submit" name="renting" value="Add To Cart" /> 
                            <input type="hidden" name="index" value="${param.index}" />
                            <input type="hidden" name="txtCarName" value="${param.txtCarName}" />
                            <input type="hidden" name="categoryOption" value="${param.categoryOption}" />
                            <input type="hidden" name="txtAmount" value="${param.txtAmount}" />
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </tbody>
    </table>
    <div class="paging">
        <c:forEach begin="1" end="${requestScope.END_PAGE}" var="i">
            <a href="DefaultSearchServlet?index=${i}">${i}</a>
        </c:forEach>
    </div>

    <form action="viewCartPage" method="POST">
        <div class="id-search">
            <input type="submit" name="btAction" value="View Your Cart" />
        </div>
    </form> <br/>
    <form action="rentingDate" method="POST">
        <div class="id-search">
            <input type="submit" value="Return To Choose Date Renting" name="btAction" />
        </div>
    </form>

    <!--            <font color="red">
                <h2> No record found </h2>
                </font>-->
    <br/>
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
</html>
