/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import util.DBHelper;

/**
 *
 * @author N.Minh
 */
public class OrderDAO {
    private List<OrderDTO> lists;

    /**
     * @return the lists
     */
    public List<OrderDTO> getLists() {
        return lists;
    }
    
    public OrderDTO getOrderInfor(String orderID) throws SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        OrderDTO orderDTO = null;
        try {
            con = DBHelper.createConnection();
            if (con!= null) {
                String sql = "SELECT oDate, total "
                        + "FROM tbl_Order "
                        + "WHERE id = ? ";
                stm = con.prepareCall(sql);
                stm.setString(1, orderID);
                
                rs = stm.executeQuery();
                
                while (rs.next()) {                    
                    java.sql.Date orderDate = rs.getDate("oDate");
                    double orderTotal = rs.getDouble("total");
                    orderDTO = new OrderDTO(orderID, orderDate, orderTotal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch(NamingException e){
            e.printStackTrace();
        }finally{
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
        return orderDTO;
    }
//    public void getOrderInfor(String orderID) throws SQLException{
//        Connection con = null;
//        PreparedStatement stm = null;
//        ResultSet rs = null;
//        try {
//            con = DBHelper.createConnection();
//            if (con!= null) {
//                String sql = "SELECT oDate, total "
//                        + "FROM tbl_Order "
//                        + "WHERE id = ? ";
//                stm = con.prepareCall(sql);
//                stm.setString(1, orderID);
//                
//                rs = stm.executeQuery();
//                
//                while (rs.next()) {                    
//                    java.sql.Date orderDate = rs.getDate("oDate");
//                    double orderTotal = rs.getDouble("total");
//                    OrderDTO dto = new OrderDTO(orderID, orderDate, orderTotal);
//                    if (lists != null) {
//                        lists = new ArrayList<>();
//                    }
//                    this.lists.add(dto);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }catch(NamingException e){
//            e.printStackTrace();
//        }finally{
//            if (rs != null) {
//                rs.close();
//            }
//            if (stm != null) {
//                stm.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//        }
//    }
    
    public boolean addOrder(OrderDTO orderDTO) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.createConnection();
            if (con != null) {
                String sql = "INSERT INTO tbl_Order ("
                        + "id, oDate, total"
                        + ") VALUES ("
                        + "?, GETDATE(), ? "
                        + ")";
                stm = con.prepareCall(sql);
                stm.setString(1, orderDTO.getOrderID());
                stm.setDouble(2, orderDTO.getTotal());
                int effectedRow = stm.executeUpdate();
                if (effectedRow > 0) {
                    result = true;
                }
            }
        }finally{
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
