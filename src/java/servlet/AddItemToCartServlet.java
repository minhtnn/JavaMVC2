/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import cart.CartObject;
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
import product.ProductCreateError;
import product.ProductDAO;
import tool.Validation;
import util.MyApplictionConstants;

/**
 *
 * @author N.Minh
 */
@WebServlet(name = "AddItemToCartServlet", urlPatterns = {"/AddItemToCartServlet"})
public class AddItemToCartServlet extends HttpServlet {
//    private final String BOOKSTORE_PAGE = "bookStore.jsp";
//    private final String ERROR_PAGE = "errors.html";

    private String ADD_BOOK_MORE_THAN_REMAIN_ERROR = "Add book more than the remain!";
    private String INPUT_QUANTITY_ERROR = "Only except number!";

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
        String addBookQuantity = request.getParameter("txtAddQuantity");
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
//        String url = BOOKSTORE_PAGE;
        String url = siteMaps.getProperty(MyApplictionConstants.AddItemToCartFeather.BOOKSTORE_PAGE);
        ProductCreateError pce = new ProductCreateError();
        boolean check = false;
        int quantity = 0;
        if (Validation.checkPositiveIntegerNumber(addBookQuantity)) {
            quantity = Integer.parseInt(addBookQuantity);
        } else {
            check = true;
            pce.setInputQuantityErr(INPUT_QUANTITY_ERROR);
        }
        try {
            //1. Cust goes to cart place
            HttpSession session = request.getSession();
            //2. Cust takes his/her cart  
            CartObject cart = (CartObject) session.getAttribute("Cart");
            if (cart == null) {
                cart = new CartObject();
            }//cart has initialized
            //3. Cust drops item to cart
            String bookID = request.getParameter("txtBookID");
            ProductDAO productDAO = new ProductDAO();
            if (quantity > productDAO.getBookQuantityByID(bookID)) {
                check = true;
                pce.setAddBookMoreThanRemainErr(ADD_BOOK_MORE_THAN_REMAIN_ERROR);
            }
            if (check) {
                request.setAttribute("CREATE_ADD_BOOKS_ERROR", pce);
            } else {
                //Check quanity in DB whether is equal to add items
                cart.addItemToCart(bookID, quantity);
                //Always update attribute
                session.setAttribute("Cart", cart);

            }
            //4. Cust goes to shopping book by returning bookStore page
            url = siteMaps.getProperty("searchBookController")
                    + "?btAction=Find"
                    + "&txtSearchBookValue=" + searchBookValue;
        } catch (NamingException ex) {
            log("CreateAccountServlet1_Naming: " + ex.getMessage());
        } catch (SQLException ex) {
            log("CreateAccountServlet1_SQL: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
//            response.sendRedirect(url);
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
