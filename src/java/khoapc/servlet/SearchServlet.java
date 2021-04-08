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
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private static final String SEARCH_PAGE = "search.jsp";
    private static final String DEFAULT_SEARCH_SERVLET = "DefaultSearchServlet";

    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class);

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
        String txtCategory = request.getParameter("categoryOption");
        String txtCarName = request.getParameter("txtCarName");
        String txtAmount = request.getParameter("txtAmount");

        List<CarDTO> list = null;
        List<CarDTO> updateCarList = null;
        String url = SEARCH_PAGE;

        try {
            HttpSession session = request.getSession();
            String textRentingDate = (String) session.getAttribute("RENTING_DATE");
            String textReturnDate = (String) session.getAttribute("RETURN_DATE");
            SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
            Date rentingDate = smp.parse(textRentingDate);
            Date returnDate = smp.parse(textReturnDate);

            int index = 0;
            if (txtIndex == null || txtIndex.equals("")) {
                index = 1;
            } else {
                index = Integer.parseInt(txtIndex);
            }
            int countCar = 0;
            int endPage = 0;

            CarDAO dao = new CarDAO();
            if ("All".equals(txtCategory) || txtCategory.equals("")) {
                if (txtCarName.trim().equals("") && txtAmount.trim().equals("")) {
                    url = DEFAULT_SEARCH_SERVLET;
                } else if (txtCarName.trim().equals("")) {
                    countCar = dao.countAllCarByAmount(Integer.parseInt(txtAmount));
                    endPage = dao.getEndPage(countCar);
                    request.setAttribute("END_PAGE", endPage);
                    list = dao.getPagingListAllCarByAmount(index, Integer.parseInt(txtAmount));
                } else if (txtAmount.trim().equals("")) {
                    countCar = dao.countAllCarByName(txtCarName);
                    endPage = dao.getEndPage(countCar);
                    request.setAttribute("END_PAGE", endPage);
                    list = dao.getPagingListAllCarByName(index, txtCarName);
                } else {
                    countCar = dao.countAllCarByName_Amount(txtCarName, Integer.parseInt(txtAmount));
                    endPage = dao.getEndPage(countCar);
                    request.setAttribute("END_PAGE", endPage);
                    list = dao.getPagingListAllCarByName_Amount(index, txtCarName, Integer.parseInt(txtAmount));
                }
            } else {
                if (txtCarName.trim().equals("") && txtAmount.trim().equals("")) {
                    countCar = dao.countCarByCategory(txtCategory);
                    endPage = dao.getEndPage(countCar);
                    request.setAttribute("END_PAGE", endPage);
                    list = dao.getPagingListCarByCategory(index, txtCategory);
                } else if (txtCarName.trim().equals("")) {
                    countCar = dao.countCarByCategory_Amount(txtCategory, Integer.parseInt(txtAmount));
                    endPage = dao.getEndPage(countCar);
                    request.setAttribute("END_PAGE", endPage);
                    list = dao.getPagingListCarByCategory_Amount(index, txtCategory, Integer.parseInt(txtAmount));
                    request.setAttribute("LIST_CAR", list);
                } else if (txtAmount.trim().equals("")) {
                    countCar = dao.countCarByCategory_Name(txtCategory, txtCarName);
                    endPage = dao.getEndPage(countCar);
                    request.setAttribute("END_PAGE", endPage);
                    list = dao.getPagingListCarByCategory_Name(index, txtCategory, txtCarName);
                } else {
                    countCar = dao.countCarByCategory_Name_Amount(txtCategory, txtCarName, Integer.parseInt(txtAmount));
                    endPage = dao.getEndPage(countCar);
                    request.setAttribute("END_PAGE", endPage);
                    list = dao.getPagingListCarByCategory_Name_Amount(index, txtCategory, txtCarName, Integer.parseInt(txtAmount));
                }
            }
            CategoryDAO categoryDAO = new CategoryDAO();
            List<CategoryDTO> listCate = categoryDAO.getPagingListCategory();
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
            request.setAttribute("LIST_CAR", updateCarList);
            request.setAttribute("LIST_CATEGORY", listCate);
        } catch (NamingException ex) {
            LOGGER.error(ex);
        } catch (SQLException ex) {
            LOGGER.error(ex);
        } catch (ParseException ex) {
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
