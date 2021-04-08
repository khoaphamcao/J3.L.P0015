/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.shoppinghistory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import khoapc.car.CarDTO;
import khoapc.rent.RentDTO;
import khoapc.ultities.DBHelpers;

/**
 *
 * @author Khoa Pham
 */
public class ShoppingHistoryDAO implements Serializable {

    public ShoppingHistoryDTO getCarHistoryByRentID(RentDTO rent) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<CarDTO> listCar = null;
        ShoppingHistoryDTO history = new ShoppingHistoryDTO();
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT c.carName,rd.quantity,rd.price,r.total "
                        + "FROM tblRent r "
                        + "JOIN tblRentingDetail rd ON r.rentID=rd.rentID AND r.rentID = rd.rentID "
                        + "JOIN tblCar c  ON rd.carID = c.carID "
                        + "WHERE r.rentID = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, rent.getRentID());
                rs = preStm.executeQuery();
                float total = 0;
                while (rs.next()) {
                    String carName = rs.getString("carName");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    total = rs.getFloat("total");
                    CarDTO dto = new CarDTO(carName, price, quantity);
                    if (listCar == null) {
                        listCar = new ArrayList<>();
                    }
                    listCar.add(dto);
                }
                history = new ShoppingHistoryDTO(rent.getRentID(), listCar, total);
                return history;
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
        return history;
    }
}
