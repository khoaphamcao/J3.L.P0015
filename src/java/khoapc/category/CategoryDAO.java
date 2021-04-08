/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.category;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import khoapc.car.CarDTO;
import khoapc.ultities.DBHelpers;

/**
 *
 * @author Khoa Pham
 */
public class CategoryDAO implements Serializable {

    public List<CategoryDTO> getPagingListCategory() throws NamingException, SQLException {
        List<CategoryDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT categoryID, category_name "
                        + "FROM tblCategory ";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String categoryID = rs.getString("categoryID");
                    String category_name = rs.getString("category_name");
                    CategoryDTO dto = new CategoryDTO(categoryID, category_name);
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
}
