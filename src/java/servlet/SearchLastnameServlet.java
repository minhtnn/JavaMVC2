/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(name = "SearchLastnameServlet", urlPatterns = {"/SearchLastnameServlet"})
public class SearchLastnameServlet extends HttpServlet {
//    private final String ERROR_PAGE = "errors.html";
//    private final String SEARCH_PAGE = "search.html";
//    private final String RESULT_SEARCH_PAGE = "search.jsp";
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
        String searchValue = request.getParameter("txtSearchValue");
        //0. get current context
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
//        String url = RESULT_SEARCH_PAGE;
        String url = siteMaps.getProperty(MyApplictionConstants.SearchLastNameFeature.SEARCH_PAGE);
        try {
            if (!searchValue.trim().isEmpty()) {
                //2. call DAO
                //2. call new DAO
                RegistrationDAO dao = new RegistrationDAO();
                //2.2 call method
                dao.searchLastname(searchValue);
                //3. Process
                List<RegistrationDTO> result = dao.getAccounts();
//                url = RESULT_SEARCH_PAGE;
                url = siteMaps.getProperty(MyApplictionConstants.SearchLastNameFeature.SEARCH_PAGE);
                request.setAttribute("SEARCH_RESULT", result);
                //
            }//end user typed valid value
//        } catch (ClassNotFoundException ex) {
//           Logger.getLogger(SearchLastnameServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            log("CreateAccountServlet1_Naming: " + ex.getMessage());
            url = siteMaps.getProperty(MyApplictionConstants.SearchBookFeature.ERROR_PAGE);
        } catch (SQLException ex) {
            log("CreateAccountServlet1_SQL: " + ex.getMessage());
            url = siteMaps.getProperty(MyApplictionConstants.SearchBookFeature.ERROR_PAGE);
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
