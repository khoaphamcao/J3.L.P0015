/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.discount;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;
import khoapc.ultities.DBHelpers;

/**
 *
 * @author Khoa Pham
 */
public class DiscountDAO implements Serializable {

    public int countCheck(String email, String discountID) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(discount_id) "
                        + "FROM tblRent "
                        + "WHERE email = ? "
                        + "AND discount_id = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, email);
                preStm.setString(2, discountID);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    int total = rs.getInt(1);
                    return total;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return 0;
    }

    public DiscountDTO checkExpireDate(String recentDate, String discountCode) throws NamingException, SQLException {
        DiscountDTO discountDTO = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT expire_date, discount "
                        + "FROM tblDiscount "
                        + "WHERE expire_date >= ? "
                        + "AND discount_id = ? ";
                preStm = conn.prepareStatement(sql);
//                preStm.setDate(1, new java.sql.Date(recentDate.getTime()));
                preStm.setString(1, recentDate);
                preStm.setString(2, discountCode);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    Date expiredDate = rs.getDate("expire_date");
                    int discount = rs.getInt("discount");
                    discountDTO = new DiscountDTO(discountCode, expiredDate, discount);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return discountDTO;
    }
}
