/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.ultities;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Khoa Pham
 */
public class DBHelpers {
    public static Connection myConnection() throws NamingException, SQLException {
        Context context = new InitialContext();
        Context tomcatContext = (Context) context.lookup("java:comp//env");
        DataSource ds = (DataSource) tomcatContext.lookup("J3.L.P0015");
        Connection conn = ds.getConnection();
        return conn;
    }
}
