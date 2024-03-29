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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import registration.RegistrationDAO;
import util.MyApplictionConstants;

/**
 *
 * @author N.Minh
 */
@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {
//   private final String ERROR_PAGE = "errors.html";
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String chkAdmin = request.getParameter("chkAdmin");
        boolean role = false;
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
        
        if (chkAdmin != null) {
            role = true;
        }
        String searchValue = request.getParameter("lastSearchValue");
//        String url = ERROR_PAGE;
        String url = siteMaps.getProperty(MyApplictionConstants.UpdateAccountFeature.ERROR_PAGE);
        try {
//Call DAO
            //2.1 new DAO
            RegistrationDAO dao = new RegistrationDAO();
            //2.2 DAO Method
            boolean result = dao.updateAccount(username, password, role);
            if (result) {
                url = siteMaps.getProperty("searchLastNameController")
                        + "?btAction=Search"
                        + "&txtSearchValue=" + searchValue;
            }
        } catch (NamingException ex) {
            log("CreateAccountServlet1_Naming: " + ex.getMessage());
            url = siteMaps.getProperty(MyApplictionConstants.SearchBookFeature.ERROR_PAGE);
        } catch (SQLException ex) {
            log("CreateAccountServlet1_SQL: " + ex.getMessage());
            url = siteMaps.getProperty(MyApplictionConstants.SearchBookFeature.ERROR_PAGE);
        } finally {
            response.sendRedirect(url);
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
