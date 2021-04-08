/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.rent;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import khoapc.car.CarDTO;
import khoapc.ultities.DBHelpers;

/**
 *
 * @author Khoa Pham
 */
public class RentDAO implements Serializable {

    public int countRentID() throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(rentID) "
                        + "FROM tblRent";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    int result = rs.getInt(1);
                    return result;
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

    public boolean createRent(RentDTO dto, Map<String, CarDTO> items, Date rentingDate, Date returnDate) throws NamingException, SQLException {
        boolean check = true;
        Connection conn = null;
        PreparedStatement preStm = null;

        Date date = new Date();

        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                String sql = "INSERT INTO tblRent(rentID, email, total, date, status) "
                        + "VALUES (?,?,?,?,?) ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getRentID());
                preStm.setString(2, dto.getEmail());
                preStm.setFloat(3, dto.getTotal());
                preStm.setDate(4, new java.sql.Date(date.getTime()));
                preStm.setString(5, "Active");
                preStm.executeUpdate();
                for (String car : items.keySet()) {
                    sql = "INSERT INTO tblRentingDetail(carID, rentID, price, quantity, renting_date, return_date) "
                            + "VALUES (?,?,?,?,?,?)";
                    preStm = conn.prepareStatement(sql);
                    preStm.setString(1, car);
                    preStm.setString(2, dto.getRentID());
                    preStm.setFloat(3, items.get(car).getPrice());
                    preStm.setInt(4, items.get(car).getQuantity());
                    preStm.setDate(5, new java.sql.Date(rentingDate.getTime()));
                    preStm.setDate(6, new java.sql.Date(returnDate.getTime()));
                    preStm.executeUpdate();
                }
            }
            conn.commit();
        } catch (Exception e) {
            check = false;
            conn.rollback();
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean createRentDiscount(RentDTO dto, Map<String, CarDTO> items, Date rentingDate, Date returnDate, String discountCode) throws NamingException, SQLException {
        boolean check = true;
        Connection conn = null;
        PreparedStatement preStm = null;

        Date date = new Date();

        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                String sql = "INSERT INTO tblRent(rentID, email, total, date, discount_id, status) "
                        + "VALUES (?,?,?,?,?,?) ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getRentID());
                preStm.setString(2, dto.getEmail());
                preStm.setFloat(3, dto.getTotal());
                preStm.setDate(4, new java.sql.Date(date.getTime()));
                preStm.setString(5, discountCode);
                preStm.setString(6, "Active");
                preStm.executeUpdate();
                for (String car : items.keySet()) {
                    sql = "INSERT INTO tblRentingDetail(carID, rentID, price, quantity, renting_date, return_date) "
                            + "VALUES (?,?,?,?,?,?)";
                    preStm = conn.prepareStatement(sql);
                    preStm.setString(1, car);
                    preStm.setString(2, dto.getRentID());
                    preStm.setFloat(3, items.get(car).getPrice());
                    preStm.setInt(4, items.get(car).getQuantity());
                    preStm.setDate(5, new java.sql.Date(rentingDate.getTime()));
                    preStm.setDate(6, new java.sql.Date(returnDate.getTime()));
                    preStm.executeUpdate();
                }
            }
            conn.commit();
        } catch (Exception e) {
            check = false;
            conn.rollback();
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public List<RentDTO> getOrder(String email) throws NamingException, SQLException {
        List<RentDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT rentID, total, date "
                        + "FROM tblRent "
                        + "WHERE email= ? "
                        + "AND status = 'Active' "
                        + "ORDER BY date DESC ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, email);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String rentID = rs.getString("rentID");
                    float total = rs.getFloat("total");
                    Date date = rs.getDate("date");
                    RentDTO dto = new RentDTO(rentID, email, total, date);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(dto);
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
        return list;
    }

    public List<RentDTO> getHistoryByDate(String email, Date date) throws NamingException, SQLException {
        List<RentDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT rentID,total,date "
                        + "FROM tblRent "
                        + "WHERE email = ? AND date= ?   "
                        + "AND status = 'Active' "
                        + "ORDER BY date DESC ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, email);
                preStm.setDate(2, new java.sql.Date(date.getTime()));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String rentID = rs.getString("rentID");
                    float total = rs.getFloat("total");
                    Date historyDate = rs.getDate("date");
                    RentDTO dto = new RentDTO(rentID, email, total, historyDate);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(dto);
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
        return list;
    }

    public List<RentDTO> getHistoryByCarName(String email, String carName) throws NamingException, SQLException {
        List<RentDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = " SELECT r.rentID,r.total,r.date "
                        + "FROM tblRent r JOIN tblRentingDetail rd ON r.rentID = rd.rentID "
                        + "AND  r.rentID = rd.rentID "
                        + " JOIN tblCar c ON rd.carID = c.carID "
                        + "WHERE r.email = ? "
                        + "AND c.carName LIKE ? "
                        + "AND r.status='Active' "
                        + "ORDER BY date DESC ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, email);
                preStm.setString(2, "%" + carName + "%");
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String rentID = rs.getString("rentID");
                    float total = rs.getFloat("total");
                    Date date = rs.getDate("date");
                    RentDTO dto = new RentDTO(rentID, email, total, date);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(dto);
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
        return list;
    }

    public boolean deleteOrder(String orderID) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "UPDATE tblRent "
                        + "SET status = 'deActive'  "
                        + "WHERE rentID = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, orderID);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    return true;
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
        return false;
    }
}
