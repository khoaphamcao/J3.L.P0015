/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khoapc.car.CarDTO;
import khoapc.cart.CartDTO;
import khoapc.discount.DiscountDAO;
import khoapc.discount.DiscountDTO;
import khoapc.user.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "UpdateDiscountCodeServlet", urlPatterns = {"/UpdateDiscountCodeServlet"})
public class UpdateDiscountCodeServlet extends HttpServlet {

    private static final String VIEW_CART_PAGE = "view_discount_cart.jsp";
    private static final String VIEW_CART = "viewcart.jsp";
    
    private static final Logger LOGGER = Logger.getLogger(UpdateDiscountCodeServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_CART;
        String discountCode = request.getParameter("txtDiscountCode").trim();
        try {
            HttpSession session = request.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("ACCOUNT");
            if (discountCode.trim().length() != 0) {
                DiscountDAO discountDAO = new DiscountDAO();
                int checkDiscountDAO = discountDAO.countCheck(userDTO.getEmail(), discountCode);
                if (checkDiscountDAO == 0) {
                    Date recentDate = new Date();
                    SimpleDateFormat smp = new SimpleDateFormat("MM/dd/yyyy");
                    String rencentDay = smp.format(recentDate);
                    DiscountDTO discountDTO = discountDAO.checkExpireDate(rencentDay, discountCode);
                    if (discountDTO != null) {
                        Date date = new Date();
                        if (date.before(discountDTO.getDate()) || discountDTO.getDate().equals(date)) {
                            CartDTO cart = (CartDTO) session.getAttribute("SHOPPING_CART");
                            CartDTO cartDiscoutDTO = new CartDTO();
                            for (CarDTO car : cart.getCars().values()) {
                                float discount = (float) discountDTO.getDiscount();
                                float discountPrice = (car.getPrice() - ((car.getPrice() * discount) / 100));
                                CarDTO carDTO = new CarDTO(car.getCarID(), car.getCarName(), car.getColor(), car.getCategory(), discountPrice, car.getQuantity());
                                cartDiscoutDTO.addCarToCart(carDTO);
                                session.setAttribute("DISCOUNT_SHOPPING_CART", cartDiscoutDTO);
                            }
                            url = VIEW_CART_PAGE;
                        } else {
                            request.setAttribute("DISCOUNT_ERROR", "THIS CODE IS OVERDUE");
                        }
                    } else {
                        request.setAttribute("DISCOUNT_ERROR", "INVALID DISCOUNT CODE");
                    }
                } else {
                    request.setAttribute("DISCOUNT_ERROR", "THIS DISCOUNT CODE ALREADY BEEN USED");
                }
            }
        } catch (Exception e) {
           LOGGER.error(e);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
