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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import registration.RegistrationDAO;
import registration.RegistrationDTO;
import util.MyApplictionConstants;
/**
 *
 * @author N.Minh
 */
@WebServlet(name = "StartUpServlet", urlPatterns = {"/StartUpServlet"})
public class StartUpServlet extends HttpServlet {
//    private final String ERROR_PAGE = "errors.html";
//    private final String LOGIN_PAGE = "login.html";
//    private final String SEARCH_PAGE = "search.jsp";
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //0. get current context
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
//        String url = LOGIN_PAGE;
        String url = siteMaps.getProperty(MyApplictionConstants.StartupFeature.LOGIN_PAGE);
        try {
            //1. Get all cookies
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                //2. Get username and password from the newest cookies
                Cookie newestCookie = cookies[cookies.length - 1];
                String username = newestCookie.getName();
                String password = newestCookie.getValue();
                //3. call DAO
                //3.1 new DAO
                RegistrationDAO dao = new RegistrationDAO();
                //3.2 call DAO method
                RegistrationDTO result = dao.checkLogin(username, password);
                
                if (result != null) {
//                    url = SEARCH_PAGE;
                    url = siteMaps.getProperty(MyApplictionConstants.StartupFeature.SEARCH_PAGE);
                }
            }//end, cookie has existed
        } catch (NamingException ex) {
            log("CreateAccountServlet1_Naming: " + ex.getMessage());
        } catch (SQLException ex) {
            log("CreateAccountServlet1_SQL: " + ex.getMessage());
        }finally{
            response.sendRedirect(url); //cookie is saved in file
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
