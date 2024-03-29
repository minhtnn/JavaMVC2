/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registration;

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
public class RegistrationDAO implements Serializable {

//    public boolean checkLogin(String username, String password) 
//            throws /*ClassNotFoundException*/NamingException, SQLException {
    public RegistrationDTO checkLogin(String username, String password) 
            throws /*ClassNotFoundException*/NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        RegistrationDTO result = null;
        try {
            //1. Create connection
            con = DBHelper.createConnection();
            if (con != null) {
                //2. Create SQL string
                String sql = "Select lastname, isAdmin "
                        + "From Registration "
                        + "Where username = ? "
                        + "And password = ? ";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4. Execute querry
                rs = stm.executeQuery();

                //5. Process
                if (rs.next()) {
                    //5.1. Get data from result set
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    
                    //5.2 Set data in DTO
                    result = new RegistrationDTO(username, null, fullName, role);
                }//end username and password are existed
            }// end connection available

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
    
    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccounts() {
        return accounts;
    }
    
    public void searchLastname(String searchValue) throws /*ClassNotFoundException*/ NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Create connection
            con = DBHelper.createConnection();
            if (con != null) {
                //2. Create SQL string, url
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname Like ? ";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                
                //4. Execute querry
                rs = stm.executeQuery();

                //5. Process
                while (rs.next()) {                    
                    //5.1 get data from rs
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullName = rs.getString("lastname");
                    boolean role =  rs.getBoolean("isAdmin");

                    RegistrationDTO dto = new RegistrationDTO(username, password, fullName, role);
                    //5.2 set data into DTO
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    }
                    //5.3 add DTO into list
                    this.accounts.add(dto);
                }
                //end traverse rs to get data
            }// end connection available

        } catch (SQLException ex) {
            ex.printStackTrace();
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
    
    public boolean deleteAcount(String username)
            throws /*ClassNotFoundException*/NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //1. Create connection
            con = DBHelper.createConnection();
            if (con != null) {
                //2. Create SQL string
                String sql = "Delete From Registration "
                        + "Where username = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //4. Execute querry
                int effectRows = stm.executeUpdate();

                //5. Process
                if (effectRows > 0) {
                    result = true;
                }
                //end username and password are existed
            }// end connection available

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
    
    public boolean updateAccount(String username, String password, boolean role)
            throws /*ClassNotFoundException*/NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //1. Create connection
            con = DBHelper.createConnection();
            if (con != null) {
                //2. Create SQL string
                String sql = "Update Registration "
                        + "SET password = ?, isAdmin = ? "
                        + "Where username = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, role);
                stm.setString(3, username);
                //4. Execute querry
                int effectRows = stm.executeUpdate();
                //5. Process
                if (effectRows > 0) {
                    result = true;
                }
                //end username and password are existed
            }// end connection available

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
    public boolean createAccount(RegistrationDTO account) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //1. Create connection
            con = DBHelper.createConnection();
            if (con != null) {
                //2. Create SQL string
                String sql = "Insert into Registration("
                        + "username, password, lastname, isAdmin"
                        + ") Values("
                        + "?, ?, ?, ?"
                        + ")";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, account.getUsername());
                stm.setString(2, account.getPassword());
                stm.setNString(3, account.getFullName());
                stm.setBoolean(4, account.isRole());
                //4. Execute querry
                int effectRows = stm.executeUpdate();
                //5. Process
                if (effectRows > 0) {
                    result = true;
                }
                //end username and password are existed
            }// end connection available
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
