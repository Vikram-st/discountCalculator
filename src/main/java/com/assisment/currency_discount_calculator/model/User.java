package com.assisment.currency_discount_calculator.model;

public class User {
    private long userId;
    private UserType userType;
    private int customerTenure;

    public User(long userId, UserType userType, int customerTenure) {
        this.userId = userId;
        this.userType = userType;
        this.customerTenure = customerTenure;
    }

    public User(String userType, int customerTenure) {
        this.userType = UserType.valueOf(userType);
        this.customerTenure = customerTenure;
    }

    public User() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getCustomerTenure() {
        return customerTenure;
    }

    public void setCustomerTenure(int customerTenure) {
        this.customerTenure = customerTenure;
    }
}