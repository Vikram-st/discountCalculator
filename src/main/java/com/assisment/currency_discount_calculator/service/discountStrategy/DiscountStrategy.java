package com.assisment.currency_discount_calculator.service.discountStrategy;

import com.assisment.currency_discount_calculator.model.Item;

import java.util.List;

public interface DiscountStrategy {
    double calculateDiscount(double totalBill, List<Item> items);
}
