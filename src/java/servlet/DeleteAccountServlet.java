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
@WebServlet(name="DeleteAccountServlet", urlPatterns={"/DeleteAccountServlet"})
public class DeleteAccountServlet extends HttpServlet {
//    public final String ERROR_PAGE = "errors.html";
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
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
        String url = siteMaps.getProperty(MyApplictionConstants.DeleteAccountFeather.ERROR_PAGE);
        //1. Get all parameter
        String username = request.getParameter("pk");
        String searchValue = request.getParameter("lastSearchValue");
        try {
            RegistrationDAO dao = new RegistrationDAO();
            boolean result = dao.deleteAcount(username);
            if (result) {
                //.refresh --> call previous function (Search) again --> using url rewriting
                url = siteMaps.getProperty("searchLastNameController")
                        + "?btAction=Search"
                        + "&txtSearchValue=" + searchValue;
            }//end delete action is successfull
        } catch (NamingException ex) {
             log("CreateAccountServlet1_Naming: " + ex.getMessage());
        } catch (SQLException ex) {
             log("CreateAccountServlet1_SQL: " + ex.getMessage());
        }finally{
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
