<%-- 
    Document   : viewcart
    Created on : Mar 7, 2021, 8:41:56 PM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style><%@include file="css/viewcart.css" %></style>
        <title>View Cart Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.ACCOUNT.role != 'user' && sessionScope.ACCOUNT.status != 'Active'}">
            <c:redirect url="loginPage"></c:redirect>
        </c:if>
        <header>
            <h1>
                <font color="white">
                Renting Car Shop
                </font>
            </h1>
            <h3>
                <c:set var="fullName" value="${sessionScope.ACCOUNT.name}"/>
                <font color="white">

                <h3>Welcome ${fullName}  </h3>
                </font>
            </h3>
        </header>
        <form action="login" method="POST">
            <c:if test="${fullName == null}">
                <input type="submit" value="Login" name="btAction" />
            </c:if>
        </form>
        <form action="logout" method="POST">
            <c:if test="${fullName != null}">
                <input type="submit" value="Log Out" name="btAction" />
            </c:if>
        </form>

        <h2>Your Cart</h2>
        <c:set var="cart" value="${sessionScope.DISCOUNT_SHOPPING_CART}"></c:set>
        <c:if test="${cart != null}">
            <table class="table table-striped" border="1" style="width: 50%;text-align: center">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Car Name</th>
                        <th>Category</th>
                        <th>Color</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>


                <tbody>
                    <c:set var="total" value="0"></c:set>
                    <c:forEach var="cartCus" items="${cart.cars}">
                    <form action="actionOnCart" method="POST">
                        <tr>
                            <td>${cartCus.key}
                                <input type="hidden" name="txtCarID" value="${cartCus.key}" />
                            </td>
                            <td>${cartCus.value.carName}</td>
                            <td>${cartCus.value.category}</td>
                            <td>${cartCus.value.color}</td>
                            <td>
                                <input type="number" name="txtQuantity" value="${cartCus.value.quantity}" min="1" max="1000"/>
                                <input type="submit" value="Update Quantity" name="btAction" />
                            </td>
                            <td>${cartCus.value.quantity * cartCus.value.price} $
                                <input type="hidden" name="txtPrice" value="${cartCus.value.quantity * cartCus.value.price}" />
                                <c:set var="total" value="${total + cartCus.value.quantity * cartCus.value.price}" />
                            </td>
                            <td> 
                                <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" onclick="return confirm('Do you want to Delete this product?')" name="btAction" value="Remove Items">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </form>

                </c:forEach>
                <tr>
                    <td colspan="3">
                        <a href="default">Add more Car To Cart </a>
                    </td>
                    <td colspan="3">
                        Total Price =  ${total} $
                    </td>
                    <td>
                    </td>
                </tr>
            </tbody>
        </table>
    </c:if>

    <c:if test="${sessionScope.SHOPPING_CART != null}">
        <form action="updateDiscount" method="POST">
            <div id="id-discount">
                Discount Code: <input type="text" name="txtDiscountCode" value="${param.txtDiscountCode}"/>
                <input type="submit" value="Confirm code" />
                <c:if test="${requestScope.DISCOUNT_ERROR != null}">
                    <font color="red">
                    ${requestScope.DISCOUNT_ERROR}
                    </font>
                </c:if>
            </div>
        </form>
    </c:if>

    <br/>
    <c:if test="${cart == null}">
        <font color="red">
        <h2> Cart is empty</h2>
        </font>
    </c:if>

    <form action="renting" method="POST">
        <div id="id-discount">
            <c:if test="${cart != null}">
                <input type="submit" value="Renting" name="btAction" onclick="return confirm('Do you want to pay for this bill?')/>
                <input type="hidden" name="txtDiscountCode" value="${param.txtDiscountCode}" />
            </c:if>
            <c:if test="${requestScope.LIST_ERR != null}">
                <font color ="red">
                ${requestScope.LIST_ERR}
                </font>
            </c:if>
            <input type="hidden" name="total" value="${total}" />
        </div>
    </form> <br/>

    <form action="history" method="POST">
        <div id="id-discount">
            <input type="submit" value="View History" name="btAction"/>
        </div>
    </form><br/>

    <form action="default" method="POST">
        <div id="id-discount">
            <input type="submit" value="Return Car Shop" name="btAction"/>
        </div>
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
</html>
