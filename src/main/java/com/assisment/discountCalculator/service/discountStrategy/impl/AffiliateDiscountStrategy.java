package com.assisment.discountCalculator.service.discountStrategy.impl;

import com.assisment.discountCalculator.model.Item;
import com.assisment.discountCalculator.service.discountStrategy.DiscountStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffiliateDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(double totalBill, List<Item> items) {
        double discount = 0.10;
        return (totalBill * (1 - discount));
    }
}

