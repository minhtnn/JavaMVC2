/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.util.Properties;
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
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

//    private final String LOGIN_PAGE = "login.html";
//    private final String LOGIN_CONTROLLER = "LoginServlet";
//    private final String LOG_OUT_CONTROLLER = "LogOutServlet";
//    private final String SEARCH_LAST_NAME = "SearchLastnameServlet";
//    private final String DELETE_ACCOUNT_CONTROLLER = "DeleteAccountServlet";
//    private final String UPDATE_ACCOUNT_CONTROLLER = "UpdateAccountServlet";
//    private final String START_UP_CONTROLLER = "StartUpServlet";
//    private final String ADD_ITEM_TO_CART_CONTROLLER = "AddItemToCartServlet";
//    private final String VIEW_CART_PAGE = "viewcart.jsp";
//    private final String REMOVE_CART_ITEMS= "RemoveCartItemsController";
//    private final String SEARCH_BOOK = "SearchBookController";
//    private final String CREATE_ACCOUNT_CONTROLLER = "CreateAccountServlet";
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
        //0. get current context
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
//        String url = siteMaps.getProperty(LOGIN_PAGE);
        String url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.LOGIN_PAGE);
        //1. Which button did user click?
        String button = request.getParameter("btAction");
        try {
//            if (button == null) {           //Welcome file trigger
//                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.START_UP_CONTROLLER);
//            } else if (button.equals("Login")) {
////                url = LOGIN_CONTROLLER;
//                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.LOGIN_CONTROLLER);
//            } else if (button.equals("Search")) {
////                url = SEARCH_LAST_NAME;
//                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.SEARCH_LASTNAME_CONTROLLER);
//            } else if (button.equals("Delete")) {
//                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.DELETE_ACCOUNT_CONTROLLER);
//            } else if (button.equals("Update")) {
//                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.UPDATE_ACCOUNT_CONTROLLER);
//            } else if (button.equals("Log out")) {
//                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.LOGOUT_CONTROLLER);
//            } else
        if (button.equals("Add Book to your cart")) {
                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.ADD_ITEM_TO_CART_CONTROLLER);
            } else if (button.equals("View your cart")) {
                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.VIEW_CART_PAGE);
            } else if (button.equals("Remove selected items")) {
                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.REMOVE_ITEMS_FROM_CART_CONTROLLER);
//            } else if (button.equals("Find")) {
//                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.SEARCH_BOOK_CONTROLLER);
//            } else if (button.equals("Create new account")) {
//                url = siteMaps.getProperty(MyApplictionConstants.DispatchFeather.CREATE_ACCOUNT_CONTROLLER);
            }else if (button.equals("Check out")) {
                url = "CheckOutController";
            }
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
