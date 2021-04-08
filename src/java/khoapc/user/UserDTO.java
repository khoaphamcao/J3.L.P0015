/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.user;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Khoa Pham
 */
public class UserDTO implements Serializable{
    private String email;
    private String phone;
    private String name;
    private String password;
    private String address;
    private Date createDate;
    private String role;
    private String status;

    public UserDTO() {
    }

    public UserDTO(String email, String phone, String name, String address, String role, String status) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.role = role;
        this.status = status;
    }
    
    

    public UserDTO(String email, String phone, String name, String password, String address, Date createDate, String role) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.address = address;
        this.createDate = createDate;
        this.role = role;
    }
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
