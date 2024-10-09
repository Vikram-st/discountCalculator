package com.assisment.currency_discount_calculator.service.discountStrategy.impl;

import com.assisment.currency_discount_calculator.model.Item;
import com.assisment.currency_discount_calculator.service.discountStrategy.DiscountStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffiliateDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(double totalBill, List<Item> items) {
        double discount = 0.10;
        double amountDiscount = Math.floor(totalBill / 100) * 5;
        return (totalBill * (1 - discount)) - amountDiscount;
    }
}

