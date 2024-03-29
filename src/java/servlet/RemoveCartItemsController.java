/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import cart.CartObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import product.ProductDTO;

/**
 *
 * @author N.Minh
 */
@WebServlet(name = "RemoveCartItemsController", urlPatterns = {"/RemoveCartItemsController"})
public class RemoveCartItemsController extends HttpServlet {
   
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
        //1. Cust goes to cart place
        HttpSession session = request.getSession(false); //timeout
        if (session != null) {
            //2. Cust takes his/her cart
            CartObject cart = (CartObject) session.getAttribute("Cart");
            if (cart != null) {
                //3. Cust gets items
                Map<ProductDTO, Integer> items = cart.getItems();
                if (items != null) {
                    //4. Cust chooses all removing items
                    String[] selectedItems = request.getParameterValues("chkItem");
                    if (selectedItems != null) {
                        //5. Remove items
                        for (String selectedItem : selectedItems) {
                            cart.removeItemFromCart(selectedItem, 1);
                        }
                        session.setAttribute("Cart", cart);
                    }//cust must be choose
                }//end items exist
            }//end cart must exist
        }//end cart place must exist
        //6. Refresh --> call previous function again (View your cart)
        String urlRewriting = "DispatchServlet"
                + "?btAction=View your cart";
        response.sendRedirect(urlRewriting);
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
