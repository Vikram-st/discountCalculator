package com.assisment.discountCalculator.model;

import java.util.List;

public class BillResponse {

    private String billId;
    private double finalAmount;
    private List<Item> groceryItems;
    private List<Item> nonGroceryItems;
    private double groceryTotal;
    private double nonGroceryTotal;

    // Constructors, Getters, and Setters

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public List<Item> getGroceryItems() {
        return groceryItems;
    }

    public void setGroceryItems(List<Item> groceryItems) {
        this.groceryItems = groceryItems;
    }

    public List<Item> getNonGroceryItems() {
        return nonGroceryItems;
    }

    public void setNonGroceryItems(List<Item> nonGroceryItems) {
        this.nonGroceryItems = nonGroceryItems;
    }

    public double getGroceryTotal() {
        return groceryTotal;
    }

    public void setGroceryTotal(double groceryTotal) {
        this.groceryTotal = groceryTotal;
    }

    public double getNonGroceryTotal() {
        return nonGroceryTotal;
    }

    public void setNonGroceryTotal(double nonGroceryTotal) {
        this.nonGroceryTotal = nonGroceryTotal;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }
}
