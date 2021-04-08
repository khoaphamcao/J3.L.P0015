/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khoapc.user.UserDAO;
import khoapc.user.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {

    private static final String SIGN_UP_PAGE = "signup.jsp";
    private static final String VERIFY_SERVLET = "VerifyServlet";
    private static final Logger LOGGER = Logger.getLogger(SignUpServlet.class);
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
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        String txtFullname = request.getParameter("txtFullname").trim();
        String txtPhone = request.getParameter("txtPhone").trim();
        String txtAddress = request.getParameter("txtAddress").trim();
        String txtEmail = request.getParameter("txtEmail").trim();
        String txtPassword = request.getParameter("txtPassword").trim();
        String txtRepeatPassword = request.getParameter("txtRepeatPassword").trim();

        Date createDate = new Date();
        String role = "user";

        String url = SIGN_UP_PAGE;
        try {
            String notification = null;
            UserDTO dto = new UserDTO(txtEmail, txtPhone, txtFullname, txtPassword, txtAddress, createDate, role);
            UserDAO dao = new UserDAO();
            boolean checkDupID = dao.checkDupAccount(txtEmail);
            if (checkDupID) {
                if (txtPassword.equals(txtRepeatPassword)) {
                    boolean check = dao.createAccount(dto);
                    if (check) {
                        notification = "Create Account Successful!";
                        request.setAttribute("NOTIFICATION", notification);
                        url = VERIFY_SERVLET;

                    } else {
                        notification = "Create Account Fail";
                        request.setAttribute("NOTIFICATION", notification);
                    }
                } else {
                    notification = "Your repeat pasword is not correct";
                    request.setAttribute("NOTIFICATION", notification);
                }
            } else {
                notification = "This Email have been used! Please try another email";
                request.setAttribute("NOTIFICATION", notification);
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
