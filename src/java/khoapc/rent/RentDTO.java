/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.rent;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Khoa Pham
 */
public class RentDTO implements Serializable{
    private String rentID;
    private String email;
    private float total;
    private Date date;
    private String status;

    public RentDTO(String rentID, String email, float total, Date date) {
        this.rentID = rentID;
        this.email = email;
        this.total = total;
        this.date = date;
    }
    

    public RentDTO(String rentID, String email, float total) {
        this.rentID = rentID;
        this.email = email;
        this.total = total;
    }

    public RentDTO(String rentID, String email, float total, Date date, String status) {
        this.rentID = rentID;
        this.email = email;
        this.total = total;
        this.date = date;
        this.status = status;
    }

    public String getRentID() {
        return rentID;
    }

    public void setRentID(String rentID) {
        this.rentID = rentID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
