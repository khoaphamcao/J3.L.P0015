/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.car;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import khoapc.ultities.DBHelpers;

/**
 *
 * @author Khoa Pham
 */
public class CarDAO implements Serializable {

    //DEFAULT SEARCH
    public int countCar() throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(c.carID) "
                        + "FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID "
                        + "WHERE quantity >= 0";
                preStm = conn.prepareStatement(sql);
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

    // PAGING DEFAULT 
    public List<CarDTO> getPagingListAllCar(int index) throws NamingException, SQLException {
        List<CarDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, color, year, ca.category_name, price, quantity "
                        + "FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID "
                        + "WHERE quantity >= 0 "
                        + "ORDER BY year "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY ";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, (index * 3 - 3));
                rs = preStm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date year = rs.getDate("year");
                    String category = rs.getString("category_name");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    CarDTO dto = new CarDTO(carID, carName, color, year, category, price, quantity);
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

    //SEARCH BY CATE = ALL & AMOUNT 
    public int countAllCarByAmount(int amount) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(c.carID) "
                        + "FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID "
                        + "WHERE quantity >= ?";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, amount);
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
    
    
    // PAGING SEARCH BY ALL && AMOUNT 
    public List<CarDTO> getPagingListAllCarByAmount(int index, int amount) throws NamingException, SQLException {
        List<CarDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, color, year, ca.category_name, price, quantity "
                        + "FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID "
                        + "WHERE quantity >= ? "
                        + "ORDER BY year "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY ";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, amount);
                preStm.setInt(2, (index * 3 - 3));
                rs = preStm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date year = rs.getDate("year");
                    String category = rs.getString("category_name");
                    float answer_content_3 = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    CarDTO dto = new CarDTO(carID, carName, color, year, category, answer_content_3, quantity);
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
    
    //SEARCH BY CATE = ALL & CARNAME 
    public int countAllCarByName(String carname) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(c.carID) " +
                             " FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID " +
                             "WHERE quantity >= 0 " +
                             "AND c.carName LIKE ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%"+ carname +"%");
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
    
    // PAGING SEARCH BY ALL && AMOUNT 
    public List<CarDTO> getPagingListAllCarByName(int index, String carname) throws NamingException, SQLException {
        List<CarDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, color, year, ca.category_name, price, quantity "
                        + "FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID "
                        + "WHERE quantity >= 0 "
                        + "AND c.carName LIKE ? "
                        + "ORDER BY year "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%"+ carname + "%" );
                preStm.setInt(2, (index * 3 - 3));
                rs = preStm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date year = rs.getDate("year");
                    String category = rs.getString("category_name");
                    float answer_content_3 = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    CarDTO dto = new CarDTO(carID, carName, color, year, category, answer_content_3, quantity);
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
    
    
    //SEARCH BY CATE = ALL & CARNAME 
    public int countAllCarByName_Amount(String carname,int amount) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(c.carID) " +
                             " FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID " +
                             "WHERE quantity >= ? " +
                             "AND c.carName LIKE ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1,  amount);
                preStm.setString(2, "%"+ carname +"%");
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
    
    
    
    public List<CarDTO> getPagingListAllCarByName_Amount(int index, String carname, int amount) throws NamingException, SQLException {
        List<CarDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, color, year, ca.category_name, price, quantity "
                        + "FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID "
                        + "WHERE quantity >= ? "
                        + "AND c.carName LIKE ? "
                        + "ORDER BY year "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY ";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, amount);
                preStm.setString(2, "%"+ carname + "%" );
                preStm.setInt(3, (index * 3 - 3));
                rs = preStm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date year = rs.getDate("year");
                    String category = rs.getString("category_name");
                    float answer_content_3 = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    CarDTO dto = new CarDTO(carID, carName, color, year, category, answer_content_3, quantity);
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
    
    public int countCarByCategory(String category) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(c.carID) " +
                             " FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID " +
                             "WHERE quantity >= 0 " +
                             "AND ca.category_name LIKE ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1,  category);
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
    
    
    public List<CarDTO> getPagingListCarByCategory(int index, String categorySearch) throws NamingException, SQLException {
        List<CarDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, color, year, ca.category_name, price, quantity "
                        + "FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID "
                        + "WHERE quantity >= 0 "
                        + "AND ca.category_name LIKE ? "
                        + "ORDER BY year "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, categorySearch);
                preStm.setInt(2, (index * 3 - 3));
                rs = preStm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date year = rs.getDate("year");
                    String category = rs.getString("category_name");
                    float answer_content_3 = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    CarDTO dto = new CarDTO(carID, carName, color, year, category, answer_content_3, quantity);
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
    
    
    // COUNT CAR BY CATEGORY && AMOUNT
    public int countCarByCategory_Amount(String category, int amount) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(c.carID) " +
                             " FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID " +
                             "WHERE quantity >= ? " +
                             "AND ca.category_name LIKE ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1,  amount);
                preStm.setString(2,  category);
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
    
    //PAGING CATEGORY && AMOUNT
    public List<CarDTO> getPagingListCarByCategory_Amount(int index, String categorySearch, int amount) throws NamingException, SQLException {
        List<CarDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, color, year, ca.category_name, price, quantity "
                        + "FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID "
                        + "WHERE quantity >= ? "
                        + "AND ca.category_name LIKE ? "
                        + "ORDER BY year "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY ";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, amount);
                preStm.setString(2, categorySearch);
                preStm.setInt(3, (index * 3 - 3));
                rs = preStm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date year = rs.getDate("year");
                    String category = rs.getString("category_name");
                    float answer_content_3 = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    CarDTO dto = new CarDTO(carID, carName, color, year, category, answer_content_3, quantity);
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
    
    // COUNT CAR BY CATEGORY && CARNAME
    public int countCarByCategory_Name(String category, String carname) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(c.carID) " +
                             " FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID " +
                             "WHERE quantity >= 0 " +
                             "AND ca.category_name LIKE ? "
                        + "AND c.carName LIKE ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1,  category);
                preStm.setString(2,  "%" + carname + "%");
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
    
    
    //PAGING CATEGORY && CARNAME
    public List<CarDTO> getPagingListCarByCategory_Name(int index, String categorySearch, String carname) throws NamingException, SQLException {
        List<CarDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, color, year, ca.category_name, price, quantity "
                        + "FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID "
                        + "WHERE quantity >= 0 "
                        + "AND ca.category_name LIKE ? "
                        + "AND c.carName LIKE ? "
                        + "ORDER BY year "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, categorySearch);
                preStm.setString(2, "%"+carname+"%");
                preStm.setInt(3, (index * 3 - 3));
                rs = preStm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date year = rs.getDate("year");
                    String category = rs.getString("category_name");
                    float answer_content_3 = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    CarDTO dto = new CarDTO(carID, carName, color, year, category, answer_content_3, quantity);
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
    
    // COUNT CAR BY CATEGORY && CARNAME && AMOUNT
    public int countCarByCategory_Name_Amount(String category, String carname, int amount) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(c.carID) " +
                             " FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID " +
                             "WHERE quantity >= ? " +
                             "AND ca.category_name LIKE ? "
                        + "AND c.carName LIKE ?";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1,  amount);
                preStm.setString(2,  category);
                preStm.setString(3,  "%" + carname + "%");
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
    
    //PAGING CATEGORY && CARNAME
    public List<CarDTO> getPagingListCarByCategory_Name_Amount(int index, String categorySearch, String carname, int amount) throws NamingException, SQLException {
        List<CarDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, color, year, ca.category_name, price, quantity "
                        + "FROM tblCar c JOIN tblCategory ca ON c.categoryID = ca.categoryID "
                        + "WHERE quantity >= ? "
                        + "AND ca.category_name LIKE ? "
                        + "AND c.carName LIKE ? "
                        + "ORDER BY year "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY ";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, amount);
                preStm.setString(2, categorySearch);
                preStm.setString(3, "%"+carname+"%");
                preStm.setInt(4, (index * 3 - 3));
                rs = preStm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date year = rs.getDate("year");
                    String category = rs.getString("category_name");
                    float answer_content_3 = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    CarDTO dto = new CarDTO(carID, carName, color, year, category, answer_content_3, quantity);
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
    
    // GET ENDPAGE
    public int getEndPage(int countQuest) {
        int endPage = 0;
        int pageSize = 3;
        endPage = countQuest / pageSize;
        if (countQuest % pageSize != 0) {
            endPage++;
        }
        return endPage;
    }
    
    
    public int getCarQuantityByID(String carID) throws SQLException, NamingException{
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT quantity " +
                            "FROM tblCar " +
                            "WHERE  carID = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, carID);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    int totalFood = rs.getInt("quantity");
                    return totalFood;
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
}
