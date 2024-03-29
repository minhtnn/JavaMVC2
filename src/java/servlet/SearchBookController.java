/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import product.ProductDAO;
import product.ProductDTO;
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
import util.MyApplictionConstants;

/**
 *
 * @author N.Minh
 */
@WebServlet(name = "SearchBookController", urlPatterns = {"/SearchBookController"})
public class SearchBookController extends HttpServlet {
//    private final String ERROR_PAGE = "errors.html";
//    private final String SEARCH_BOOK_PAGE = "bookStore.html";
//    private final String RESULT_SEARCH_BOOK_PAGE = "bookStore.jsp";

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
        String searchBookValue = request.getParameter("txtSearchBookValue");
        //0. get current context
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
//        String url = RESULT_SEARCH_BOOK_PAGE;
        String url = siteMaps.getProperty(MyApplictionConstants.SearchBookFeature.BOOKSTORE_PAGE);
        try {
            if (!searchBookValue.trim().isEmpty()) {
                //2. Call DAO
                //2.1. Call new DAO
                ProductDAO dao = new ProductDAO();
                //2.2. Call DAO method
                dao.getBookInfoByName(searchBookValue);
                //3. Process
                List<ProductDTO> books = dao.getBooks();
                request.setAttribute("BOOKS_RESULT_SET", books);
            }
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
