/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product;

import java.io.Serializable;
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
public class ProductDAO implements Serializable {

    private List<ProductDTO> books;

    public List<ProductDTO> getBooks() {
        return books;
    }

    public ProductDTO getBookInfoByID(String bookID)
            throws /*ClassNotFoundException*/ NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ProductDTO book = null;
        try {
            //1. Get connection
            con = DBHelper.createConnection();

            if (con != null) {
                //2. Create SQL statement
                String sql = "SELECT name , quantity , unitPrice , status "
                        + "FROM Product "
                        + "WHERE id = ? ";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, (bookID));
                //4. Execute querry
                rs = stm.executeQuery();
                //5. Process
                //5.1. Get data from result set
                while (rs.next()) {
                    String bookName = rs.getNString("name");
                    int quantity = rs.getInt("quantity");
                    double unitPrice = rs.getDouble("unitPrice");
                    boolean status = rs.getBoolean("status");
                    book = new ProductDTO(bookID, bookName, quantity, unitPrice, status);
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
        return book;
    }
    
    public int getBookQuantityByID(String bookID)
            throws /*ClassNotFoundException*/ NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int quantity = 0;
        try {
            //1. Get connection
            con = DBHelper.createConnection();

            if (con != null) {
                //2. Create SQL statement
                String sql = "SELECT quantity "
                        + "FROM Product "
                        + "WHERE id = ? ";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, (bookID));
                //4. Execute querry
                rs = stm.executeQuery();
                //5. Process
                //5.1. Get data from result set
                while (rs.next()) {
                    quantity = rs.getInt("quantity");
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
        return quantity;
    }

    public void getBookInfoByName(String searchBookValue)
            throws /*ClassNotFoundException*/ NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Get connection
            con = DBHelper.createConnection();

            if (con != null) {
                //2. Create SQL statement
                String sql = "SELECT id , name , quantity , unitPrice "
                        + "FROM Product "
                        + "WHERE name LIKE ? ";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, ("%" + searchBookValue + "%"));
                //4. Execute querry
                rs = stm.executeQuery();
                //5. Process
                //5.1. Get data from result set
                while (rs.next()) {
                    String bookID = rs.getString("id");
                    String bookName = rs.getNString("name");
                    int quantity = rs.getInt("quantity");
                    double unitPrice = rs.getDouble("unitPrice");
                    boolean status = false;
                    if (quantity > 0) {
                        status = true;
                    }
                    ProductDTO book = new ProductDTO(bookID, bookName, quantity, unitPrice, status);
                    //5.2. Set data into DTO
                    if (books == null) {
                        books = new ArrayList<>();
                    }
                    //5.3. Add DTO into list
                    this.books.add(book);
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
    }
    
    public boolean updateBookQuantity(String bookID, int quantity) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.createConnection();
            if (con != null) {
                String sql = "UPDATE Product "
                        + "SET quantity = quantity - ? "
                        + "WHERE id = ? ";
                stm = con.prepareCall(sql);
                stm.setInt(1, quantity);
                stm.setString(2, bookID);
                
                int effectedRow = stm.executeUpdate();
                if (effectedRow > 0 ) {
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
