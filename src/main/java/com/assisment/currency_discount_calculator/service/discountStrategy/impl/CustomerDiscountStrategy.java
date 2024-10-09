package com.assisment.currency_discount_calculator.service.discountStrategy.impl;

import com.assisment.currency_discount_calculator.model.Item;
import com.assisment.currency_discount_calculator.service.discountStrategy.DiscountStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDiscountStrategy implements DiscountStrategy {
    private static final double LONG_TERM_CUSTOMER_DISCOUNT = 0.05;

    @Override
    public double calculateDiscount(double totalBill, List<Item> items) {
        double discount = 0;
        double amountDiscount = Math.floor(totalBill / 100) * 5;

        if (!isGroceries(items)) {
            discount = LONG_TERM_CUSTOMER_DISCOUNT;
        }

        return (totalBill * (1 - discount)) - amountDiscount;
    }

    private boolean isGroceries(List<Item> items) {
        // Check if items are groceries (implement actual logic)
        return items.stream().allMatch(item -> item.getCategory().equals("GROCERIES"));
    }
}
