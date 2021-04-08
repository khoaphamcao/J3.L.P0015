/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khoapc.rent.RentDAO;
import khoapc.rent.RentDTO;
import khoapc.shoppinghistory.ShoppingHistoryDAO;
import khoapc.shoppinghistory.ShoppingHistoryDTO;
import khoapc.user.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/SearchHistoryServlet"})
public class SearchHistoryServlet extends HttpServlet {

    private static final String VIEW_HISTORY_PAGE = "history.jsp";
    private static final String HISTORY_SERVLET = "HistoryServlet";

    private static final Logger LOGGER = Logger.getLogger(SearchHistoryServlet.class);
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
        String url = VIEW_HISTORY_PAGE;
        String searchOption = request.getParameter("searchOption");
        String carName = request.getParameter("carName");
        String rentingDate = request.getParameter("date");
        try {
            // Convert date => Date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            // Het Account username
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserDTO account = (UserDTO) session.getAttribute("ACCOUNT");
                if (account != null) {
                    String email = account.getEmail();
                    RentDAO dao = new RentDAO();
                    List<RentDTO> listOrder = null;
                    if (searchOption.equals("RentingDate")) {
                        if (rentingDate.trim().length() == 0) {
                            url = HISTORY_SERVLET;
                        } else {
                            Date date = format.parse(rentingDate);
                            listOrder = dao.getHistoryByDate(email, date);
                        }
                    } else if (searchOption.equals("CarName")) {
                        if (carName.trim().length() == 0) {
                            url = HISTORY_SERVLET;
                        } else {
                            listOrder = dao.getHistoryByCarName(email, carName);
                        }
                    }
                    if (listOrder != null) {
                        session.setAttribute("LIST_ORDER", listOrder);
                        List<ShoppingHistoryDTO> listCar = null;
                        session.setAttribute("LIST_ORDER", listOrder);
                        ShoppingHistoryDAO historyDAO = new ShoppingHistoryDAO();
                        ShoppingHistoryDTO hisDTO = new ShoppingHistoryDTO();
                        for (RentDTO rentDTO : listOrder) {
                            hisDTO = historyDAO.getCarHistoryByRentID(rentDTO);
                            if (hisDTO != null) {
                                if (listCar == null) {
                                    listCar = new ArrayList<>();
                                }
                                listCar.add(hisDTO);
                            }
                        }
                        request.setAttribute("HISTORY_CAR", listCar);
                    }
                }
            }
        } catch (ParseException ex) {
            LOGGER.error(ex);
        } catch (NamingException ex) {
            LOGGER.error(ex);
        } catch (SQLException ex) {
            LOGGER.error(ex);
        } catch (Exception ex) {
            LOGGER.error(ex);
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
