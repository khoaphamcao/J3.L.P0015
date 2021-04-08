/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.rentingdetail;

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
public class RentingDetailDAO implements Serializable {

    public RentingDetailDTO getCarOnRentingCarDeltail(String carID, Date rentingDate, Date returnDate) throws NamingException, SQLException {
        RentingDetailDTO rentingDTO = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT carID, quantity "
                        + "FROM tblRentingDetail rd "
                        + "WHERE renting_date >= ? AND return_date <= ? "
                        + "AND carID = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setDate(1, new java.sql.Date(rentingDate.getTime()));
                preStm.setDate(2, new java.sql.Date(returnDate.getTime()));
                preStm.setString(3, carID);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    rentingDTO = new RentingDetailDTO(carID, quantity);
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
        return rentingDTO;
    }
}
