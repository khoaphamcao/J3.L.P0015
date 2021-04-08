/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.car;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Khoa Pham
 */
public class CarDTO implements Serializable {

    private String carID;
    private String carName;
    private String color;
    private Date year;
    private String category;
    private float price;
    private int quantity;

    public CarDTO() {
    }

    public CarDTO(String carName, float price, int quantity) {
        this.carName = carName;
        this.price = price;
        this.quantity = quantity;
    }
    
    public CarDTO(String carID, String carName, String color, String category, float price, int quantity) {
        this.carID = carID;
        this.carName = carName;
        this.color = color;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
    
    

    public CarDTO(String carID, String carName, String color, Date year, String category, float price, int quantity) {
        this.carID = carID;
        this.carName = carName;
        this.color = color;
        this.year = year;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
