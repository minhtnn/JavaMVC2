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
import javax.servlet.http.HttpSession;
import registration.RegistrationDAO;
import registration.RegistrationDTO;
import util.MyApplictionConstants;

/**
 *
 * @author N.Minh
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
//    private final String SEARCH_PAGE = "search.jsp";
//    private final String INVALID_PAGE = "invalid.html";
//    private final String ERROR_PAGE = "errors.html";
    private final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";
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
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        String button = request.getParameter("btAction");
        //0. Get current 
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
        String url = siteMaps.getProperty(MyApplictionConstants.LoginFeather.INVALID_PAGE);
        //1. Get all parameters
        String username = request.getParameter("txtName");
        String password = request.getParameter("txtPassword");
        try {
            if (button.equals("Login")) {
                //2. call DAO
                //2.1 new DAO object 
                RegistrationDAO dao = new RegistrationDAO();
                //2.2 call method of DAO
                RegistrationDTO result = dao.checkLogin(username, password);
                //3 Process result
                if (result != null) {
                    url = siteMaps.getProperty(MyApplictionConstants.LoginFeather.SEARCH_PAGE);
//                    url = SEARCH_PAGE;
                    HttpSession session = request.getSession(true); //Login successfully must save user
                    session.setAttribute("USER_INFO", result);
//                    Cookie cookie = new Cookie(username, password); //password is encrypt
//                    cookie.setMaxAge(60 * 10);
//                    response.addCookie(cookie);
                }else{
                    url = siteMaps.getProperty(MyApplictionConstants.LoginFeather.INVALID_PAGE);
                }
            }
        } catch (SQLException ex) {
            log("CreateAccountServlet1_Naming: " + ex.getMessage());
            url = siteMaps.getProperty(MyApplictionConstants.LoginFeather.ERROR_PAGE);
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
        } catch (NamingException ex) {
            log("CreateAccountServlet1_Naming: " + ex.getMessage());
            url = siteMaps.getProperty(MyApplictionConstants.LoginFeather.ERROR_PAGE);
        } finally {
            response.sendRedirect(url);
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
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
