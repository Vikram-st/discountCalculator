package com.assisment.currency_discount_calculator.model;

import java.util.List;

public class BillRequest {



    private User user;
    private String originalCurrency;
    private String targetCurrency;
    private double totalAmount;
    private List<Item> items;
    private String itemId; // New field for itemId

    // Constructors, Getters, and Setters


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
