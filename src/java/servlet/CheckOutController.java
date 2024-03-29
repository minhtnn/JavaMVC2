/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import cart.CartObject;
import checkout.CheckOutObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
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
import order.OrderDAO;
import order.OrderDTO;
import orderDetail.OrderDetailDAO;
import orderDetail.OrderDetailDTO;
import product.ProductDAO;
import product.ProductDTO;
import util.MyApplictionConstants;

/**
 *
 * @author N.Minh
 */
@WebServlet(name = "CheckOutController", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

//    private final String VIEW_CART = "viewcart.jsp";
//    private final String CHECK_OUT_PAGE = "clientOrder.jsp";
//    private final String ERROR_PAGE = "errors.html";

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
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
//        String url = VIEW_CART;
        String url = siteMaps.getProperty(MyApplictionConstants.CheckOutFeather.VIEW_CART_PAGE);
        try {
            //1. Check cart
            HttpSession session = request.getSession(false);
            if (session != null) {
                CheckOutObject checkOutObject = (CheckOutObject) session.getAttribute("CheckOut");
                if (checkOutObject == null) {
                    checkOutObject = new CheckOutObject();
                }
                OrderDTO orderDTO = checkOutObject.addOrder();
                checkOutObject.initializeOrderDetails();
                CartObject cartObject = (CartObject) session.getAttribute("Cart");
                if (cartObject != null) {
                    Map<ProductDTO, Integer> items = cartObject.getItems();
                    if (items != null) {
                        for (Map.Entry<ProductDTO, Integer> entry : items.entrySet()) {
                            ProductDTO key = entry.getKey();
                            Integer value = entry.getValue();
                            checkOutObject.addOrderDetail(key.getId(), orderDTO.getOrderID(), value);
                        }
                    }
                    checkOutObject.updateTotalForOrder(orderDTO.getOrderID());
                }
                if (addOrderIntoDB(checkOutObject) && addOrderDetailIntoDB(checkOutObject)) {
                    updateBookQuantity(checkOutObject);
                    session.setAttribute("CHECK_OUT", checkOutObject);
//                    url = CHECK_OUT_PAGE;
                    url = siteMaps.getProperty(MyApplictionConstants.CheckOutFeather.CLIENT_ORDER_PAGE);
                    session.removeAttribute("Cart");
                } else {
//                    url = ERROR_PAGE;
                    url = siteMaps.getProperty(MyApplictionConstants.CheckOutFeather.ERROR_PAGE);
                }
            }
        } catch (SQLException ex) {
            log("CreateAccountServlet1_SQL: " + ex.getMessage());
            url = siteMaps.getProperty(MyApplictionConstants.CheckOutFeather.ERROR_PAGE);
        } catch (NamingException ex) {
            log("CreateAccountServlet1_Naming: " + ex.getMessage());
            url = siteMaps.getProperty(MyApplictionConstants.CheckOutFeather.ERROR_PAGE);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    public boolean addOrderIntoDB(CheckOutObject checkOutObject) throws NamingException, SQLException {
        boolean check = true;
        OrderDAO orderDAO = new OrderDAO();
        for (OrderDTO value : checkOutObject.getOrders().values()) {
            if (!orderDAO.addOrder(value)) {
                check = false;
                return check;
            }
        }
        return check;
    }

    public boolean addOrderDetailIntoDB(CheckOutObject checkOutObject) throws SQLException, NamingException {
        boolean check = true;
        OrderDetailDAO orderDAO = new OrderDetailDAO();
        for (OrderDetailDTO value : checkOutObject.getOrderDetails().values()) {
            if (!orderDAO.addOrderDetail(value)) {
                check = false;
                return check;
            }
        }
        return check;
    }

    public boolean updateBookQuantity(CheckOutObject checkOutObject) throws NamingException, SQLException {
        boolean check = false;
        ProductDAO productDAO = new ProductDAO();
        for (OrderDetailDTO value : checkOutObject.getOrderDetails().values()) {
            productDAO.updateBookQuantity(value.getProductID(), value.getQuantity());
        }
        return check;
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
