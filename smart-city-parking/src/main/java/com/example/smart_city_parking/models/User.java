package com.example.smart_city_parking.models;

public class User {
    private int userId;
    private int role_id;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String licensePlate;
    private String paymentMethod;
    private String password; // Add password field

    // Constructor (update or add)
    public User(int userId, String userName, int role_id,String userEmail, String userPhone, String licensePlate, String paymentMethod, String password) {
        this.userId = userId;
        
        this.userName = userName;
        this.role_id = role_id;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.licensePlate = licensePlate;
        this.paymentMethod = paymentMethod;
        this.password = password;
    }

    // Default constructor (if needed)
    public User() {}

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public int getRoleId() {
        return role_id;
    }

    public void setRoleId(int role_id) {
        this.role_id = role_id;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPassword() { // Add getter for password
        return password;
    }

    public void setPassword(String password) { // Add setter for password
        this.password = password;
    }
}
