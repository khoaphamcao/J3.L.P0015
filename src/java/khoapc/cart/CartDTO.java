/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import khoapc.car.CarDTO;

/**
 *
 * @author Khoa Pham
 */
public class CartDTO implements Serializable{
    private Map<String, CarDTO> cars;
    
    public CartDTO() {
    }
    
    public CartDTO(Map<String, CarDTO> cars) {
        this.cars = cars;
    }
    
    public Map<String, CarDTO> getCars() {
        return cars;
    }
    
    public void setItems(Map<String, CarDTO> cars) {
        this.cars = cars;
    }
    
    
    public void addCarToCart(CarDTO dto) {
        if (this.cars == null) {
            this.cars = new HashMap<>();
        }
        if (this.cars.containsKey(dto.getCarID())) {
            int tmp = dto.getQuantity() - cars.get(dto.getCarID()).getQuantity();
            dto.setQuantity(dto.getQuantity() - tmp + 1);
        } else {
            dto.setQuantity(1);
        }
        this.cars.put(dto.getCarID(), dto);
    }
    
    public void removeCarFromCart(String carID) {
        if (this.cars == null) {
            return;
        }
        if (this.cars.containsKey(carID)) {
            cars.remove(carID);
            if (this.cars.isEmpty()) {
                this.cars = null;
            }
        }
    }
    
    public void updateCart(CarDTO dto){
        if(cars != null){
            if(cars.containsKey(dto.getCarID())){
                this.cars.replace(dto.getCarID(), dto);
            }
        }
    }
    
    
//    public CartDTO getDiscountCart(CarDTO dto){
//        CartDTO cartDiscount = null;
//        if(cars != null){
//            if(cars.containsKey(dto.getCarID())){
//                this.cars.replace(dto.getCarID(), dto);
//            }
//        }
//        return discountCart;
//    }
}
