package com.assisment.discountCalculator.model;

import java.util.List;

public class BillCalculationResult {

    private List<Item> groceryItems;
    private List<Item> nonGroceryItems;
    private double groceryTotal;
    private double nonGroceryTotal;
    private double finalAmount;

    public BillCalculationResult(List<Item> groceryItems, List<Item> nonGroceryItems,
                                 double groceryTotal, double nonGroceryTotal, double finalAmount) {
        this.groceryItems = groceryItems;
        this.nonGroceryItems = nonGroceryItems;
        this.groceryTotal = groceryTotal;
        this.nonGroceryTotal = nonGroceryTotal;
        this.finalAmount = finalAmount;
    }

    public List<Item> getGroceryItems() {
        return groceryItems;
    }

    public List<Item> getNonGroceryItems() {
        return nonGroceryItems;
    }

    public double getGroceryTotal() {
        return groceryTotal;
    }

    public double getNonGroceryTotal() {
        return nonGroceryTotal;
    }

    public double getFinalAmount() {
        return finalAmount;
    }
}

