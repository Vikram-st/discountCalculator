package com.assisment.discountCalculator.service.discountStrategy;

import com.assisment.discountCalculator.model.Item;

import java.util.List;

public interface DiscountStrategy {
    double calculateDiscount(double totalBill, List<Item> items);
}
