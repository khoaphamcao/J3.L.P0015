/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khoapc.ultities.VerifyRecaptcha;
import khoapc.user.UserDAO;
import khoapc.user.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String DEFAULT_SEARCH_SERVLET = "DefaultSearchServlet";

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
        String url = LOGIN_PAGE;
        String email = request.getParameter("txtEmail").trim();
        String password = request.getParameter("txtPassword").trim();

        String gRecapcha = request.getParameter("g-recaptcha-response");

        try {
            UserDAO dao = new UserDAO();
            UserDTO dto = dao.checkAccount(email, password);
            boolean checkVerifyCapcha = VerifyRecaptcha.verify(gRecapcha);
            HttpSession session = request.getSession();
            if (dto != null) {
                if (checkVerifyCapcha) {
                    session.setAttribute("ACCOUNT", dto);
                    String role = dto.getRole();
                    if (role != null) {
                        url = DEFAULT_SEARCH_SERVLET;
                    }
                } else {
                    String error = "Please check reCapcha";
                    request.setAttribute("ERROR", error);
                }
            } else {
                String error = "Invalid name or password!";
                request.setAttribute("ERROR", error);
                url = LOGIN_PAGE;
            }
        } catch (Exception e) {
            LOGGER.error(e);;
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
