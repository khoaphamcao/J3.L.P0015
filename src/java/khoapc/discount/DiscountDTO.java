/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.discount;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Khoa Pham
 */
public class DiscountDTO implements Serializable{
    private String discountID;
    private Date date;
    private int discount;

    public DiscountDTO(String discountID, Date date) {
        this.discountID = discountID;
        this.date = date;
    }

    
    
    public DiscountDTO(String discountID, Date date, int discount) {
        this.discountID = discountID;
        this.date = date;
        this.discount = discount;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
    
    
    
    
}
