/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
/**
 *
 * @author N.Minh
 */
public class DBHelper implements Serializable{
    public static Connection createConnection() throws /*ClassNotFoundException*/NamingException, SQLException{
        //1. Get current con
        Context currentContext = new InitialContext();
        //2. Load tomcat context
        Context tomcatContext = (Context) currentContext.lookup("java:comp/env");
        //3. Look up our data source
        DataSource ds = (DataSource)tomcatContext.lookup("SE1708DS");
        //4. Open connection from DS
        Connection con = ds.getConnection();
        
        return con;
        //1. Load driver
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        String url = "jdbc:sqlserver://localhost:1433;databaseName=SE1708MVC2";
//        Connection con = DriverManager.getConnection(url, "sa", "123456");
//        return con;
    }
}
