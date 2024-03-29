/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import util.DBHelper;

/**
 *
 * @author N.Minh
 */
public class OrderDetailDAO {

    private List<OrderDetailDTO> lists;

    public List<OrderDetailDTO> getLists() {
        return lists;
    }
    
    public boolean getOrderDetail(int orderDetailID) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            con = DBHelper.createConnection();
            if (con != null) {
                String sql = "SELECT productID, orderID, quantity, price, total "
                        + "FROM OrderDetail "
                        + "WHERE id = ? ";
                stm = con.prepareCall(sql);
                stm.setInt(1, orderDetailID);
                rs = stm.executeQuery();
                while (rs.next()) {                    
                    String productID = rs.getString("productID");
                    if (productID != null) {
                        result = true;
                    }
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean addOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.createConnection();
            if (con != null) {
                String sql = "INSERT INTO OrderDetail ("
                        + "id, productID, orderID, quantity, price, total"
                        + ") VALUES("
                        + "?, ?, ?, ?, ?, ?"
                        + ")";
                stm = con.prepareCall(sql);
                stm.setInt(1, orderDetailDTO.getId());
                stm.setString(2, orderDetailDTO.getProductID());
                stm.setString(3, orderDetailDTO.getOrderID());
                stm.setInt(4, orderDetailDTO.getQuantity());
                stm.setDouble(5, orderDetailDTO.getPrice());
                stm.setDouble(6, orderDetailDTO.getTotal());
                
                int effectRow = stm.executeUpdate();
                if (effectRow > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}
