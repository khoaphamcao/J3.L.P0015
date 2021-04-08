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
import khoapc.car.CarDAO;
import khoapc.car.CarDTO;
import khoapc.category.CategoryDAO;
import khoapc.category.CategoryDTO;
import khoapc.rentingdetail.RentingDetailDAO;
import khoapc.rentingdetail.RentingDetailDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "DefaultSearchServlet", urlPatterns = {"/DefaultSearchServlet"})
public class DefaultSearchServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DefaultSearchServlet.class);

    private static final String SEARCH_PAGE = "default_search.jsp";
    private static final String RENTING_PAGE = "renting_car.jsp";

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
        String txtIndex = request.getParameter("index");
        String txtRentingDate = request.getParameter("renting_date");
        String txtReturnDate = request.getParameter("return_date");
        HttpSession session = request.getSession();
        if(txtRentingDate != null && txtReturnDate != null){
            session.setAttribute("RENTING_DATE", txtRentingDate);
            session.setAttribute("RETURN_DATE", txtReturnDate);
        }

        List<CarDTO> updateCarList = null;
        int index = 0;
        String url = SEARCH_PAGE;
        try {
            String textRentingDate = (String) session.getAttribute("RENTING_DATE");
            String textReturnDate = (String) session.getAttribute("RETURN_DATE");
            
            SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
            Date rentingDate = smp.parse(textRentingDate);
            Date returnDate = smp.parse(textReturnDate);
            if (rentingDate.before(returnDate)) {
                if (txtIndex == null || txtIndex.equals("")) {
                    index = 1;
                } else {
                    index = Integer.parseInt(txtIndex);
                }
                int countCar = 0;
                int endPage = 0;
                CarDAO dao = new CarDAO();
                countCar = dao.countCar();
                endPage = dao.getEndPage(countCar);
                if (endPage != 0) {
                    request.setAttribute("END_PAGE", endPage);
                    List<CarDTO> list = dao.getPagingListAllCar(index);

                    RentingDetailDAO rentingDAO = new RentingDetailDAO();
                    updateCarList = new ArrayList<>();
                    for (CarDTO carDTO : list) {
                        RentingDetailDTO rentingDeltaiDTO = rentingDAO.getCarOnRentingCarDeltail(carDTO.getCarID(), rentingDate, returnDate);
                        if (rentingDeltaiDTO != null) {
                            int quantityTotal = carDTO.getQuantity() - rentingDeltaiDTO.getQuantity();
                            updateCarList.add(new CarDTO(carDTO.getCarID(), carDTO.getCarName(), carDTO.getColor(), carDTO.getYear(), carDTO.getCategory(), carDTO.getPrice(), quantityTotal));
                        } else {
                            updateCarList.add(carDTO);
                        }
                    }
                    CategoryDAO categoryDAO = new CategoryDAO();
                    List<CategoryDTO> listCate = categoryDAO.getPagingListCategory();
                    request.setAttribute("LIST_CATEGORY", listCate);
                    request.setAttribute("LIST_CAR", updateCarList);

                    url = SEARCH_PAGE;
                }
            }else{
                url = RENTING_PAGE;
                request.setAttribute("ERROR", "Return Date shouldn't BEFORE Renting Date");
            }
        } catch (NamingException ex) {
            LOGGER.error(ex);
        } catch (SQLException ex) {
            LOGGER.error(ex);
        } catch (ParseException ex) {
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
