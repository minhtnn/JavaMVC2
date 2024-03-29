/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkout;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import order.OrderDAO;
import order.OrderDTO;
import orderDetail.OrderDetailDAO;
import orderDetail.OrderDetailDTO;
import product.ProductDAO;
import product.ProductDTO;

/**
 *
 * @author N.Minh
 */
public class CheckOutObject {

    private Map<Integer, OrderDetailDTO> orderDetails;
    private Map<String, OrderDTO> orders;

    /**
     * @return the orderDetailsMap
     */
    public Map<Integer, OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    /**
     * @return the orders
     */
    public Map<String, OrderDTO> getOrders() {
        return orders;
    }

    public void initializeOrders() {
        if (orders == null) {
            this.orders = new HashMap<>();
        }
    }

    public void initializeOrderDetails() {
        if (orderDetails == null) {
            this.orderDetails = new HashMap<>();
        }
    }

    private boolean IsOrderIDExist(String orderID) throws SQLException {
        boolean check = false;
        OrderDAO orderDAO = new OrderDAO();
        OrderDTO orderDTO = orderDAO.getOrderInfor(orderID);
        if (orderDTO != null) {
            check = true;
        }
        return check;
    }

    public String createOrderID(int length, String string) throws SQLException {
        String id = "";
        int i = 1;
        if (length > string.length()) {
            int restOfID = length - string.length();
            id = String.format(string + "%0" + restOfID + "d", i);
            if (IsOrderIDExist(id)) {
                do {
                    ++i;
                    id = String.format(string + "%0" + restOfID + "d", i);
                } while (IsOrderIDExist(id));
            }
        }
        return id;
    }

    public OrderDTO addOrder() throws NamingException, SQLException {
        initializeOrders();
        String id = createOrderID(5, "OO");
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date dateTime = new java.sql.Date(currentDate.getTime());
        double total = 0.0;
        OrderDTO dto = new OrderDTO(id, dateTime, total);
        this.orders.put(id, dto);
        return dto;
    }

    public double findOrderTotalInOrderDetail(String orderID) {
        double orderTotal = 0.0;
        for (OrderDetailDTO value : orderDetails.values()) {
            if (value.getOrderID().equals(orderID)) {
                orderTotal += value.getTotal();
            }
        }
        return orderTotal;
    }

    public void updateTotalForOrder(String orderID) {
        if (this.orders.containsKey(orderID)) {
            double newTotal = findOrderTotalInOrderDetail(orderID);
            this.orders.get(orderID).setTotal(newTotal);
        }
    }

    private boolean isOrderDetailIDExistInDB(int orderDetailID) throws SQLException, NamingException {
        boolean check = false;
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        if (orderDetailDAO.getOrderDetail(orderDetailID)) {
            check = true;
        }
        return check;
    }

    private int createOrderDetailID() throws SQLException, NamingException {
        int i = 1;

        if (orderDetails.containsKey(i) || isOrderDetailIDExistInDB(i)) {
            do {
                ++i;
            } while (orderDetails.containsKey(i) || isOrderDetailIDExistInDB(i));
        }
        return i;
    }

    public void addOrderDetail(String productID, String orderID, int quantity) throws SQLException, NamingException {
        if (productID == null) {
            return;
        }
        if (productID.trim().isEmpty()) {
            return;
        }
        if (quantity < 0) {
            return;
        }
        int orderDetailID = createOrderDetailID();
        OrderDetailDTO orderDetailDTO = null;
        ProductDAO productDAO = new ProductDAO();
        ProductDTO productDTO = productDAO.getBookInfoByID(productID);
        orderDetailDTO = new OrderDetailDTO(orderDetailID, productID, orderID,
                quantity, productDTO.getUnitPrice(), (quantity * productDTO.getUnitPrice()));

        if (IsproductIDExist(productID)) {
            int newQuantity = quantity + this.getOrderDetailValues(productID).getQuantity();
            orderDetailDTO = new OrderDetailDTO(orderDetailID, productID, orderID,
                    newQuantity, productDTO.getUnitPrice(), (newQuantity * productDTO.getUnitPrice()));
        }
        if (orderDetailDTO != null) {
            this.orderDetails.put(orderDetailID, orderDetailDTO);
        }

    }

    private boolean IsproductIDExist(String productID) {
        boolean check = false;
        for (OrderDetailDTO value : orderDetails.values()) {
            if (value.getProductID().equals(productID)) {
                check = true;
                return check;
            }
        }
        return check;
    }

    private OrderDetailDTO getOrderDetailValues(String productID) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        for (OrderDetailDTO value : orderDetails.values()) {
            if (value.getProductID().equals(productID)) {
                orderDetailDTO = value;
            }
        }
        return orderDetailDTO;
    }
}
