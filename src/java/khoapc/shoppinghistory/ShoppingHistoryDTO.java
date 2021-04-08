/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.shoppinghistory;

import java.io.Serializable;
import java.util.List;
import khoapc.car.CarDTO;

/**
 *
 * @author Khoa Pham
 */
public class ShoppingHistoryDTO implements Serializable{
    private String rentID;
    private List<CarDTO> carList;
    private float total;

    public ShoppingHistoryDTO() {
    }

    public ShoppingHistoryDTO(String rentID, List<CarDTO> carList, float total) {
        this.rentID = rentID;
        this.carList = carList;
        this.total = total;
    }

    public String getRentID() {
        return rentID;
    }

    public void setRentID(String rentID) {
        this.rentID = rentID;
    }

    public List<CarDTO> getCarList() {
        return carList;
    }

    public void setCarList(List<CarDTO> carList) {
        this.carList = carList;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
