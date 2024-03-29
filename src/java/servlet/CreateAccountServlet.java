/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import registration.RegistrationCreateError;
import registration.RegistrationDAO;
import registration.RegistrationDTO;
import util.MyApplictionConstants;

/**
 *
 * @author N.Minh
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    private final String USER_LENGTH_ERROR = "Username is required typing from 6 to 30 characters";
    private final String USER_ERROR = "is existed in this system";
    private final String PASSWORD_LENGTH_ERROR = "Password is required typing from 6 to 30 characters";
    private final String CONFIRM_LENGTH_ERROR = "Confirm is required typing from 6 to 30 characters";
    private final String FULLNAME_LENGTH_ERROR = "Fullname is required typing from 6 to 30 characters";
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
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullName = request.getParameter("txtFullname");
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
//        String url = CREATE_NEW_ACCOUNT;
        
        String url = siteMaps.getProperty(MyApplictionConstants.CreateAccountFeather.CREATE_NEW_ACCOUNT);
        RegistrationCreateError errors = new RegistrationCreateError();
        boolean foundErr = false;

        try {
            //2. Verify user's error 
            if ((username.trim().length() < 6) || (username.trim().length() > 30)) {
                foundErr = true;
                errors.setUsernameLengthErr(USER_LENGTH_ERROR);
            }

            //if user types correct password format, confirm will be checked then.
            if ((password.trim().length() < 6) || (password.trim().length() > 20)) {
                foundErr = true;
                errors.setPasswordLengthErr(PASSWORD_LENGTH_ERROR);
            } else if (!confirm.trim().equals(password.trim())) {
                foundErr = true;
                errors.setConfirmNotMatch(CONFIRM_LENGTH_ERROR);
            }

            if ((fullName.trim().length() < 2) || (fullName.trim().length() > 50)) {
                foundErr = true;
                errors.setFullNameLengthErr(FULLNAME_LENGTH_ERROR);
            }

            if (foundErr) {//errors occur
                request.setAttribute("CREATE_ERRORS", errors);
            } else {//no error
                //3. Call DAO
                //3.1. new DAO
                RegistrationDAO dao = new RegistrationDAO();
                //3.2. call method of DAO
                RegistrationDTO account = new RegistrationDTO(username, password, fullName, false);
                boolean result = dao.createAccount(account);
                //4. Process result
                if (result) {
//                    url = LOGIN_PAGE;
                    url = siteMaps.getProperty(MyApplictionConstants.CreateAccountFeather.LOGIN_PAGE);
                }//insert successfully
            }//end no error
        }catch (SQLException ex) {
            String errMsg = ex.getMessage();
            log("CreateAccountServlet1_SQL: " + ex.getMessage());
            if (errMsg.contains("duplicate")) {
                errors.setUserNameIsExisted(username + " " + USER_ERROR);
                request.setAttribute("CREATE_ERRORS", errors);
                System.out.println(errors.getUsernameIsExisted());
            }
        } catch (NamingException ex) {
            log("CreateAccountServlet1_Naming: " + ex.getMessage());
        }
        finally {
            //There is still an attribute to be forward to .jsp 
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
