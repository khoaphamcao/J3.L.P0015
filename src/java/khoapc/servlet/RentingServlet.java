/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khoapc.car.CarDAO;
import khoapc.car.CarDTO;
import khoapc.cart.CartDTO;
import khoapc.rent.RentDAO;
import khoapc.rent.RentDTO;
import khoapc.rentingdetail.RentingDetailDAO;
import khoapc.rentingdetail.RentingDetailDTO;
import khoapc.user.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "RentingServlet", urlPatterns = {"/RentingServlet"})
public class RentingServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RentingServlet.class);
    private static final String VIEW_CART = "viewcart.jsp";

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

        
        String txtToltal = request.getParameter("total");
        String url = VIEW_CART;
        try {
            List<String> listError = new ArrayList<>();
            boolean check = true;
            HttpSession session = request.getSession(false);
            UserDTO userDTO = (UserDTO) session.getAttribute("ACCOUNT");

            String textRentingDate = (String) session.getAttribute("RENTING_DATE");
            String textReturnDate = (String) session.getAttribute("RETURN_DATE");

            SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
            Date rentingDate = smp.parse(textRentingDate);
            Date returnDate = smp.parse(textReturnDate);

            CartDTO cart = (CartDTO) session.getAttribute("SHOPPING_CART");
            CarDAO dao = new CarDAO();
            RentingDetailDAO rentingDAO = new RentingDetailDAO();
            int quantity = 0;
            if (cart != null) {
                for (CarDTO carDTO : cart.getCars().values()) {
                    RentingDetailDTO rentingDTO = rentingDAO.getCarOnRentingCarDeltail(carDTO.getCarID(), rentingDate, returnDate);
                    if (rentingDTO != null) {
                        quantity = dao.getCarQuantityByID(carDTO.getCarID()) - rentingDTO.getQuantity();
                    } else {
                        quantity = dao.getCarQuantityByID(carDTO.getCarID());
                    }

                    if (quantity < carDTO.getQuantity()) {
                        String ERROR = " Car's: " + carDTO.getCarName() + " Quantity is have " + quantity + " left";
                        listError.add(ERROR);
                        check = false;
                    }
                }
            }
            if (check) {
                String email = userDTO.getEmail();
                RentDAO rentDAO = new RentDAO();
                if (email != null) {
                    int countRentID = rentDAO.countRentID();
                    String rentID = "R-1";
                    if (countRentID != 0) {
                        countRentID++;
                        rentID = "R-" + countRentID;
                    }

                    if (txtToltal != null) {
                        float total = Float.parseFloat(txtToltal);
                        RentDTO rentDTO = new RentDTO(rentID, email, total);
                        String discountCode = request.getParameter("txtDiscountCode");
                        if (discountCode.trim().length() == 0) {
                            Map<String, CarDTO> items = cart.getCars();
                            if (items != null) {
                                boolean result = rentDAO.createRent(rentDTO, items, rentingDate, returnDate);
                                if (result) {
                                    session.removeAttribute("SHOPPING_CART");
                                    session.removeAttribute("DISCOUNT_SHOPPING_CART");
                                }
                            }
                        } else {
                            CartDTO cartDiscount = (CartDTO) session.getAttribute("DISCOUNT_SHOPPING_CART");
                            Map<String, CarDTO> items = cartDiscount.getCars();
                            if (items != null) {
                                boolean result = rentDAO.createRentDiscount(rentDTO, items, rentingDate, returnDate, discountCode);
                                if (result) {
                                    session.removeAttribute("SHOPPING_CART");
                                    session.removeAttribute("DISCOUNT_SHOPPING_CART");
                                }
                            }
                        }
                    }
                }
            }
            request.setAttribute("LIST_ERR", listError);
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
